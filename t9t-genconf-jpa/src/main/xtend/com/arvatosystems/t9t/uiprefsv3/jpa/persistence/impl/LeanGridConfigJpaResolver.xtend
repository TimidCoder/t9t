package com.arvatosystems.t9t.uiprefsv3.jpa.persistence.impl

import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.base.jpa.impl.AbstractJpaResolver
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigDTO
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigRef
import com.arvatosystems.t9t.uiprefsv3.jpa.entities.LeanGridConfigEntity
import com.arvatosystems.t9t.uiprefsv3.jpa.mapping.ILeanGridConfigDTOMapper
import com.arvatosystems.t9t.uiprefsv3.jpa.persistence.ILeanGridConfigEntityResolver
import com.arvatosystems.t9t.uiprefsv3.services.ILeanGridConfigResolver
import de.jpaw.dp.Default
import de.jpaw.dp.Jdp
import de.jpaw.dp.Singleton

@Default @Singleton
class LeanGridConfigJpaResolver extends AbstractJpaResolver<LeanGridConfigRef,LeanGridConfigDTO,FullTrackingWithVersion,LeanGridConfigEntity> implements ILeanGridConfigResolver {

    public new() {
        super("LeanGridConfig", Jdp.getRequired(ILeanGridConfigEntityResolver), Jdp.getRequired(ILeanGridConfigDTOMapper))
    }

    override public LeanGridConfigRef createKey(Long ref) {
        return if (ref !== null) new LeanGridConfigRef(ref);
    }
}
