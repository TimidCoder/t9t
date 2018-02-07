package com.arvatosystems.t9t.io.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AllCanAccessGlobalTenant
import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.io.CsvConfigurationRef
import com.arvatosystems.t9t.io.DataSinkRef
import com.arvatosystems.t9t.io.OutboundMessageRef
import com.arvatosystems.t9t.io.SinkRef
import com.arvatosystems.t9t.io.jpa.entities.CsvConfigurationEntity
import com.arvatosystems.t9t.io.jpa.entities.DataSinkEntity
import com.arvatosystems.t9t.io.jpa.entities.OutboundMessageEntity
import com.arvatosystems.t9t.io.jpa.entities.SinkEntity
import java.util.List
import com.arvatosystems.t9t.annotations.jpa.GlobalTenantCanAccessAll

@AutoResolver42
class IOResolvers {
    @GlobalTenantCanAccessAll   // required for Camel startup
    @AllCanAccessGlobalTenant   // must allow read access to global defaults
    def CsvConfigurationEntity       getCsvConfigurationEntity          (CsvConfigurationRef entityRef, boolean onlyActive) { return null; }
    def List<CsvConfigurationEntity> findByCsvConfigurationIdWithDefault(boolean onlyActive, String csvConfigurationId)     { return null; }

    @GlobalTenantCanAccessAll   // required for Camel startup
    @AllCanAccessGlobalTenant   // for DataSinkEntity, everyone can see the global tenant's defaults
    def DataSinkEntity          getDataSinkEntity           (DataSinkRef  entityRef, boolean onlyActive) { return null; }
    def List<DataSinkEntity>    findByDataSinkIdWithDefault (boolean onlyActive, String dataSinkId) { return null; }

    def SinkEntity              getSinkEntity               (SinkRef      entityRef, boolean onlyActive) { return null; }

    def OutboundMessageEntity   getOutboundMessageEntity    (OutboundMessageRef entityRef, boolean onlyActive) { return null; }
}
