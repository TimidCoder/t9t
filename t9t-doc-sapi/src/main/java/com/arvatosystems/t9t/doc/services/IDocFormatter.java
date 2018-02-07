package com.arvatosystems.t9t.doc.services;

import java.util.Map;

import com.arvatosystems.t9t.doc.api.DocumentSelector;
import com.arvatosystems.t9t.doc.api.TemplateType;

import de.jpaw.bonaparte.pojos.api.media.MediaData;

public interface IDocFormatter {
    MediaData formatDocument(
        Long sharedTenantRef,
        TemplateType templateType,
        String template,
        DocumentSelector selector,
        String overrideTimeZone,
        Object data,
        Map<String, MediaData> cidMap // if null, the binary data will be inlined in HTML without conversion, otherwise stored here
    );
}
