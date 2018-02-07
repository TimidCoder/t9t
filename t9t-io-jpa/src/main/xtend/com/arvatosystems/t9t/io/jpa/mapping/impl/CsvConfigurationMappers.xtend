package com.arvatosystems.t9t.io.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.io.CsvConfigurationDTO
import com.arvatosystems.t9t.io.jpa.entities.CsvConfigurationEntity
import com.arvatosystems.t9t.io.jpa.persistence.ICsvConfigurationEntityResolver

@AutoMap42
public class CsvConfigurationMappers {
    ICsvConfigurationEntityResolver entityResolver

    @AutoHandler("S42")
    def void e2dCsvConfigurationDTO         (CsvConfigurationEntity entity, CsvConfigurationDTO dto) {}
}
