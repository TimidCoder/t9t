package com.arvatosystems.t9t.doc.jpa.impl;

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver;
import com.arvatosystems.t9t.doc.DocTemplateDTO;
import com.arvatosystems.t9t.doc.DocTemplateRef;
import com.arvatosystems.t9t.doc.jpa.entities.DocTemplateEntity;
import com.arvatosystems.t9t.doc.jpa.mapping.IDocTemplateDTOMapper;
import com.arvatosystems.t9t.doc.jpa.persistence.IDocTemplateEntityResolver;
import com.arvatosystems.t9t.doc.services.IDocTemplateResolver;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

@Singleton
public class DocTemplateResolver extends AbstractJpaResolver<DocTemplateRef, DocTemplateDTO, FullTrackingWithVersion, DocTemplateEntity>
    implements IDocTemplateResolver {

    public DocTemplateResolver() {
        super("DocTemplate", Jdp.getRequired(IDocTemplateEntityResolver.class), Jdp.getRequired(IDocTemplateDTOMapper.class));
    }

    @Override
    public DocTemplateRef createKey(Long ref) {
        return ref == null ? null : new DocTemplateRef(ref);
    }
}
