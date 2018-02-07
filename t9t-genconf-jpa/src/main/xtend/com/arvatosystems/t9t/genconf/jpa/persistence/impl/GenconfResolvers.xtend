package com.arvatosystems.t9t.genconf.jpa.persistence.impl

import com.arvatosystems.t9t.annotations.jpa.AllCanAccessGlobalTenant
import com.arvatosystems.t9t.annotations.jpa.AutoResolver42
import com.arvatosystems.t9t.genconf.ConfigRef
import com.arvatosystems.t9t.genconf.jpa.entities.ConfigEntity
import java.util.List

@AutoResolver42
class GenconfResolvers {

    @AllCanAccessGlobalTenant  // for DataSinkEntity, everyone can see the global tenant's defaults
    def ConfigEntity   getConfigEntity (ConfigRef  entityRef, boolean onlyActive) { return null; }
    def ConfigEntity   findByKey(boolean onlyActive, Long tenantRef, String configGroup, String configKey, Long genericRef1, Long genericRef2) { return null; }
    def List<ConfigEntity> findByGroup(boolean onlyActive, Long tenantRef, String configGroup) { return null; }
    def List<ConfigEntity> findByKeyWithDefault(boolean onlyActive, String configGroup, String configKey, Long genericRef1, Long genericRef2) { return null; }
}
