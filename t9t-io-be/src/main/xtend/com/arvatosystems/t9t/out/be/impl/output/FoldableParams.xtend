package com.arvatosystems.t9t.out.be.impl.output

import com.arvatosystems.t9t.out.be.ISpecificTranslationProvider
import de.jpaw.bonaparte.pojos.api.media.EnumOutputType
import java.util.List
import org.eclipse.xtend.lib.annotations.Data

@Data
class FoldableParams {
    // parameters for the constructor
    List<String>                selectedFields;
    List<String>                headers;
    // parameters for the initDataGenerator call
    EnumOutputType              relevantEnumType;
    ISpecificTranslationProvider enumTranslator;
    boolean                     applyVariantFilter;
}
