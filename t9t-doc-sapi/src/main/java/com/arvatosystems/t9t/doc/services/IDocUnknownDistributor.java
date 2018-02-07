package com.arvatosystems.t9t.doc.services;

import java.util.function.Function;

import com.arvatosystems.t9t.base.types.Recipient;
import com.arvatosystems.t9t.doc.api.DocumentSelector;

import de.jpaw.bonaparte.pojos.api.media.MediaData;
import de.jpaw.bonaparte.pojos.api.media.MediaXType;

public interface IDocUnknownDistributor {
    void transmit(
        Recipient rcpt,
        Function<MediaXType, MediaData> data,
        MediaXType primaryFormat,
        String documentTemplateId,      // the unmapped template ID
        DocumentSelector documentSelector
    );
}
