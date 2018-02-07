package com.arvatosystems.t9t.base.services;

import de.jpaw.bonaparte.pojos.api.media.MediaData;
import de.jpaw.bonaparte.pojos.api.media.MediaTypeDescriptor;

public interface IEventImpl {
    void asyncEvent(String address, MediaData data, MediaTypeDescriptor description);
}
