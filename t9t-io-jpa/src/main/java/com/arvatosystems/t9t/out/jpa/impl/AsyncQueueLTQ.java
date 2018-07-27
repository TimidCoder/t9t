/*
 * Copyright (c) 2012 - 2018 Arvato Systems GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arvatosystems.t9t.out.jpa.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.output.ExportStatusEnum;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.cfg.be.AsyncTransmitterConfiguration;
import com.arvatosystems.t9t.cfg.be.ConfigProvider;
import com.arvatosystems.t9t.io.AsyncChannelDTO;
import com.arvatosystems.t9t.io.InMemoryMessage;
import com.arvatosystems.t9t.io.jpa.entities.AsyncChannelEntity;
import com.arvatosystems.t9t.io.jpa.entities.AsyncMessageEntity;
import com.arvatosystems.t9t.out.services.IAsyncMessageUpdater;
import com.arvatosystems.t9t.out.services.IAsyncQueue;
import com.arvatosystems.t9t.out.services.IAsyncSender;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.HttpPostResponseObject;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Named;
import de.jpaw.dp.Provider;
import de.jpaw.dp.Singleton;
import de.jpaw.util.ExceptionUtil;

@Singleton
@Named("LTQ")
public class AsyncQueueLTQ<R extends BonaPortable> implements IAsyncQueue {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncQueueLTQ.class);
    private final Provider<RequestContext> ctxProvider = Jdp.getProvider(RequestContext.class);
    private final IAsyncMessageUpdater messageUpdater = Jdp.getRequired(IAsyncMessageUpdater.class);
    private final IAsyncSender<R> messageSender = Jdp.getRequired(IAsyncSender.class);
    private final LinkedTransferQueue<InMemoryMessage> queue = new LinkedTransferQueue<>();
    private final AsyncTransmitterConfiguration serverConfig = ConfigProvider.getConfiguration().getAsyncMsgConfiguration();
    private final Boolean lock = new Boolean(true);  // separate object used as semaphore
    private final AtomicBoolean gate = new AtomicBoolean();  // true is GREEN, false is RED
    private final AtomicBoolean shutdownInProgress = new AtomicBoolean();
    private final EntityManagerFactory emf = Jdp.getRequired(EntityManagerFactory.class);
    private final Cache<String, AsyncChannelDTO> channelCache = CacheBuilder.newBuilder().expireAfterWrite(30, TimeUnit.SECONDS).build();
    private ExecutorService executor;
    private Future<Boolean> writerResult;

    public AsyncQueueLTQ() {
        LOGGER.warn("Async queue by LinkedTransferQueue loaded");
        // get any pending data from DB, fill queue up to a certain limit. If less than MAX_IN_QUEUE records are found, set the gate to GREEN
        // update: this is done by the regular refill job within the transmitter thread, no need to code it again here
        // launch a separate thread which continuously drains the transfer queue
        executor = Executors.newSingleThreadExecutor(call -> new Thread(call, "t9t-AsyncTx"));
        writerResult = executor.submit(new WriterThread());
    }

    private class WriterThread implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            while (!shutdownInProgress.get()) {
                try {
                    final InMemoryMessage nextMsg = queue.peek();
                    if (nextMsg != null) {
                        if (!tryToSend(nextMsg)) {
                            // switch to RED and wait
                            LOGGER.debug("Flipping gate to RED (transmission error)");
                            gate.set(false);
                            Thread.sleep(serverConfig.waitAfterExtError);
                        } else {
                            // eat message, it was sent successfully
                            if (queue.poll() == null) {
                                LOGGER.error("ILE: queue element no longer available!");
                            }
                        }
                    } else if (gate.get()) {
                        // gate is "GREEN", any message would be in memory, if it existed. No need to check the DB
                        Thread.sleep(serverConfig.timeoutIdleGreen);
                    } else {
                        // gate is "RED"
                        // no message in the queue now, refill queue from DB
                        if (!refillQueue()) {
                            // we are really idle and have switched to "GREEN" during the call to refillQueue(). Must wait the same time as above
                            Thread.sleep(serverConfig.timeoutIdleGreen);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Exception in Async transmitter thread: {}", ExceptionUtil.causeChain(e));
                }
            }
            return Boolean.TRUE;
        }
    }

    // fills the queue from the DB. Returns true if there was at least one element (or an exception has occurred), else false.
    // the gate is "RED" if we enter here. Synchronization is needed if we want to flip to "GREEN", in order to avoid race conditions
    protected boolean refillQueue() {
        final EntityManager em = emf.createEntityManager();
        List<AsyncMessageEntity> results = null;

        synchronized(lock) {
            try {
                em.getTransaction().begin();
                TypedQuery<AsyncMessageEntity> query = em.createQuery("SELECT m FROM AsyncMessageEntity m WHERE m.status != null ORDER BY m.objectRef", AsyncMessageEntity.class);
                query.setMaxResults(serverConfig.maxMessageAtStartup);
                results = query.getResultList();
                em.getTransaction().commit();
                em.clear();
            } catch (Exception e) {
                LOGGER.error("Database query exception: {}", ExceptionUtil.causeChain(e));
                try {
                    Thread.sleep(serverConfig.waitAfterDbErrors);
                } catch (InterruptedException e1) {
                    // continue with aborted sleep
                    return true;
                }
            } finally {
                em.close();
            }
            if (results == null) {
                // error: do nothing
                return false;
            } else if (results.size() == 0) {
                // switch to green, no data pending
                LOGGER.debug("Flipping gate to GREEN (queue empty)");
                gate.set(true);
                return false;
            }
            // add them to the queue
            for (AsyncMessageEntity m : results) {
                queue.put(new InMemoryMessage(m.getTenantRef(), m.getAsyncChannelId(), m.getObjectRef(), m.getPayload()));
            }
            if (results.size() < serverConfig.maxMessageAtStartup) {
                LOGGER.debug("Flipping gate to GREEN (low watermark)");
                gate.set(true);
            }
        }
        return true;
    }

    // sends a message. In case of error, the gate is flipped to "RED" and a long timeout is done, in order to ensure we do not burn CPU
    // in high frequency retry cycles.
    // Returns true is the message was sent successfully, else false
    protected boolean tryToSend(final InMemoryMessage nextMsg) {
        ExportStatusEnum newStatus = ExportStatusEnum.RESPONSE_ERROR;  // OK when sent
        final String channelId = nextMsg.getAsyncChannelId();
        final Long tenantRef = nextMsg.getTenantRef();
        try {
            LOGGER.info("Sending message to channel {} of type {}", nextMsg.getAsyncChannelId(), nextMsg.getPayload().ret$PQON());
            // obtain (cached) channel config
            final AsyncChannelDTO channelDto = channelCache.get(channelId, () -> readChannelConfig(channelId, tenantRef));
            if (!channelDto.getIsActive()) {
                LOGGER.debug("Discarding async message to inactive channel {}", channelId);
                newStatus = ExportStatusEnum.RESPONSE_OK;
            } else {
                // do external I/O
                Integer newHttpCode = null;
                Integer newClientReturnCode = null;
                String newClientReference = null;
                HttpPostResponseObject resp = messageSender.fireMessage(channelDto, serverConfig.timeoutExternal, nextMsg.getPayload());
                newHttpCode = resp.getHttpReturnCode();
                newStatus = (newHttpCode / 100) == 2 ? ExportStatusEnum.RESPONSE_OK : ExportStatusEnum.RESPONSE_ERROR;
                if (resp.getResponseObject() != null) {
                    R r = (R)resp.getResponseObject();
                    newClientReturnCode = messageSender.getClientReturnCode(r);
                    newClientReference = messageSender.getClientReference(r);
                }
                messageUpdater.updateMessage(nextMsg.getObjectRef(), newStatus, newHttpCode, newClientReturnCode, newClientReference);
                return newStatus == ExportStatusEnum.RESPONSE_OK;
            }
        } catch (ExecutionException e) {
            LOGGER.error("Cannot get cache configuration for channelId {}: {}", channelId, ExceptionUtil.causeChain(e));
            newStatus = ExportStatusEnum.PROCESSING_ERROR;
        }

        messageUpdater.updateMessage(nextMsg.getObjectRef(), newStatus, null, null, null);
        return newStatus == ExportStatusEnum.RESPONSE_OK;
    }

    // read a channel configuration from the database
    protected AsyncChannelDTO readChannelConfig(String channelId, Long tenantRef) {
        LOGGER.debug("Reading async channel configuration for channelId {}, tenantRef {}", channelId, tenantRef);
        final EntityManager em = emf.createEntityManager();
        List<AsyncChannelEntity> results = null;
        try {
            em.getTransaction().begin();
            TypedQuery<AsyncChannelEntity> query = em.createQuery("SELECT cfg FROM AsyncChannelEntity cfg WHERE cfg.asyncChannelId = :channelId and cfg.tenantRef = :tenantRef", AsyncChannelEntity.class);
            query.setParameter("tenantRef", tenantRef);
            query.setParameter("channelId", channelId);
            results = query.getResultList();
            em.getTransaction().commit();
            em.clear();
        } finally {
            em.close();
        }
        if (results.size() != 1)
            throw new T9tException(T9tException.RECORD_DOES_NOT_EXIST, "ChannelId " + channelId + " for tenant " + tenantRef);
        AsyncChannelDTO dto = results.get(0).ret$Data();
        dto.freeze();  // ensure it stays immutable in cache
        return dto;
    }

    @Override
    public void sendAsync(final String asyncChannelId, BonaPortable payload, Long objectRef) {
        final RequestContext ctx = ctxProvider.get();
        // redundant check to see if the channel exists (to get exception in sync thread already). Should not cost too much time due to caching
        try {
            final AsyncChannelDTO cfg = channelCache.get(asyncChannelId, () -> readChannelConfig(asyncChannelId, ctx.tenantRef));
            if (!cfg.getIsActive()) {
                LOGGER.debug("Discarding async message to inactive channel {}", asyncChannelId);
                return;
            }
        } catch (ExecutionException e) {
            LOGGER.error("Cannot get cache configuration for channelId {}: {}", asyncChannelId, ExceptionUtil.causeChain(e));
            throw new T9tException(T9tException.RECORD_DOES_NOT_EXIST, "ChannelId " + asyncChannelId);
        }

        // build the in-memory message
        final InMemoryMessage m = new InMemoryMessage();
        m.setTenantRef(ctx.tenantRef);       // obtain the tenantRef and store it
        m.setAsyncChannelId(asyncChannelId);
        m.setObjectRef(objectRef);
        m.setPayload(payload);

        synchronized(lock) {
            if (gate.get()) {
                // we are in "GREEN" status
                ctx.addPostCommitHook((RequestContext oldCtx, RequestParameters rq, ServiceResponse rs) -> {
                    queue.put(m);
                });
            }
        }
    }

    @Override
    public void close() {
        LOGGER.info("Shutting down async transmitter (in current state {})", gate.get());
        gate.set(false);
        shutdownInProgress.set(true);
        executor.shutdown();
        try {
            if (executor.awaitTermination(serverConfig.timeoutShutdown, TimeUnit.MILLISECONDS)) {
                LOGGER.info("Normal completion of shutting down async transmitter");
            } else {
                LOGGER.warn("Timeout during shutdown of async transmitter");
            }
        } catch (InterruptedException e) {
            LOGGER.warn("Shutdown of async transmitter was interrupted");
        }
    }

    @Override
    public void clearQueue() {
        // drain the queue! This is done after artificially removing entries from the queue
        queue.clear();
        gate.set(true);  // gate must be true now, because we otherwise will never poll again
    }
}
