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
