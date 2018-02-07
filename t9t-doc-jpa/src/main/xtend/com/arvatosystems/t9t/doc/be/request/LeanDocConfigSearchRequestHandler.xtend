package com.arvatosystems.t9t.doc.be.request

import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import com.arvatosystems.t9t.doc.jpa.entities.DocConfigEntity
import com.arvatosystems.t9t.doc.jpa.persistence.IDocConfigEntityResolver
import com.arvatosystems.t9t.doc.request.LeanDocConfigSearchRequest
import de.jpaw.dp.Jdp

class LeanDocConfigSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanDocConfigSearchRequest, DocConfigEntity> {
    public new() {
        super(Jdp.getRequired(IDocConfigEntityResolver),
            [ return new Description(null, documentId, description, false, false) ]
        )
    }
}
