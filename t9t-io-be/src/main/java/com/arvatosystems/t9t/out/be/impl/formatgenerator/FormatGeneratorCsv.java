package com.arvatosystems.t9t.out.be.impl.formatgenerator;

import java.io.IOException;
import java.io.OutputStreamWriter;

import com.arvatosystems.t9t.io.CsvConfigurationDTO;
import com.arvatosystems.t9t.io.services.CSVTools;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.CSVComposer3;
import de.jpaw.bonaparte.core.MessageComposer;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Named;
import de.jpaw.util.ApplicationException;

@Dependent
@Named("CSV")
public class FormatGeneratorCsv extends FoldableFormatGenerator<IOException> {

    protected OutputStreamWriter osw = null;
    protected CSVComposer3 csvComposer = null;

    @Override
    protected MessageComposer<IOException> getMessageComposer() {
        return csvComposer;
    }

    @Override
    protected void openHook() throws IOException, ApplicationException {
        CsvConfigurationDTO csvCfg = (CsvConfigurationDTO)sinkCfg.getCsvConfigurationRef();
        osw = new OutputStreamWriter(outputResource.getOutputStream(), encoding);
        csvComposer = new CSVComposer3(osw, CSVTools.getCsvConfiguration(csvCfg));
        csvComposer.startTransmission();
        super.openHook();
    }

    @Override
    public void generateData(int recordNo, int mappedRecordNo, long recordId, BonaPortable record) throws IOException, ApplicationException {
        foldingComposer.writeRecord(record);
    }

    @Override
    public void close() throws IOException, ApplicationException {
        csvComposer.terminateTransmission();
        osw.flush();
    }
}
