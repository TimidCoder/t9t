package com.arvatosystems.t9t.auth.jpa.impl

import com.arvatosystems.t9t.auth.TenantLogoDTO
import com.arvatosystems.t9t.auth.jpa.entities.TenantLogoEntity
import com.arvatosystems.t9t.auth.jpa.persistence.ITenantLogoEntityResolver
import com.arvatosystems.t9t.auth.services.ITenantLogoDtoResolver
import com.arvatosystems.t9t.core.jpa.impl.AbstractModuleConfigResolver
import de.jpaw.bonaparte.api.media.MediaTypes
import de.jpaw.bonaparte.pojos.api.media.MediaData
import de.jpaw.dp.Singleton
import de.jpaw.util.ByteArray
import java.nio.charset.StandardCharsets

@Singleton
class TenantLogoDtoResolver extends AbstractModuleConfigResolver<TenantLogoDTO, TenantLogoEntity> implements ITenantLogoDtoResolver {
    private static final byte [] TRANSPARENT_1X1 =
        "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNkYAAAAAYAAjCB0C8AAAAASUVORK5CYII=".getBytes(StandardCharsets.UTF_8)
    private static final TenantLogoDTO DEFAULT_MODULE_CFG = new TenantLogoDTO(
        null,                       // Json z
        new MediaData => [
            mediaType   = MediaTypes.MEDIA_XTYPE_PNG
            rawData     = ByteArray.fromBase64(TRANSPARENT_1X1, 0, TRANSPARENT_1X1.length)
        ]
    );

    public new() {
        super(ITenantLogoEntityResolver)
    }

    override public TenantLogoDTO getDefaultModuleConfiguration() {
        return DEFAULT_MODULE_CFG;
    }
}
