package com.arvatosystems.t9t.out.be.impl.formatgenerator;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.out.be.impl.OutputHeading;
import com.arvatosystems.t9t.out.be.impl.output.EnumTranslatorComposerFilter;
import com.arvatosystems.t9t.out.be.impl.output.VariantComposerFilter;

import de.jpaw.bonaparte.core.BonaCustom;
import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.EnumAsTokenComposerFilter;
import de.jpaw.bonaparte.core.FoldingComposer;
import de.jpaw.bonaparte.core.MessageComposer;
import de.jpaw.bonaparte.pojos.meta.FoldingStrategy;
import de.jpaw.util.ApplicationException;

public abstract class FoldableFormatGenerator<E extends Exception> extends AbstractFormatGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(FoldableFormatGenerator.class);
    protected MessageComposer<E> foldingComposer;
    protected Map<Class<? extends BonaCustom>, List<String>> map = null;

    protected abstract MessageComposer<E> getMessageComposer();


    @Override
    protected void openHook() throws IOException, ApplicationException {
        MessageComposer<E> baseComposer = getMessageComposer();
        if (foldableParams == null || foldableParams.getSelectedFields() == null ||  foldableParams.getSelectedFields().isEmpty()) {
            map = null;
        } else {
            map = new HashMap<>();
            map.put(BonaPortable.class, foldableParams.getSelectedFields());
            if (foldableParams.getRelevantEnumType() != null) {
                switch (foldableParams.getRelevantEnumType()) {
                case NAME:
                    baseComposer = new EnumAsTokenComposerFilter<E>(baseComposer);
                    break;
                case DESCRIPTION:
                    baseComposer = new EnumTranslatorComposerFilter<E>(baseComposer, foldableParams.getEnumTranslator());
                    break;
                default:
                      // intentionally no activity
                }
            }
            if (foldableParams.isApplyVariantFilter()) {
                // insert a mapper from the Variant to one of its member types
                baseComposer = new VariantComposerFilter<>(baseComposer);
            }
        }
        foldingComposer = map == null ? baseComposer : new FoldingComposer<E>(baseComposer, map, FoldingStrategy.TRY_SUPERCLASS);
    }

    protected void writeTitles() {
        if (foldableParams == null || foldableParams.getHeaders() == null || foldableParams.getHeaders().isEmpty())
            return;
        try {
            getMessageComposer().writeRecord(new OutputHeading(foldableParams.getHeaders()));
        } catch (Exception e) {
            LOGGER.error("Failed to write header to output.", e);
        }
    }
}
