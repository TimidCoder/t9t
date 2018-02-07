package com.arvatosystems.t9t.misc.extensions

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.base.crud.CrudSurrogateKeyResponse
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.voice.VoiceApplicationDTO
import com.arvatosystems.t9t.voice.VoiceApplicationKey
import com.arvatosystems.t9t.voice.VoiceResponseDTO
import com.arvatosystems.t9t.voice.VoiceResponseKey
import com.arvatosystems.t9t.voice.VoiceUserDTO
import com.arvatosystems.t9t.voice.VoiceUserKey
import com.arvatosystems.t9t.voice.request.VoiceApplicationCrudRequest
import com.arvatosystems.t9t.voice.request.VoiceResponseCrudRequest
import com.arvatosystems.t9t.voice.request.VoiceUserCrudRequest
import de.jpaw.bonaparte.pojos.api.OperationType

class VoiceExtensions {
    // extension methods for the types with surrogate keys
    def static CrudSurrogateKeyResponse<VoiceApplicationDTO, FullTrackingWithVersion> merge(VoiceApplicationDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new VoiceApplicationCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new VoiceApplicationKey(dto.applicationId)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<VoiceResponseDTO, FullTrackingWithVersion> merge(VoiceResponseDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new VoiceResponseCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new VoiceResponseKey(dto.applicationRef, dto.languageCode, dto.key)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<VoiceUserDTO, FullTrackingWithVersion> merge(VoiceUserDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new VoiceUserCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new VoiceUserKey(dto.applicationRef, dto.providerId)
        ], CrudSurrogateKeyResponse)
    }
}
