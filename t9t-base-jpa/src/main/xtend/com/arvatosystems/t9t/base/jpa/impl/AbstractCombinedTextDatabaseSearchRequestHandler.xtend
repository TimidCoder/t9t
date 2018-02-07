package com.arvatosystems.t9t.base.jpa.impl

import com.arvatosystems.t9t.base.jpa.IEntityMapper42
import com.arvatosystems.t9t.base.jpa.IResolverSurrogateKey42
import com.arvatosystems.t9t.base.search.ReadAllResponse
import com.arvatosystems.t9t.base.search.SearchFilterTypeEnum
import com.arvatosystems.t9t.base.search.SearchRequest
import com.arvatosystems.t9t.base.services.AbstractSearchRequestHandler
import com.arvatosystems.t9t.base.services.IExecutor
import com.arvatosystems.t9t.base.services.ISearchTools
import com.arvatosystems.t9t.base.services.ITextSearch
import com.arvatosystems.t9t.base.services.RequestContext
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.core.BonaPortableClass
import de.jpaw.bonaparte.jpa.BonaPersistableKey
import de.jpaw.bonaparte.jpa.BonaPersistableTracking
import de.jpaw.bonaparte.pojos.api.AndFilter
import de.jpaw.bonaparte.pojos.api.FieldFilter
import de.jpaw.bonaparte.pojos.api.LongFilter
import de.jpaw.bonaparte.pojos.api.SearchFilter
import de.jpaw.bonaparte.pojos.api.TrackingBase
import de.jpaw.bonaparte.pojos.apiw.Ref
import de.jpaw.dp.Jdp
import java.util.List
import java.util.Map
import org.eclipse.xtend.lib.annotations.Data

@AddLogger
@Data
class AbstractCombinedTextDatabaseSearchRequestHandler<REF extends Ref, DTO extends REF, TRACKING extends TrackingBase, REQ extends SearchRequest<DTO, TRACKING>, ENTITY extends BonaPersistableKey<Long> & BonaPersistableTracking<TRACKING>> extends AbstractSearchRequestHandler<REQ> {
    protected final IExecutor executor = Jdp.getRequired(IExecutor)
    protected final ISearchTools searchTools = Jdp.getRequired(ISearchTools)
    protected final ITextSearch textSearch = Jdp.getRequired(ITextSearch)
    protected IResolverSurrogateKey42<REF, TRACKING, ENTITY> resolver
    protected IEntityMapper42<Long, DTO, TRACKING, ENTITY> mapper
    protected Map<SearchFilterTypeEnum, List<String>> textSearchPathElements
    protected Map<String, String> textSearchFieldMappings
    protected String documentName
    protected String keyFieldName
    protected BonaPortableClass<SearchRequest<DTO, TRACKING>> bclass

    override ReadAllResponse<DTO, TRACKING> execute(RequestContext ctx, REQ rq) {

        var processDecision = processPathElements(rq, textSearchPathElements)
        LOGGER.debug("processDecision>>"+processDecision+";req>>"+rq+" rq.expression.nullOrEmpty>"+rq.expression.nullOrEmpty)
        if (!rq.expression.nullOrEmpty || processDecision == SearchFilterTypeEnum.SOLR_ONLY) {
            // preprocess args for SOLR
            searchTools.mapNames(rq, textSearchFieldMappings)
            LOGGER.debug("solr only rq mapping>>"+rq)
            val refs = textSearch.search(ctx, rq, documentName, keyFieldName)

            // end here if there are no results - DB query would return an error
            if (refs.isEmpty)
                return new ReadAllResponse<DTO, TRACKING> => [
                    dataList = #[]
                ]
            // obtain DTOs for the refs
            val newSearchRq = bclass.newInstance => [
                searchFilter = new LongFilter("objectRef", null, null, null, refs)
                sortColumns = rq.sortColumns
                searchOutputTarget = rq.searchOutputTarget
            ]
            LOGGER.debug("solr only new searchReq>>"+newSearchRq)
            mapper.processSearchPrefixForDB(newSearchRq); // convert the field with searchPrefix
            return mapper.createReadAllResponse(resolver.search(newSearchRq, null),
                newSearchRq.getSearchOutputTarget());

        } else if (processDecision == SearchFilterTypeEnum.DB_ONLY) {
            // database search
            // preprocess prefixes for DB
            LOGGER.debug("DB only new Req>>"+rq)
            mapper.processSearchPrefixForDB(rq); // convert the field with searchPrefix
            // delegate to database search
            return mapper.createReadAllResponse(resolver.search(rq, null), rq.getSearchOutputTarget());
        } else { // BOTH case

            var solrRequest=bclass.newInstance
            var dbRequest = bclass.newInstance => [
                sortColumns = rq.sortColumns
                limit = rq.limit
            ]
            LOGGER.debug("Solr&DB before split->  Solr Req>>"+solrRequest+" DB req>>"+dbRequest)
            splitSearches(rq, solrRequest, dbRequest)
            LOGGER.debug("Solr&DB after split-> original req>>"+rq+" Solr Req>> "+solrRequest+" DB req>> "+dbRequest)
            var dbRequestFilter = dbRequest.searchFilter // save for later use
            LOGGER.debug("Solr&DB after split-> DB dbRequestFilter>> "+dbRequestFilter)
            searchTools.mapNames(solrRequest, textSearchFieldMappings)

            var resultsToSkip = rq.offset
            var increasedLimit = rq.limit*4
            val finalResultList = newArrayList
            var iteration = 0

            solrRequest.limit = increasedLimit

            for (var foundResults = 0; foundResults < rq.limit;) {
                solrRequest.offset = increasedLimit * iteration // consecutively increase the solr offset to include more results if they are needed

                val refs = textSearch.search(ctx, solrRequest, documentName, keyFieldName)
                // just return finalResultList if no more refs can be found with the given offset/limit.
                if (refs.isEmpty) return mapper.createReadAllResponse(finalResultList, rq.getSearchOutputTarget());
                if (dbRequestFilter !== null) {
                    dbRequest.searchFilter = new AndFilter(dbRequestFilter, new LongFilter => [
                        fieldName = "objectRef"
                        valueList = refs
                    ])
                } else {
                    dbRequest.searchFilter = new LongFilter => [
                        fieldName = "objectRef"
                        valueList = refs
                    ]
                }
                LOGGER.debug("Solr&DB after split->For loop-> dbRequest.searchFilter>> "+dbRequest.searchFilter)
                 // only query INITIAL_CALLER_LIMIT - RESULTS_FOUND + RESULTS_TO_SKIP. This will ensure we always get enough because a certain amount has to be skipped
                dbRequest.limit = rq.limit - foundResults + resultsToSkip

                mapper.processSearchPrefixForDB(dbRequest);
                // execute search and save result
                var tempResult = resolver.search(dbRequest, null)

                if (resultsToSkip > 0) { // if the caller supplied an offset we have to skip the first X (offset) results
                    var firstTempResult = tempResult.size
                    tempResult          = tempResult.drop(resultsToSkip).toList // drop the amount of results
                    resultsToSkip      -= firstTempResult
                    resultsToSkip       = Math.max(resultsToSkip, 0) // keep at 0
                }
                // this might be an empty list
                finalResultList.addAll(tempResult)

                foundResults+=tempResult.size // after dropping enough from the caller's offset we can save the results

                iteration++ // lastly increase iteration count

            }
            return mapper.createReadAllResponse(finalResultList, rq.getSearchOutputTarget());
        }
    }

    /**
     * Method splits the searchFilters in the original request into two requests that are solr only and dbOnly
     */
    def void splitSearches(SearchRequest<DTO, TRACKING> originalRequest, SearchRequest<DTO, TRACKING> solrRequest, SearchRequest<DTO, TRACKING> dbRequest) {
        var originalRequestSearchFilter = originalRequest.searchFilter
        var List<FieldFilter> solrRequestFilters = newArrayList
        var List<FieldFilter> dbRequestFilters = newArrayList
        // first filter and split
        decideFilterAssociation(originalRequestSearchFilter, solrRequestFilters, dbRequestFilters)
        // recreate filters with Ands
        solrRequest.searchFilter = recreateSearchTree(solrRequestFilters)
        dbRequest.searchFilter = recreateSearchTree(dbRequestFilters)

    }

    def SearchFilter recreateSearchTree(List<FieldFilter> fieldFilters) {
        if (fieldFilters.size === 1) {
            return fieldFilters.get(0)
        } else if (fieldFilters.size > 1) {
            var fieldFilter = fieldFilters.get(0)
            fieldFilters.remove(0)
            return new AndFilter(fieldFilter, recreateSearchTree(fieldFilters))
        } else {
            return null;
        }
    }

    /**
     * Decide what filter type the prefix relates to
     */
    def void decideFilterAssociation(SearchFilter originalFilter, List<FieldFilter> solrRequestFilters, List<FieldFilter> dbRequestFilters) {
        if (originalFilter instanceof FieldFilter) {
            if (textSearchPathElements.get(SearchFilterTypeEnum.DB_ONLY).contains(originalFilter.fieldName)) {
                dbRequestFilters.add(originalFilter)
            } else if (textSearchPathElements.get(SearchFilterTypeEnum.SOLR_ONLY).filter[originalFilter.fieldName.startsWith(it)].toList.size>0 ||
                textSearchPathElements.get(SearchFilterTypeEnum.BOTH).contains(originalFilter.fieldName)) {
                solrRequestFilters.add(originalFilter)
            }
        } else if (originalFilter instanceof AndFilter) {
            decideFilterAssociation(originalFilter.filter1, solrRequestFilters, dbRequestFilters)
            decideFilterAssociation(originalFilter.filter2, solrRequestFilters, dbRequestFilters)
        }
    }

    def SearchFilterTypeEnum processPathElements(SearchRequest<DTO, TRACKING> rq, Map<SearchFilterTypeEnum, List<String>> textSearchPathElements) {
        var dbOnly   = searchTools.containsFieldPathElements(rq, textSearchPathElements.get(SearchFilterTypeEnum.DB_ONLY))
        var solrOnly = searchTools.containsFieldPathElements(rq, textSearchPathElements.get(SearchFilterTypeEnum.SOLR_ONLY))
        if (dbOnly && solrOnly) {
            return SearchFilterTypeEnum.BOTH
        } else if (solrOnly) {
            return SearchFilterTypeEnum.SOLR_ONLY
        } else {
            return SearchFilterTypeEnum.DB_ONLY
        }
    }
}
