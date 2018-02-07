package com.arvatosystems.t9t.doc.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.doc.DocTemplateDTO;
import com.arvatosystems.t9t.doc.DocTemplateRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface IDocTemplateResolver extends RefResolver<DocTemplateRef, DocTemplateDTO, FullTrackingWithVersion> {}
