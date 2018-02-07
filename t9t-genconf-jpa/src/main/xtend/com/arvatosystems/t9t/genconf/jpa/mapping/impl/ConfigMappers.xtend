package com.arvatosystems.t9t.genconf.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.genconf.ConfigDTO
import com.arvatosystems.t9t.genconf.jpa.entities.ConfigEntity
import com.arvatosystems.t9t.genconf.jpa.persistence.IConfigEntityResolver

@AutoMap42
public class ConfigMappers {
    IConfigEntityResolver resolver
    def void e2dConfigDTO(ConfigEntity entity, ConfigDTO dto) {}
}
