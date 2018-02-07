package com.arvatosystems.t9t.trns.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.trns.TrnsModuleCfgDTO
import com.arvatosystems.t9t.trns.jpa.entities.TrnsModuleCfgEntity
import com.arvatosystems.t9t.trns.jpa.persistence.ITrnsModuleCfgEntityResolver

@AutoMap42
class TrnsModuleCfgEntityMapper {
    ITrnsModuleCfgEntityResolver entityResolver

    @AutoHandler("SP42")
    def void d2eTrnsModuleCfgDTO(TrnsModuleCfgEntity entity, TrnsModuleCfgDTO dto, boolean onlyActive) {}
    def void e2dTrnsModuleCfgDTO(TrnsModuleCfgEntity entity, TrnsModuleCfgDTO dto) {}

}
