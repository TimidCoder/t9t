package com.arvatosystems.t9t.io.be.request;

import javax.persistence.EntityManager;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.io.jpa.persistence.impl.SinkEntityResolver;
import com.arvatosystems.t9t.io.request.CheckSinkFilenameUsedRequest;
import com.arvatosystems.t9t.io.request.CheckSinkFilenameUsedResponse;

import de.jpaw.dp.Jdp;

public class CheckSinkFilenameUsedRequestHandler extends AbstractRequestHandler<CheckSinkFilenameUsedRequest> {

    private final SinkEntityResolver sinkResolver = Jdp.getRequired(SinkEntityResolver.class);

    @Override
    public ServiceResponse execute(RequestContext context, CheckSinkFilenameUsedRequest request) throws Exception {
        final EntityManager em = sinkResolver.getEntityManager();

        final Long sinkCount = em.createQuery(new StringBuilder()
                                                                 .append("SELECT count(*) FROM ")
                                                                 .append(sinkResolver.getEntityClass()
                                                                                     .getName())
                                                                 .append(" WHERE tenantRef = :tenantRef")
                                                                 .append(" AND   fileOrQueueName = :fileOrQueueName")
                                                                 .toString(),
                                              Long.class)
                                 .setParameter("tenantRef", context.getTenantRef())
                                 .setParameter("fileOrQueueName", request.getFileOrQueueName())
                                 .getSingleResult();

        return new CheckSinkFilenameUsedResponse(0, sinkCount > 0);
    }

}
