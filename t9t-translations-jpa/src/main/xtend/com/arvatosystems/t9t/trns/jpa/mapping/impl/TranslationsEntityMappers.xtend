package com.arvatosystems.t9t.trns.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.trns.TranslationsDTO
import com.arvatosystems.t9t.trns.jpa.entities.TranslationsEntity
import com.arvatosystems.t9t.trns.jpa.persistence.ITranslationsEntityResolver

@AutoMap42
public class TranslationsEntityMappers {
    ITranslationsEntityResolver translationsResolver

    @AutoHandler("CSP42")
    def void e2dTranslationsDTO(TranslationsEntity entity, TranslationsDTO dto) {
    }
}
