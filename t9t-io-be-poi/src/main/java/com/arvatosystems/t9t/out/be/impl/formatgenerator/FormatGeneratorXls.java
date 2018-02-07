package com.arvatosystems.t9t.out.be.impl.formatgenerator;

import java.io.IOException;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.MessageComposer;
import de.jpaw.bonaparte.poi.ExcelComposer;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Named;
import de.jpaw.util.ApplicationException;

@Dependent
@Named("XLS")
public class FormatGeneratorXls extends FoldableFormatGenerator<RuntimeException> {

    protected final ExcelComposer xlsComposer = new ExcelComposer();

    @Override
    protected void openHook() throws IOException, ApplicationException {
        super.openHook();
        xlsComposer.newSheet("Sheet 1");
        writeTitles();
    }

    @Override
    protected MessageComposer<RuntimeException> getMessageComposer() {
        return xlsComposer;
    }

    @Override
    public void generateData(int recordNo, int mappedRecordNo, long recordId, BonaPortable record) throws IOException, ApplicationException {
        foldingComposer.writeRecord(record);
    }

    @Override
    public void close() throws IOException {
        xlsComposer.closeSheet();
        xlsComposer.write(outputResource.getOutputStream());
    }
}
