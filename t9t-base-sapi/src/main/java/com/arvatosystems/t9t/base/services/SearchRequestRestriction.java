package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.search.SearchCriteria;
import com.arvatosystems.t9t.base.search.SearchRequest;

/**
 * Define restriction that can be applied for a particular {@linkplain SearchRequest}.
 * @author LIEE001
 */
public interface SearchRequestRestriction {

    /**
     * Apply search restriction to the request.
     * @param searchRequest target search request
     * @throws T9tException if any error accessing persistence
     */
    void apply(RequestContext ctx, SearchCriteria searchRequest);
}
