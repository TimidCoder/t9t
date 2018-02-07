package com.arvatosystems.t9t.doc.jpa.impl;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;
import com.arvatosystems.t9t.doc.DocConfigDTO;
import com.arvatosystems.t9t.doc.DocConfigRef;
import com.arvatosystems.t9t.doc.jpa.entities.DocConfigEntity;
import com.arvatosystems.t9t.doc.jpa.mapping.IDocConfigDTOMapper;
import com.arvatosystems.t9t.doc.jpa.persistence.IDocConfigEntityResolver;
import com.arvatosystems.t9t.doc.services.IDocConfigResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class DocConfigResolver extends AbstractJpaResolver<DocConfigRef, DocConfigDTO, FullTrackingWithVersion, DocConfigEntity>
    implements IDocConfigResolver {

    public DocConfigResolver() {
        super("DocConfig", Jdp.getRequired(IDocConfigEntityResolver.class), Jdp.getRequired(IDocConfigDTOMapper.class));
    }

    @Override
    public DocConfigRef createKey(Long ref) {
        return ref == null ? null : new DocConfigRef(ref);
    }
}
