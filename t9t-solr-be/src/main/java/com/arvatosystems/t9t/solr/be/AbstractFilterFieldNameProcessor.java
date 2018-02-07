package com.arvatosystems.t9t.solr.be;

import java.util.Map;

public abstract class AbstractFilterFieldNameProcessor implements IFilterFieldNameProcessor {

    @Override
    public String processFieldName(String fieldName) {
        Map<String,String> fieldNames = getFieldNameMappings();
        String destValue = fieldNames.get(fieldName);
        if (destValue != null) {
            return destValue;
        }
        else {
            return fieldName;
        }
    }


    public abstract Map<String,String> getFieldNameMappings();
}
