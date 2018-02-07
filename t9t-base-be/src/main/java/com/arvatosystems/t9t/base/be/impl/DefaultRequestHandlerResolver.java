package com.arvatosystems.t9t.base.be.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.api.RequestParameters;
import com.arvatosystems.t9t.base.be.impl.NoHandlerPresentRequestHandler;
import com.arvatosystems.t9t.base.services.IRequestHandler;
import com.arvatosystems.t9t.base.services.IRequestHandlerResolver;

import de.jpaw.dp.Singleton;
import de.jpaw.util.ExceptionUtil;

/** Implements the global request handler resolver, which returns cached instances of request handler classes.
 * Functionality to actively override implementations is provided.
 *
 * Modules plugging into this, for example cache layers for resolvers, would first obtain the default implementation via the getHandlerInstance
 * method, and then plug in their wrapper using the setHandlerInstance method.
 *
 * If the default naming functionality should be altered, then this class must be extended and the interface's default implementation
 * of getRequestHandlerClassname overridden.
 */
@Singleton
public class DefaultRequestHandlerResolver implements IRequestHandlerResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultRequestHandlerResolver.class);
    private final Map<String, IRequestHandler<?>> cachedHandlerInstances = new ConcurrentHashMap<String, IRequestHandler<?>>(250);

    /** Obtains a cached instance of a request handler, or creates a default one. */
    @Override
    public <RQ extends RequestParameters> IRequestHandler<RQ> getHandlerInstance(Class<RQ> requestClass) {
        IRequestHandler<?> instance = cachedHandlerInstances.get(requestClass.getCanonicalName());
        if (instance == null) {
            // create a new instance. It is accepted that in the beginning at application start, due to race conditions, multiple
            // instances of the same handler may be created and subsequently used. Long term, the last stored instance will be used
            // by subsequent calls.
            try {
                Class<?> handlerClass = Class.forName(getRequestHandlerClassname(requestClass));
                if (!IRequestHandler.class.isAssignableFrom(handlerClass)) {
                    LOGGER.error("Class {} is not a request handler class (i.e. does not implement the IRequestHandler interface", handlerClass.getCanonicalName());
                    instance = new NoHandlerPresentRequestHandler(handlerClass.getCanonicalName() + " does not implement IRequestHandler<?>");
                } else {
                    instance = (IRequestHandler<?>) handlerClass.newInstance();
                }
            } catch (Exception e) {
                String causeChain = ExceptionUtil.causeChain(e);
                LOGGER.error("Required request handler class {} for {} not found, creating an exception handler. [Reason: {}]",
                        getRequestHandlerClassname(requestClass), requestClass.getCanonicalName(), causeChain);
                instance = new NoHandlerPresentRequestHandler(causeChain);
            }
            cachedHandlerInstances.put(requestClass.getCanonicalName(), instance);
        }
        return (IRequestHandler<RQ>) instance;
    }

    /** Defines a new request handler for subsequent use. */
    @Override
    public <RQ extends RequestParameters> void setHandlerInstance(Class<RQ> requestClass, IRequestHandler<RQ> newInstance) {
        cachedHandlerInstances.put(requestClass.getCanonicalName(), newInstance);
    }
}
