package com.arvatosystems.t9t.doc.services;

import java.util.Map;

import com.arvatosystems.t9t.doc.DocConfigDTO;
import com.arvatosystems.t9t.doc.DocEmailCfgDTO;
import com.arvatosystems.t9t.doc.DocModuleCfgDTO;
import com.arvatosystems.t9t.doc.DocTemplateDTO;
import com.arvatosystems.t9t.doc.api.DocumentSelector;

import de.jpaw.bonaparte.pojos.api.media.MediaData;

/** Defines the communication layer between the backend modules (business logic / persistence layer). */
public interface IDocPersistenceAccess {
    DocConfigDTO                    getDocConfigDTO     (String templateId);
    DocEmailCfgDTO                  getDocEmailCfgDTO   (DocModuleCfgDTO cfg, String templateId, DocumentSelector selector);
    DocTemplateDTO                  getDocTemplateDTO   (DocModuleCfgDTO cfg, String templateId, DocumentSelector selector);
    Map<String,MediaData>           getDocComponents    (DocModuleCfgDTO cfg, DocumentSelector selector);
}
