package com.arvatosystems.t9t.bpmn.be.request;

import javax.persistence.Query;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IExecutor;
import com.arvatosystems.t9t.bpmn.ProcessDefinitionDTO;
import com.arvatosystems.t9t.bpmn.jpa.persistence.IProcessDefinitionEntityResolver;
import com.arvatosystems.t9t.bpmn.request.SetLockModeForAllWorkflowsRequest;

import de.jpaw.dp.Jdp;

public class SetLockModeForAllWorkflowsRequestHandler extends AbstractRequestHandler<SetLockModeForAllWorkflowsRequest> {
    protected final IProcessDefinitionEntityResolver resolver = Jdp.getRequired(IProcessDefinitionEntityResolver.class);
    protected final IExecutor executor = Jdp.getRequired(IExecutor.class);

    @Override
    public ServiceResponse execute(final SetLockModeForAllWorkflowsRequest request) throws Exception {
        Query q = resolver.getEntityManager().createQuery(
                "UPDATE " + resolver.getBaseJpaEntityClass().getSimpleName() + " SET useExclusiveLock = :lockMode WHERE tenantRef = :tenantRef");
        q.setParameter("lockMode", request.getLockMode());
        q.setParameter("tenantRef", resolver.getSharedTenantRef());
        q.executeUpdate();
        executor.clearCache(ProcessDefinitionDTO.class.getSimpleName(), null);
        return ok();
    }
}
