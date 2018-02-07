package com.arvatosystems.t9t.base.services;

import java.util.List;

import com.arvatosystems.t9t.base.search.SearchCriteria;

/** Interface which allows the extension of text search using an engine like SOLR or ElasticSearch.
 *
 * @author BISC02
 *
 */
public interface ITextSearch {
    /** Method provides the list of primary keys to data objects. */
    List<Long> search(RequestContext ctx, SearchCriteria sc, String documentName, String resultFieldName);
}
