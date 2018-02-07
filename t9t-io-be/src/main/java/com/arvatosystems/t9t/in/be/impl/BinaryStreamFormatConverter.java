package com.arvatosystems.t9t.in.be.impl;

import java.io.IOException;
import java.io.InputStream;

import com.arvatosystems.t9t.io.BinaryImportDTO;

import de.jpaw.bonaparte.pojos.api.media.MediaXType;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Named;
import de.jpaw.util.ByteArray;

/**
 * Binary format converter which allows import of arbitrary binary data.
 * It is up to the configured IInputDataTransformer to create a valid request. (e.g. an file upload request.)
 */
@Dependent
@Named("BINARY")
public class BinaryStreamFormatConverter extends AbstractInputFormatConverter {

    @Override
    public void process(InputStream is) {
        try {
            final String sourceURI = inputSession.getSourceURI();
            final MediaXType formatType = cfg.getCommFormatType();

            final ByteArray inputData = ByteArray.fromInputStream(is, 11500000);

            inputSession.process(new BinaryImportDTO(sourceURI, formatType, inputData));
        } catch (IOException e) {
            throw new RuntimeException("Error reading binary input data", e);
        }
    }

}
