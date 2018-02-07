package com.arvatosystems.t9t.bpmn.be.request;

import com.arvatosystems.t9t.base.crud.CrudSurrogateKeyResponse;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.jpa.impl.AbstractCrudSurrogateKey42RequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionRef;
import com.arvatosystems.t9t.bpmn.jpa.entities.ProcessDefinitionEntity;
import com.arvatosystems.t9t.bpmn.jpa.mapping.IProcessDefinitionDTOMapper;
import com.arvatosystems.t9t.bpmn.jpa.persistence.IProcessDefinitionEntityResolver;
import com.arvatosystems.t9t.bpmn.request.ProcessDefinitionCrudRequest;

import de.jpaw.dp.Jdp;

public class ProcessDefinitionCrudRequestHandler extends AbstractCrudSurrogateKey42RequestHandler<ProcessDefinitionRef, ProcessDefinitionDTO, FullTrackingWithVersion, ProcessDefinitionCrudRequest, ProcessDefinitionEntity> {
    protected final IProcessDefinitionEntityResolver resolver = Jdp.getRequired(IProcessDefinitionEntityResolver.class);
    protected final IProcessDefinitionDTOMapper mapper = Jdp.getRequired(IProcessDefinitionDTOMapper.class);
    protected final IExecutor executor = Jdp.getRequired(IExecutor.class);

    @Override
    public CrudSurrogateKeyResponse<ProcessDefinitionDTO, FullTrackingWithVersion> execute(final ProcessDefinitionCrudRequest request) throws Exception {
        executor.clearCache(ProcessDefinitionDTO.class.getSimpleName(), null);
        return execute(mapper, resolver, request);
    }
}
