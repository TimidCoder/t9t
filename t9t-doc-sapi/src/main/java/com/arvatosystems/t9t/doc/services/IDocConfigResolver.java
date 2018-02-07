package com.arvatosystems.t9t.doc.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.doc.DocConfigDTO;
import com.arvatosystems.t9t.doc.DocConfigRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface IDocConfigResolver extends RefResolver<DocConfigRef, DocConfigDTO, FullTrackingWithVersion> {}
