package com.arvatosystems.t9t.out.be.impl.formatgenerator;

import java.io.IOException;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.CompactByteArrayComposer;
import de.jpaw.bonaparte.core.MessageComposer;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Named;
import de.jpaw.util.ApplicationException;

@Dependent
@Named("COMPACT_BONAPARTE")
public class FormatGeneratorCompactBonaparte extends FoldableFormatGenerator<IOException> {

    protected final CompactByteArrayComposer cbac = new CompactByteArrayComposer();

    @Override
    protected MessageComposer<IOException> getMessageComposer() {
        return cbac;
    }

    @Override
    public void generateData(int recordNo, int mappedRecordNo, long recordId, BonaPortable record) throws IOException, ApplicationException {
        cbac.reset();
        foldingComposer.writeRecord(record);
        outputResource.write(cbac.getBuffer(), 0, cbac.getLength(), true);
    }
}
