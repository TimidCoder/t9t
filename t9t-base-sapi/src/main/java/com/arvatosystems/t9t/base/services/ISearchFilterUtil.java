package com.arvatosystems.t9t.base.services;

import java.util.Set;

import de.jpaw.bonaparte.pojos.api.SearchFilter;

/**
 * search filter utility class
 */
public interface ISearchFilterUtil {

    /**
     * reduce a more general SearchFilter to only contain a certain wantedFieldNames. Correct predicates will be preserved
     * @return the more specific SearchFilter
     */
    SearchFilter selectFiltersBasedOnFieldName(SearchFilter searchFilter, Set<String> fieldNamesWanted);

}
