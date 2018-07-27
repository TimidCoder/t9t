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
package com.arvatosystems.t9t.out.services;

import com.arvatosystems.t9t.io.AsyncChannelDTO;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.HttpPostResponseObject;

/**
 * Interface to perform the last step of message transmission, sending the message to the external party (doing the actual http).
 * This is the last interface used in data flow, the one performing the final transmission.
 * */
public interface IAsyncSender<R extends BonaPortable> {
    /** Send a message to the remote client. */
    HttpPostResponseObject fireMessage(AsyncChannelDTO channelDto, int timeout, BonaPortable payload);

    /** Extract a logical return code from a client's response. */
    default Integer getClientReturnCode(R response) { return null; };

    /** Extract a logical object or method reference from a client's response. */
    default String getClientReference(R response) { return null; };
}
