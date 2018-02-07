package com.arvatosystems.t9t.server.services;

import java.util.List;

import com.arvatosystems.t9t.base.trns.TextCategory;
import com.arvatosystems.t9t.base.trns.TranslationsPartialKey;

/** Cross module access for the translations module. Only these APIs are used (maybe only one of both). */
public interface ITranslator {
    List<String> getTranslations(String languageCode, TextCategory category, List<String> qualifiedIds);
    List<String> getTranslations(String languageCode, List<TranslationsPartialKey> keys);
}
