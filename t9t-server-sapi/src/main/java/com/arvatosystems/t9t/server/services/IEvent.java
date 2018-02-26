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
package com.arvatosystems.t9t.server.services;

import com.arvatosystems.t9t.base.types.XTargetChannelType;

import de.jpaw.bonaparte.pojos.api.media.MediaData;

public interface IEvent {
    /** Sends data to the specified target. Just checks availability of the target if data is null.
     * The target is specied as a string, starting with the token of the
     *
     * Writing data requires authorization for the target.
     *  */
    void asyncEvent(XTargetChannelType channel, String address, MediaData data);
}