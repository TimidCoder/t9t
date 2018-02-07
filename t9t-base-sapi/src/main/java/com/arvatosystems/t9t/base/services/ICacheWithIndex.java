package com.arvatosystems.t9t.base.services;

import de.jpaw.bonaparte.pojos.apiw.Ref;

public interface ICacheWithIndex<REF extends Ref, DTO extends REF> extends ICache<REF, DTO> {
    DTO getByIndex(String index);
}
