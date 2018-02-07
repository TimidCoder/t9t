package com.arvatosystems.t9t.base.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.T9tConstants;
import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceRequestHeader;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.event.BucketWriteKey;
import com.arvatosystems.t9t.server.InternalHeaderParameters;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableList;

import de.jpaw.bonaparte.pojos.api.LongFilter;
import de.jpaw.bonaparte.refsw.impl.AbstractRequestContext;
import de.jpaw.util.ExceptionUtil;

/** Holds the current request's environment.
 *
 * For every request, one of these is created.
 * Additional ones may be created for the asynchronous log writers (using dummy or null internalHeaderParameters)
 *
 * Any functionality relating to customization has been moved to a separate class (separation of concerns).
 */
public class RequestContext extends AbstractRequestContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestContext.class);
    static private final Cache<Long, Semaphore> GLOBAL_JVM_LOCKS = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.MINUTES).initialCapacity(1000).build();

    public final InternalHeaderParameters       internalHeaderParameters;

    public final ITenantCustomization customization;    // stored here for convenience
    public final ITenantMapping tenantMapping;          // stored here for convenience

    private List<IPostCommitHook> postCommitList = null;  // to be executed after successful requests / commits
    private List<IPostCommitHook> postFailureList = null; // to be executed after failures
    private final AtomicInteger progressCounter = new AtomicInteger(0);
    private final Map<BucketWriteKey, Integer> bucketsToWrite = new HashMap<BucketWriteKey, Integer>();
    private final Map<Long, Semaphore> OWNED_JVM_LOCKS = new ConcurrentHashMap<>();  // all locks held by this thread / request context (by ref)
    public volatile String statusText;
    private volatile boolean priorityRequest = false;   // can be set explicitly, or via ServiceRequestHeader

    public void incrementProgress() {
        progressCounter.incrementAndGet();
    }

    public int getProgressCounter() {
        return progressCounter.get();
    }

    public void addPostCommitHook(IPostCommitHook newHook) {
        if (postCommitList == null)
            postCommitList = new ArrayList<IPostCommitHook>(4);
        postCommitList.add(newHook);
    }

    public void addPostFailureHook(IPostCommitHook newHook) {
        if (postFailureList == null)
            postFailureList = new ArrayList<IPostCommitHook>(4);
        postFailureList.add(newHook);
    }


    public RequestContext(InternalHeaderParameters internalHeaderParameters, ICustomization customizationProvider) {
        super(internalHeaderParameters.getExecutionStartedAt(),
              internalHeaderParameters.getJwtInfo().getUserId(),
              internalHeaderParameters.getJwtInfo().getTenantId(),
              internalHeaderParameters.getJwtInfo().getUserRef(),
              internalHeaderParameters.getJwtInfo().getTenantRef(),
              internalHeaderParameters.getProcessRef());
        this.internalHeaderParameters = internalHeaderParameters;
        this.customization = customizationProvider.getTenantCustomization(tenantRef, tenantId);
        this.tenantMapping = customizationProvider.getTenantMapping(tenantRef, tenantId);
    }

    public void fillResponseStandardFields(ServiceResponse response) {
        ServiceRequestHeader h = internalHeaderParameters.getRequestHeader();
        if (h != null)
            response.setMessageId(h.getMessageId());
        response.setTenantId(tenantId);
        response.setProcessRef(requestRef);
    }

    /** Returns a LongFilter condition on the current tenant and possibly the default tenant, if that one is different. */
    public LongFilter tenantFilter(String name) {
        final LongFilter filter = new LongFilter(name);
        if (T9tConstants.GLOBAL_TENANT_REF42.equals(tenantRef))
            filter.setEqualsValue(T9tConstants.GLOBAL_TENANT_REF42);
        else
            filter.setValueList(ImmutableList.of(T9tConstants.GLOBAL_TENANT_REF42, tenantRef));
        return filter;
    }

    public void discardPostCommitActions() {
        if (postCommitList != null) {
            LOGGER.info("Discarding {} stored post commit actions", postCommitList.size());
            postCommitList.clear();
        }
    }

    public void applyPostCommitActions(RequestParameters rq, ServiceResponse rs) {
        // all persistence units have successfully committed...
        // now invoke possible postCommit hooks
        if (postCommitList != null) {
            LOGGER.info("Performing {} stored post commit actions", postCommitList.size());
            for (IPostCommitHook hook : postCommitList)
                hook.postCommit(this, rq, rs);
            // avoid duplicate execution...
            postCommitList.clear();
        }
    }

    public void applyPostFailureActions(RequestParameters rq, ServiceResponse rs) {
        // request returned an error. You can now notify someone...
        if (postFailureList != null) {
            LOGGER.info("Performing {} stored post failure actions", postFailureList.size());
            for (IPostCommitHook hook : postFailureList)
                hook.postCommit(this, rq, rs);
            // avoid duplicate execution...
            postFailureList.clear();
        }
    }

    // queue a bucket writing command. All bucket writes will be kicked off asynchronously after a successful commit
    public void writeBucket(String typeId, Long ref, Integer mode) {
        final BucketWriteKey key = new BucketWriteKey(tenantRef, ref, typeId);
        // combine it with prior commands
        bucketsToWrite.merge(key, mode, (a, b) -> Integer.valueOf(a.intValue() | b.intValue()));
    }

    // the queue is handed in by the caller because we do not have injection facilities within this class
    public void postBucketEntriesToQueue(IBucketWriter writer) {
        if (!bucketsToWrite.isEmpty()) {
            LOGGER.debug("{} bucket entries have been collected, queueing them...", bucketsToWrite.size());
            writer.writeToBuckets(bucketsToWrite);
        }
    }

    // PER_JVM resource management

    /** Releases all locks held by this context. */
    public void releaseAllLocks() {
        if (!OWNED_JVM_LOCKS.isEmpty())
            LOGGER.debug("Releasing locks on {} refs", OWNED_JVM_LOCKS.size());
        for (Semaphore sem : OWNED_JVM_LOCKS.values())
            sem.release();
        OWNED_JVM_LOCKS.clear();
    }

    /** Acquires a new lock within a given timeout of n milliseconds. */
    public void lockRef(final Long ref, final long timeoutInMillis) {
        OWNED_JVM_LOCKS.computeIfAbsent(ref, myRef -> {
            // get a Semaphore from the global pool
            try {
                final Semaphore globalSem = GLOBAL_JVM_LOCKS.get(ref, () -> new Semaphore(1, true));  // get a global Semaphore, or create one if non exists
                if (!globalSem.tryAcquire(timeoutInMillis, TimeUnit.MILLISECONDS)) {
                    final String msg = ref + " after " + timeoutInMillis + " milliseconds";
                    LOGGER.error("Could not acquire JVM lock on {}", msg);
                    throw new T9tException(T9tException.COULD_NOT_ACQUIRE_LOCK, msg);
                }
                LOGGER.debug("Acquired JVM lock on ref {}", ref);
                return globalSem;
            } catch (ExecutionException e) {
                final String msg = ref + " after " + timeoutInMillis + " milliseconds due to ExecutionException " + ExceptionUtil.causeChain(e);
                LOGGER.error("Could not acquire JVM lock on {}", msg);
                throw new T9tException(T9tException.COULD_NOT_ACQUIRE_LOCK, msg);
            } catch (InterruptedException e) {
                final String msg = ref + " after " + timeoutInMillis + " milliseconds due to InterruptedException " + ExceptionUtil.causeChain(e);
                LOGGER.error("Could not acquire JVM lock on {}", msg);
                throw new T9tException(T9tException.COULD_NOT_ACQUIRE_LOCK, msg);
            }
        });
    }

    /** Acquires a new lock within the default timeout (of currently 5000 milliseconds). */
    public void lockRef(final Long ref) {
        lockRef(ref, 5000L);
    }

    public boolean isPriorityRequest() {
        return priorityRequest;
    }

    /** The priority flag affects all subsequently triggered subrequests. (Preregisters priority settings) */
    public void setPriorityRequest(boolean priorityRequest) {
        this.priorityRequest = priorityRequest;
    }
}
