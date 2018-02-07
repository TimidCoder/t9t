package com.arvatosystems.t9t.out.be.impl;

import java.util.Collections;
import java.util.List;

import com.arvatosystems.t9t.base.output.OutputSessionParameters;
import com.arvatosystems.t9t.io.DataSinkDTO;
import com.arvatosystems.t9t.out.be.IPreOutputDataTransformer;

import de.jpaw.bonaparte.core.BonaPortable;

/**
 * Implementation of {@linkplain IInputSession} which doesn't transform data.
 *
 * @author LIEE001
 */
public class OutputDataTransformerIdentity implements IPreOutputDataTransformer {

    public static final IPreOutputDataTransformer INSTANCE = new OutputDataTransformerIdentity();

    private OutputDataTransformerIdentity() {
        // prevent instantiation
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BonaPortable> transformData(final BonaPortable record, final DataSinkDTO sinkCfg, final OutputSessionParameters outputSessionParameters) {
        return Collections.singletonList(record);
    }
}
