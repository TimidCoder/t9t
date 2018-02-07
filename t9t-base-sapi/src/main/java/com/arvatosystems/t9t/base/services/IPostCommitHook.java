package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.api.ServiceResponse;

/** Interface to be implemented by callbacks which want to be informed after a transaction has been performed (all persistence providers have committed).
 * Usually used to update caches.
 *
 * The invocation is performed after a successful commit only.
 * The RequestContext is still valid, but the JPA transaction has been closed.
 * If the hook needs additional database I/O, it must create its own JPA transaction.
 * It can use the existing entityManager, but must do a beginTransaction() / commit().
 *
 * It could be generic <R extends RequestParameters>
 */
public interface IPostCommitHook {
    void postCommit(RequestContext previousRequestContext, RequestParameters rq, ServiceResponse rs);
}
