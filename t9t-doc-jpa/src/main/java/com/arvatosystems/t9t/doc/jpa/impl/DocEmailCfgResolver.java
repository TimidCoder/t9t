package com.arvatosystems.t9t.doc.jpa.impl;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;
import com.arvatosystems.t9t.doc.DocEmailCfgDTO;
import com.arvatosystems.t9t.doc.DocEmailCfgRef;
import com.arvatosystems.t9t.doc.jpa.entities.DocEmailCfgEntity;
import com.arvatosystems.t9t.doc.jpa.mapping.IDocEmailCfgDTOMapper;
import com.arvatosystems.t9t.doc.jpa.persistence.IDocEmailCfgEntityResolver;
import com.arvatosystems.t9t.doc.services.IDocEmailCfgResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class DocEmailCfgResolver extends AbstractJpaResolver<DocEmailCfgRef, DocEmailCfgDTO, FullTrackingWithVersion, DocEmailCfgEntity>
    implements IDocEmailCfgResolver {

    public DocEmailCfgResolver() {
        super("DocEmailCfg", Jdp.getRequired(IDocEmailCfgEntityResolver.class), Jdp.getRequired(IDocEmailCfgDTOMapper.class));
    }

    @Override
    public DocEmailCfgRef createKey(Long ref) {
        return ref == null ? null : new DocEmailCfgRef(ref);
    }
}
