package com.arvatosystems.t9t.doc.jpa.impl;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;
import com.arvatosystems.t9t.doc.DocComponentDTO;
import com.arvatosystems.t9t.doc.DocComponentRef;
import com.arvatosystems.t9t.doc.jpa.entities.DocComponentEntity;
import com.arvatosystems.t9t.doc.jpa.mapping.IDocComponentDTOMapper;
import com.arvatosystems.t9t.doc.jpa.persistence.IDocComponentEntityResolver;
import com.arvatosystems.t9t.doc.services.IDocComponentResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class DocComponentResolver extends AbstractJpaResolver<DocComponentRef, DocComponentDTO, FullTrackingWithVersion, DocComponentEntity>
    implements IDocComponentResolver {

    public DocComponentResolver() {
        super("DocComponent", Jdp.getRequired(IDocComponentEntityResolver.class), Jdp.getRequired(IDocComponentDTOMapper.class));
    }

    @Override
    public DocComponentRef createKey(Long ref) {
        return ref == null ? null : new DocComponentRef(ref);
    }
}
