package com.arvatosystems.t9t.base;

import com.arvatosystems.t9t.base.crud.CrudAnyKeyRequest;
import com.arvatosystems.t9t.base.search.SearchCriteria;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.BonaPortableClass;
import de.jpaw.bonaparte.pojos.api.TrackingBase;

/** A container to hold the common references for a standard UI CRUD screen. */
public class CrudViewModel<DTO extends BonaPortable, TRACKING extends TrackingBase> {
    public final BonaPortableClass<DTO> dtoClass;
    public final BonaPortableClass<TRACKING> trackingClass;
    public final BonaPortableClass<? extends SearchCriteria> searchClass;
    public final BonaPortableClass<? extends CrudAnyKeyRequest<DTO, TRACKING>> crudClass;

    public CrudViewModel(
            BonaPortableClass<DTO> dtoClass,
            BonaPortableClass<TRACKING> trackingClass,
            BonaPortableClass<? extends SearchCriteria> searchClass,
            BonaPortableClass<? extends CrudAnyKeyRequest<DTO, TRACKING>> crudClass) {
        this.dtoClass = dtoClass;
        this.trackingClass = trackingClass;
        this.searchClass = searchClass;
        this.crudClass = crudClass;
    }
}
