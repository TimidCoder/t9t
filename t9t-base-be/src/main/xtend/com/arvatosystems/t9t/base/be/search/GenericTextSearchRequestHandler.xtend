package com.arvatosystems.t9t.base.be.search

import com.arvatosystems.t9t.base.search.GenericTextSearchRequest
import com.arvatosystems.t9t.base.search.GenericTextSearchResponse
import com.arvatosystems.t9t.base.services.AbstractSearchRequestHandler
import com.arvatosystems.t9t.base.services.ITextSearch
import com.arvatosystems.t9t.base.services.RequestContext
import de.jpaw.dp.Inject

class GenericTextSearchRequestHandler extends AbstractSearchRequestHandler<GenericTextSearchRequest> {
    @Inject ITextSearch engine

    override GenericTextSearchResponse execute(RequestContext ctx, GenericTextSearchRequest rq) {
        return new GenericTextSearchResponse => [
            results = engine.search(ctx, rq, rq.documentName, rq.resultFieldName)
        ]
    }
}
