package com.arvatosystems.t9t.uiprefsv3.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigDTO
import com.arvatosystems.t9t.uiprefsv3.jpa.entities.LeanGridConfigEntity
import com.arvatosystems.t9t.uiprefsv3.jpa.persistence.ILeanGridConfigEntityResolver

@AutoMap42
public class LeanGridConfigMappers {
    ILeanGridConfigEntityResolver resolver
    def void e2dLeanGridConfigDTO(LeanGridConfigEntity entity, LeanGridConfigDTO dto) {}
}
