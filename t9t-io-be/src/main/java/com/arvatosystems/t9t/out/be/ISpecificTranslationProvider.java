package com.arvatosystems.t9t.out.be;

import de.jpaw.bonaparte.enums.BonaTokenizableEnum;
import de.jpaw.bonaparte.pojos.meta.EnumDataItem;

public interface ISpecificTranslationProvider {
    public String translateEnum(EnumDataItem di, BonaTokenizableEnum n);
}
