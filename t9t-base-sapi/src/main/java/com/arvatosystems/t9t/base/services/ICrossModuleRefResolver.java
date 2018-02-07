package com.arvatosystems.t9t.base.services;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.crud.CrudSurrogateKeyRequest;
import com.arvatosystems.t9t.base.crud.RefResolverRequest;

import de.jpaw.bonaparte.pojos.api.TrackingBase;
import de.jpaw.bonaparte.pojos.apiw.Ref;

/**
 * Interface for component responsible for resolving entity reference. Entity may be defined in different module than caller.
 *
 * @author dzie003
 *
 */
public interface ICrossModuleRefResolver {

    /**
     * Method allows to resolve entity reference by making cross module call. Caller has to provide request object which will be processed by handler located in
     * module different than the one where caller is located.
     *
     * @param req
     *            request object (extends RefResolverRequest). May not be null.
     * @param ref
     *            reference (null is passed through)
     * @return object reference
     * @throws T9tException
     *             thrown when resolving reference fails
     */
    public <REF extends Ref, REQ extends RefResolverRequest<REF>> Long getRef(REQ req, REF ref);

    /**
     * Method allows to return a DTO when the primary key is known, by making cross module call. Caller has to provide request object which will be processed by
     * handler located in module different than the one where caller is located.
     *
     * @param req
     *            request object (extends CrudSurrogateKeyRequest)
     * @param objectRef
     *            reference (null is passed through)
     * @param onlyActive
     *            if true and the entity has an isActive column in the tracking data, only active records are queried
     * @return DTO
     * @throws T9tException
     *             thrown when no record exists
     */
    public <REF extends Ref, DTO extends REF, TRACKING extends TrackingBase, REQ extends CrudSurrogateKeyRequest<REF, DTO, TRACKING>> DTO getData(REQ req, Long objectRef, boolean onlyActive)
           ;

    /**
     * Method allows to return a DTO when the primary key is known, by making cross module call. Caller has to provide request object which will be processed by
     * handler located in module different than the one where caller is located.
     *
     * @param req
     *            request object (extends CrudSurrogateKeyRequest)
     * @param objectRef
     *            reference (null is passed through)
     * @return DTO
     * @throws T9tException
     *             thrown when no record exists
     */
    public <REF extends Ref, DTO extends REF, TRACKING extends TrackingBase, REQ extends CrudSurrogateKeyRequest<REF, DTO, TRACKING>> DTO getData(REQ req, Long objectRef);

    /**
     * Method allows to return a DTO when a natural key is known, by making cross module call. Caller has to provide request object which will be processed by
     * handler located in module different than the one where caller is located.
     *
     * @param req
     *            request object (extends CrudSurrogateKeyRequest)
     * @param ref
     *            reference (null is passed through)
     * @param onlyActive
     *            if true and the entity has an isActive column in the tracking data, only active records are queried
     * @return DTO
     * @throws T9tException
     *             thrown when no record exists
     */
    public <REF extends Ref, DTO extends REF, TRACKING extends TrackingBase, REQ extends CrudSurrogateKeyRequest<REF, DTO, TRACKING>> DTO getData(REQ req, REF ref, boolean onlyActive);

    /**
     * Method allows to return a DTO when a natural key is known, by making cross module call. Caller has to provide request object which will be processed by
     * handler located in module different than the one where caller is located.
     *
     * @param req
     *            request object (extends CrudSurrogateKeyRequest)
     * @param ref
     *            reference (null is passed through)
     * @return DTO
     * @throws T9tException
     *             thrown when no record exists
     */
    public <REF extends Ref, DTO extends REF, TRACKING extends TrackingBase, REQ extends CrudSurrogateKeyRequest<REF, DTO, TRACKING>> DTO getData(REQ req, REF ref);

}
