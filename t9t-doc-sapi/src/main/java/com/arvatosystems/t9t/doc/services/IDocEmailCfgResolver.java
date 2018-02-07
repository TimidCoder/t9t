package com.arvatosystems.t9t.doc.services;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.doc.DocEmailCfgDTO;
import com.arvatosystems.t9t.doc.DocEmailCfgRef;

import de.jpaw.bonaparte.refsw.RefResolver;

public interface IDocEmailCfgResolver extends RefResolver<DocEmailCfgRef, DocEmailCfgDTO, FullTrackingWithVersion> {}
