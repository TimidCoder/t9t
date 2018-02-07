package com.arvatosystems.t9t.misc.extensions

import com.arvatosystems.t9t.base.ITestConnection
import com.arvatosystems.t9t.base.crud.CrudModuleCfgResponse
import com.arvatosystems.t9t.base.crud.CrudSurrogateKeyResponse
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion
import com.arvatosystems.t9t.batch.SliceTrackingDTO
import com.arvatosystems.t9t.batch.SliceTrackingKey
import com.arvatosystems.t9t.batch.request.SliceTrackingCrudRequest
import com.arvatosystems.t9t.bucket.BucketCounterDTO
import com.arvatosystems.t9t.bucket.BucketCounterKey
import com.arvatosystems.t9t.bucket.request.BucketCounterCrudRequest
import com.arvatosystems.t9t.core.CannedRequestDTO
import com.arvatosystems.t9t.core.CannedRequestKey
import com.arvatosystems.t9t.core.request.CannedRequestCrudRequest
import com.arvatosystems.t9t.email.EmailModuleCfgDTO
import com.arvatosystems.t9t.email.request.EmailModuleCfgCrudRequest
import com.arvatosystems.t9t.event.ListenerConfigDTO
import com.arvatosystems.t9t.event.ListenerConfigKey
import com.arvatosystems.t9t.event.SubscriberConfigDTO
import com.arvatosystems.t9t.event.SubscriberConfigKey
import com.arvatosystems.t9t.event.request.ListenerConfigCrudRequest
import com.arvatosystems.t9t.event.request.SubscriberConfigCrudRequest
import com.arvatosystems.t9t.genconf.ConfigDTO
import com.arvatosystems.t9t.genconf.ConfigKey
import com.arvatosystems.t9t.genconf.request.ConfigCrudRequest
import com.arvatosystems.t9t.io.CsvConfigurationDTO
import com.arvatosystems.t9t.io.CsvConfigurationKey
import com.arvatosystems.t9t.io.DataSinkDTO
import com.arvatosystems.t9t.io.DataSinkKey
import com.arvatosystems.t9t.io.request.CsvConfigurationCrudRequest
import com.arvatosystems.t9t.io.request.DataSinkCrudRequest
import com.arvatosystems.t9t.rep.ReportConfigDTO
import com.arvatosystems.t9t.rep.ReportConfigKey
import com.arvatosystems.t9t.rep.ReportParamsDTO
import com.arvatosystems.t9t.rep.ReportParamsKey
import com.arvatosystems.t9t.rep.request.ReportConfigCrudRequest
import com.arvatosystems.t9t.rep.request.ReportParamsCrudRequest
import com.arvatosystems.t9t.ssm.SchedulerSetupDTO
import com.arvatosystems.t9t.ssm.SchedulerSetupKey
import com.arvatosystems.t9t.ssm.request.SchedulerSetupCrudRequest
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigDTO
import com.arvatosystems.t9t.uiprefsv3.LeanGridConfigKey
import com.arvatosystems.t9t.uiprefsv3.request.LeanGridConfigCrudRequest
import de.jpaw.bonaparte.pojos.api.OperationType

class MiscExtensions {

    // extension methods for the types with surrogate keys
    def static CrudSurrogateKeyResponse<DataSinkDTO, FullTrackingWithVersion> merge(DataSinkDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new DataSinkCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new DataSinkKey(dto.dataSinkId)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<CsvConfigurationDTO, FullTrackingWithVersion> merge(CsvConfigurationDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new CsvConfigurationCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new CsvConfigurationKey(dto.csvConfigurationId)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<SchedulerSetupDTO, FullTrackingWithVersion> merge(SchedulerSetupDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new SchedulerSetupCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new SchedulerSetupKey(dto.schedulerId)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<SchedulerSetupDTO, FullTrackingWithVersion> mergeReducedResponse(SchedulerSetupDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new SchedulerSetupCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new SchedulerSetupKey(dto.schedulerId)
            suppressResponseParameters = true
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<CannedRequestDTO, FullTrackingWithVersion> merge(CannedRequestDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new CannedRequestCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new CannedRequestKey(dto.requestId)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<CannedRequestDTO, FullTrackingWithVersion> mergeReducedResponse(CannedRequestDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new CannedRequestCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new CannedRequestKey(dto.requestId)
            suppressResponseParameters = true
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<SliceTrackingDTO, FullTrackingWithVersion> merge(SliceTrackingDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new SliceTrackingCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new SliceTrackingKey(dto.dataSinkId, dto.id)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<LeanGridConfigDTO, FullTrackingWithVersion> merge(LeanGridConfigDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new LeanGridConfigCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new LeanGridConfigKey(dto.gridId, dto.variant, dto.userRef)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<ConfigDTO, FullTrackingWithVersion> merge(ConfigDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new ConfigCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new ConfigKey(dto.configGroup, dto.configKey, dto.genericRef1, dto.genericRef2)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<ReportConfigDTO, FullTrackingWithVersion> merge(ReportConfigDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new ReportConfigCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new ReportConfigKey(dto.reportConfigId)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<ReportParamsDTO, FullTrackingWithVersion> merge(ReportParamsDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new ReportParamsCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new ReportParamsKey(dto.reportParamsId)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudModuleCfgResponse<EmailModuleCfgDTO> merge(EmailModuleCfgDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new EmailModuleCfgCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
        ], CrudModuleCfgResponse)
    }
    def static CrudSurrogateKeyResponse<SubscriberConfigDTO, FullTrackingWithVersion> merge(SubscriberConfigDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new SubscriberConfigCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new SubscriberConfigKey(dto.eventID, dto.handlerClassName)
        ], CrudSurrogateKeyResponse)
    }
    def static CrudSurrogateKeyResponse<ListenerConfigDTO, FullTrackingWithVersion> merge(ListenerConfigDTO dto, ITestConnection dlg) {
        dto.validate
        return dlg.typeIO(new ListenerConfigCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new ListenerConfigKey(dto.classification)
        ], CrudSurrogateKeyResponse)
    }

    def static CrudSurrogateKeyResponse<BucketCounterDTO, FullTrackingWithVersion> merge(BucketCounterDTO dto, ITestConnection dlg) {
        dto.validate
        dlg.typeIO(new BucketCounterCrudRequest => [
            crud            = OperationType.MERGE
            data            = dto
            naturalKey      = new BucketCounterKey(dto.qualifier)
        ], CrudSurrogateKeyResponse)
    }
}
