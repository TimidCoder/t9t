package com.arvatosystems.t9t.out.be.impl.formatgenerator;

import java.io.IOException;
import java.io.OutputStreamWriter;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.JsonComposer;
import de.jpaw.bonaparte.core.MessageComposer;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Named;
import de.jpaw.util.ApplicationException;

@Dependent
@Named("JSON")
public class FormatGeneratorJson extends FoldableFormatGenerator<IOException> {

    protected OutputStreamWriter osw = null;
    protected JsonComposer jsonComposer = null;

    @Override
    protected MessageComposer<IOException> getMessageComposer() {
        return jsonComposer;
    }

    @Override
    protected void openHook() throws IOException, ApplicationException {
        osw = new OutputStreamWriter(outputResource.getOutputStream(), encoding);
        jsonComposer = new JsonComposer(osw);
        jsonComposer.startTransmission();
        super.openHook();
    }

    @Override
    public void generateData(int recordNo, int mappedRecordNo, long recordId, BonaPortable record) throws IOException, ApplicationException {
        foldingComposer.writeRecord(record);
    }

    @Override
    public void close() throws IOException, ApplicationException {
        jsonComposer.terminateTransmission();
        osw.flush();  // no result without the flush()
    }
}
