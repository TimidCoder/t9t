package com.arvatosystems.t9t.doc.services;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.arvatosystems.t9t.doc.api.DocumentSelector;
import com.arvatosystems.t9t.email.api.RecipientEmail;

import de.jpaw.bonaparte.pojos.api.media.MediaData;
import de.jpaw.bonaparte.pojos.api.media.MediaXType;

public interface IDocEmailDistributor {
    void transmit(
        RecipientEmail      rcpt,
        Function<MediaXType, MediaData> toFormatConverter,
        MediaXType          primaryFormat,
        String              documentTemplateId,      // the unmapped template ID
        DocumentSelector    documentSelector,
        MediaData           emailSubject,
        MediaData           emailBody,
        Map<String,MediaData> cids,
        MediaData           alternateBody,
        List<MediaData>     attachments,
        boolean             storeEmail,
        boolean             sendSpooled
    );
}
