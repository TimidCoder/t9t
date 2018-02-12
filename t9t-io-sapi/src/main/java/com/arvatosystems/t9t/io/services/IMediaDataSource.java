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
package com.arvatosystems.t9t.io.services;

import java.io.InputStream;

import com.arvatosystems.t9t.base.services.RequestContext;

public interface IMediaDataSource {
    default String getAbsolutePath(String relativePath, RequestContext ctx) { return relativePath; }
    InputStream open(String path) throws Exception;
    default boolean hasMore(InputStream is) throws Exception { return false; }  // return true if the source was not yet read completely - not supported by most sources
}
