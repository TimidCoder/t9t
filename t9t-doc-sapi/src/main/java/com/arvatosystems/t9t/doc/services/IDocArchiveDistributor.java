package com.arvatosystems.t9t.doc.services;

import java.util.function.Function;

import com.arvatosystems.t9t.doc.api.DocumentSelector;
import com.arvatosystems.t9t.doc.recipients.RecipientArchive;

import de.jpaw.bonaparte.pojos.api.media.MediaData;
import de.jpaw.bonaparte.pojos.api.media.MediaXType;

public interface IDocArchiveDistributor {
    // returns the sinkRef
    DocArchiveResult transmit(
        RecipientArchive rcpt,
        Function<MediaXType, MediaData> data,
        MediaXType primaryFormat,
        String documentTemplateId,      // the unmapped template ID
        DocumentSelector documentSelector
    );
}
