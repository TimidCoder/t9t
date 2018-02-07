package com.arvatosystems.t9t.out.be.impl.output;

import com.arvatosystems.t9t.out.be.ISpecificTranslationProvider;

import de.jpaw.bonaparte.core.DelegatingBaseComposer;
import de.jpaw.bonaparte.core.MessageComposer;
import de.jpaw.bonaparte.enums.BonaTokenizableEnum;
import de.jpaw.bonaparte.pojos.meta.AlphanumericElementaryDataItem;
import de.jpaw.bonaparte.pojos.meta.EnumDataItem;

public class EnumTranslatorComposerFilter<E extends Exception> extends DelegatingBaseComposer<E> {
    private final ISpecificTranslationProvider translator;

    public EnumTranslatorComposerFilter(MessageComposer<E> delegateComposer, ISpecificTranslationProvider translator) {
        super(delegateComposer);
        this.translator = translator;
    }

    // enums replaced by the internal token
    /*
    @Override
    public void addEnum(EnumDataItem di, BasicNumericElementaryDataItem ord, BonaNonTokenizableEnum<?> n) throws E {
        delegateComposer.addField(StaticMeta.ENUM_TOKEN, n == null ? null : n.toString());
    }
     */

    // enum with alphanumeric expansion: delegate to Null/String
    // use the existing token meta here, because the name is better, depsite the length may be too short
    @Override
    public void addEnum(EnumDataItem di, AlphanumericElementaryDataItem token, BonaTokenizableEnum n) throws E {
        delegateComposer.addField(token, n == null ? null : translator.translateEnum(di, n));
    }

}
