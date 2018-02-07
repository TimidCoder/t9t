package com.arvatosystems.t9t.solr.be;

import de.jpaw.bonaparte.pojos.api.SearchFilter;

/**
 * A converter for various filter types. This implementation uses xtend dispatcher methods.
 */
public interface IFilterToSolrConverter {

    /**
     * Converts a search filter to a solr expression
     *
     * @param a
     *            search filter
     * @return the equivalent solr search expression
     */
    String toSolrCondition(SearchFilter criteria);
}
