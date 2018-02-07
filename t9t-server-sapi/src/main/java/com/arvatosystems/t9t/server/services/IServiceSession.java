package com.arvatosystems.t9t.server.services;

import java.io.Closeable;

import org.joda.time.Instant;

import com.arvatosystems.t9t.base.api.ServiceRequest;
import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.types.AuthenticationParameters;
import com.arvatosystems.t9t.base.types.SessionParameters;

/**
 * Represents a single session, its implementation is stateful, therefore of @Dependent scope.
 * Implementations can be client side (remoting requests to a stateless server, passing JWTs)
 * or server side (allowing clients with persistent connections to process series of requests).
 *
 * Authentication is either done as part of the open() (if appropriate parameters are passed),
 * or as the first request (login call).
 *
 * Deprecated - use IStatefulServiceSession instead.
 *
 */
@Deprecated
public interface IServiceSession extends Closeable {

    /**
     * Opens a connection. The parameters are either stored in the database, or just discarded (if the implementation performs another remoting).
     * If authentication parameters are passed and the authentication fails, the session is not opened and an exception thrown.
     * @param sessionParameters the session parameters
     */
    public void open(SessionParameters sessionParameters, AuthenticationParameters authenticationParameters);

    /**
     * Returns the state of the connection.
     *
     * @return {@code true} when the connection is open, {@code false} otherwise.
     */
    public boolean isOpen();

    /**
     * Returns the state of the connection.
     *
     * @return {@code null} if no valid authentication has been performed, otherwise the expiry timestamp of the related JWT.
     */
    public Instant authenticatedUntil();

    /**
     * Executes a service request.
     *
     * @param request a service request to be executed. Invokes the handler on the result, in case a handler has been provided.
     */
    public ServiceResponse execute(ServiceRequest request);

    /**
     * Method to gracefully shutdown the driver.
     * Ignored if the session is not open.
     * The interface extends {@code Closeable} and not {@code Autocloseable} in order to be Java 6 compatible.
     */
    @Override
    void close();
}
