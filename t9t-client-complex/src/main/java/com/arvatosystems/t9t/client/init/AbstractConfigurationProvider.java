package com.arvatosystems.t9t.client.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// just Java boilerplate code...
public abstract class AbstractConfigurationProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConfigurationProvider.class);

    public static final int    DEFAULT_REMOTE_PORT = 8024;
    public static final String DEFAULT_REMOTE_HOST = "localhost";
    public static final String DEFAULT_REQUEST_PATH = "/rpc";
    public static final String DEFAULT_AUTHENTICATION_PATH = "/login";

    private final int remotePort;
    private final String remoteHost;
    private final String remotePathRequests;
    private final String remotePathAuthentication;

    protected AbstractConfigurationProvider() {
        this("DEFAULTS", null, null, null, null);
    }
    protected AbstractConfigurationProvider(
      String source,
      String remotePort,
      String remoteHost,
      String remotePathRequests,
      String remotePathAuthentication) {
        this.remotePort = remotePort == null ? DEFAULT_REMOTE_PORT : Integer.parseInt(remotePort);
        this.remoteHost = remoteHost == null ? DEFAULT_REMOTE_HOST : remoteHost;
        this.remotePathRequests = remotePathRequests == null ? DEFAULT_REQUEST_PATH : remotePathRequests;
        this.remotePathAuthentication = remotePathAuthentication == null ? DEFAULT_AUTHENTICATION_PATH : remotePathAuthentication;
        LOGGER.info("Configuration loaded via {} for remote http://{}:{}/{} (login via {})",
                source, this.remoteHost, this.remotePort, this.remotePathRequests, this.remotePathAuthentication);
    }

    public int getRemotePort() {
        return remotePort;
    }
    public String getRemoteHost() {
        return remoteHost;
    }
    public String getRemotePathRequests() {
        return remotePathRequests;
    }
    public String getRemotePathAuthentication() {
        return remotePathAuthentication;
    }
}
