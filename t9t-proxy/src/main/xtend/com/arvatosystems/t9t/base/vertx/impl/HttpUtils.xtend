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
package com.arvatosystems.t9t.base.vertx.impl

import io.vertx.core.http.HttpClient
import io.vertx.core.http.HttpServerRequest
import io.vertx.core.http.HttpServerResponse
import io.vertx.ext.web.Router
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static io.vertx.core.http.HttpHeaders.*
import io.vertx.core.MultiMap
import io.vertx.core.http.HttpHeaders

class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils)
    static final String ALLOWED_CORS_HEADERS = '''«CONTENT_TYPE», Charset, «ACCEPT_CHARSET», «CONTENT_LENGTH», «AUTHORIZATION», «ACCEPT», «USER_AGENT», X-Invoker-Ref, X-Plan-Date''';

    static final String EXT_CT = "application/json"
    static final String INT_CT_42 = "application/bonjson"   // must propagate a different content type to fortytwo

    def static void addHttpHeaderForCorsOptionsRequest(HttpServerResponse it, String origin) {
        putHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin)
        putHeader(ACCESS_CONTROL_ALLOW_METHODS, "POST")
        putHeader(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_CORS_HEADERS)
        putHeader(CONTENT_TYPE, "text/html; charset=utf-8")
    }

    def static void addCorsOptionsRouter(Router router, String path) {
        router.options(path).handler [
            val origin      = request.headers.get(ORIGIN)
            val rqMethod    = request.headers.get(ACCESS_CONTROL_REQUEST_METHOD)
            val rqHeaders   = request.headers.get(ACCESS_CONTROL_REQUEST_HEADERS) ?: ""
            LOGGER.debug("CORS preflight request to {} for origin {}, method {}, headers {} received", path, origin, rqMethod, rqHeaders)
            if (origin !== null && rqMethod !== null) {
                response.addHttpHeaderForCorsOptionsRequest(origin)
            }
            response.end
        ]
    }

    def static stripCharset(String s) {
        if (s === null)
            return s;
        val ind = s.indexOf(';')
        if (ind >= 0)
            return s.substring(0, ind)      // strip off optional ";Charset..." portion
        else
            return s
    }

    def private static void map(MultiMap map, String from, String to) {
        val ct = map.get(HttpHeaders.CONTENT_TYPE)
        if (ct !== null && ct.equals(from)) {
            // exchange
            map.set(HttpHeaders.CONTENT_TYPE, to)

            // TODO: have to map ACCEPT as well?
            val acc = map.get(HttpHeaders.ACCEPT)
            if (acc !== null)
                map.set(HttpHeaders.ACCEPT, to)
        }
    }

    def static void proxy(HttpClient client, HttpServerRequest req, String host, int port, boolean mapContentType, String uriPrefix) {
        val c_req = client.request(req.method, port, host, uriPrefix + req.uri, [ c_res |
            System.out.println("Proxying response: " + c_res.statusCode());
            req.response().setChunked(true);
            req.response().setStatusCode(c_res.statusCode());
            req.response().headers().setAll(c_res.headers());
            if (mapContentType)
                req.response().headers.map(INT_CT_42, EXT_CT)
            c_res.handler [ req.response().write(it) ]
            c_res.endHandler [ req.response().end() ]
        ])
        c_req.setChunked(true);
        c_req.headers().setAll(req.headers());
        if (mapContentType)
            c_req.headers.map(EXT_CT, INT_CT_42)
        req.handler [ c_req.write(it) ]
        req.endHandler [ c_req.end() ]
    }
}
