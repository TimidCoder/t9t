package com.arvatosystems.t9t.bpmn.be.request;

import com.arvatosystems.t9t.base.jpa.impl.AbstractLeanSearchRequestHandler;
import com.arvatosystems.t9t.base.search.Description;
import com.arvatosystems.t9t.bpmn.jpa.entities.ProcessDefinitionEntity;
import com.arvatosystems.t9t.bpmn.jpa.persistence.IProcessDefinitionEntityResolver;
import com.arvatosystems.t9t.bpmn.request.LeanProcessDefinitionSearchRequest;

import de.jpaw.dp.Jdp;

public class LeanProcessDefinitionSearchRequestHandler extends AbstractLeanSearchRequestHandler<LeanProcessDefinitionSearchRequest, ProcessDefinitionEntity> {
    public LeanProcessDefinitionSearchRequestHandler() {
        super(Jdp.getRequired(IProcessDefinitionEntityResolver.class),
            e -> new Description(null, e.getProcessDefinitionId(), e.getName(), false, false)
        );
    }
}
