package com.arvatosystems.t9t.trns.services;

import java.util.List;

import com.arvatosystems.t9t.trns.TranslationsDTO;

public interface ITrnsPersistenceAccess {
    List<TranslationsDTO> readLanguage(Long tenantRef, String languageCode);
}
