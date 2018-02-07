package com.arvatosystems.t9t.base.services;

public interface IFileUtil {
    public String getAbsolutePathForTenant(String tenantId, String relativePath);
    public String getAbsolutePath(String relativePath);
    public String getFilePathPrefix();
    public boolean createFileLocation(String pathToFile);
    public String buildPath(String... pathElements);
}
