package com.arvatosystems.t9t.io.services;

import java.io.InputStream;

import com.arvatosystems.t9t.base.services.RequestContext;

public interface IMediaDataSource {
    default String getAbsolutePath(String relativePath, RequestContext ctx) { return relativePath; }
    InputStream open(String path) throws Exception;
    default boolean hasMore(InputStream is) throws Exception { return false; }  // return true if the source was not yet read completely - not supported by most sources
}
