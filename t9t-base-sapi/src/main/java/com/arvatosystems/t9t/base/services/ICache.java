package com.arvatosystems.t9t.base.services;

import de.jpaw.bonaparte.pojos.apiw.Ref;

public interface ICache<REF extends Ref, DTO extends REF> {
    DTO getByKey(Long key);
}
