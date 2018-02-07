package com.arvatosystems.t9t.genconf.jpa.persistence.impl

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver
import com.arvatosystems.t9t.genconf.ConfigDTO
import com.arvatosystems.t9t.genconf.ConfigRef
import com.arvatosystems.t9t.genconf.jpa.entities.ConfigEntity
import com.arvatosystems.t9t.genconf.jpa.mapping.IConfigDTOMapper
import com.arvatosystems.t9t.genconf.jpa.persistence.IConfigEntityResolver
import com.arvatosystems.t9t.genconf.services.IConfigResolver
import de.jpaw.dp.Default
import de.jpaw.dp.Jdp
import de.jpaw.dp.Singleton

@Default @Singleton
class GenconfJpaResolver extends AbstractJpaResolver<ConfigRef,ConfigDTO,FullTrackingWithVersion,ConfigEntity> implements IConfigResolver {

    public new() {
        super("Config", Jdp.getRequired(IConfigEntityResolver), Jdp.getRequired(IConfigDTOMapper))
    }

    override public ConfigRef createKey(Long ref) {
        return if (ref !== null) new ConfigRef(ref);
    }
}
