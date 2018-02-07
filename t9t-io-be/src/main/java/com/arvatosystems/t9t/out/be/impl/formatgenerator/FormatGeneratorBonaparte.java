package com.arvatosystems.t9t.out.be.impl.formatgenerator;

import java.io.IOException;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.ByteArrayComposer;
import de.jpaw.bonaparte.core.MessageComposer;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Named;
import de.jpaw.util.ApplicationException;

@Dependent
@Named("BONAPARTE")
public class FormatGeneratorBonaparte extends FoldableFormatGenerator<RuntimeException> {

    protected final ByteArrayComposer bac = new ByteArrayComposer();

    @Override
    protected MessageComposer<RuntimeException> getMessageComposer() {
        return bac;
    }

    @Override
    public void generateData(int recordNo, int mappedRecordNo, long recordId, BonaPortable record) throws IOException, ApplicationException {
        bac.reset();
        foldingComposer.writeRecord(record);
        outputResource.write(bac.getBuffer(), 0, bac.getLength(), true);
    }
}
