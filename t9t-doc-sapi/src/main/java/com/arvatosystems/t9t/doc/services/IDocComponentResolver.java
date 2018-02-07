package com.arvatosystems.t9t.doc.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.doc.DocComponentDTO;
import com.arvatosystems.t9t.doc.DocComponentRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface IDocComponentResolver extends RefResolver<DocComponentRef, DocComponentDTO, FullTrackingWithVersion> {}
