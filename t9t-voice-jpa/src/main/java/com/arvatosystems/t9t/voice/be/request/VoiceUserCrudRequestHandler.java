package com.arvatosystems.t9t.voice.be.request;

import com.arvatosystems.t9t.base.crud.CrudSurrogateKeyResponse;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudSurrogateKey42RequestHandler;
import com.arvatosystems.t9t.voice.VoiceUserDTO;
import com.arvatosystems.t9t.voice.VoiceUserRef;
import com.arvatosystems.t9t.voice.jpa.entities.VoiceUserEntity;
import com.arvatosystems.t9t.voice.jpa.mapping.IVoiceUserDTOMapper;
import com.arvatosystems.t9t.voice.jpa.persistence.IVoiceUserEntityResolver;
import com.arvatosystems.t9t.voice.request.VoiceUserCrudRequest;
import de.jpaw.dp.Jdp;

public class VoiceUserCrudRequestHandler extends
        AbstractCrudSurrogateKey42RequestHandler<VoiceUserRef, VoiceUserDTO, FullTrackingWithVersion, VoiceUserCrudRequest, VoiceUserEntity> {
    protected final IVoiceUserEntityResolver resolver = Jdp.getRequired(IVoiceUserEntityResolver.class);

    protected final IVoiceUserDTOMapper mapper = Jdp.getRequired(IVoiceUserDTOMapper.class);

    @Override
    public CrudSurrogateKeyResponse<VoiceUserDTO, FullTrackingWithVersion> execute(final VoiceUserCrudRequest request)
            throws Exception {
        // if a DTO is provided, set the hash code as required
        VoiceUserDTO dto = request.getData();
        if (dto != null)
            dto.setProviderIdHash(dto.getProviderId().hashCode());
        return execute(mapper, resolver, request);
    }
}
