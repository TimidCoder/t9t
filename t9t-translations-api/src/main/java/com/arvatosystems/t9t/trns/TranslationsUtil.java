package com.arvatosystems.t9t.trns;

import com.arvatosystems.t9t.base.trns.TextCategory;
import com.arvatosystems.t9t.base.trns.TranslationsPartialKey;

public class TranslationsUtil {
    private TranslationsUtil() {}

    public static String getKey(TranslationsDTO dto) {
        if (dto.getCategory() == TextCategory.DEFAULT)
            return dto.getId();
        return dto.getCategory().getToken() + "." + dto.getQualifier() + "." + dto.getId();
    }
    public static String getKey(TranslationsPartialKey dto) {
        if (dto.getCategory() == TextCategory.DEFAULT)
            return dto.getId();
        return dto.getCategory().getToken() + "." + dto.getQualifier() + "." + dto.getId();
    }
}
