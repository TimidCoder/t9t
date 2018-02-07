package com.arvatosystems.t9t.io.be.request

import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler
import com.arvatosystems.t9t.base.search.Description
import com.arvatosystems.t9t.io.jpa.entities.CsvConfigurationEntity
import com.arvatosystems.t9t.io.jpa.persistence.ICsvConfigurationEntityResolver
import com.arvatosystems.t9t.io.request.LeanCsvConfigurationSearchRequest
import de.jpaw.dp.Jdp

class LeanCsvConfigurationSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanCsvConfigurationSearchRequest, CsvConfigurationEntity> {
    public new() {
        super(Jdp.getRequired(ICsvConfigurationEntityResolver),
            [ return new Description(null, csvConfigurationId, description ?: '?', false, false) ]
        )
    }
}
