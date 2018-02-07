package com.arvatosystems.t9t.bucket.be.request;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.bucket.jpa.persistence.IBucketEntryEntityResolver;
import com.arvatosystems.t9t.bucket.request.DeleteBucketRequest;

import de.jpaw.dp.Jdp;

public class DeleteBucketRequestHandler extends AbstractRequestHandler<DeleteBucketRequest> {
    protected final IBucketEntryEntityResolver resolver = Jdp.getRequired(IBucketEntryEntityResolver.class);

    @Override
    public ServiceResponse execute(RequestContext ctx, DeleteBucketRequest rp) {
        EntityManager em = resolver.getEntityManager();

        Query q = em.createQuery(
                "DELETE FROM BucketEntryEntity be WHERE be.tenantRef = :tenantRef AND be.qualifier = :qualifier AND be.bucket = :bucketNo"
        );
        q.setParameter("tenantRef", resolver.getSharedTenantRef());
        q.setParameter("qualifier", rp.getQualifier());
        q.setParameter("bucketNo", rp.getBucketNo());
        q.executeUpdate();
        return ok();
    }
}
