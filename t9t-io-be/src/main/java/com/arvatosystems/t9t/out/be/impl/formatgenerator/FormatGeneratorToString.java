package com.arvatosystems.t9t.out.be.impl.formatgenerator;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Named;

@Dependent
@Named("toString")
public class FormatGeneratorToString extends AbstractFormatGenerator {

    @Override
    public void generateData(int recordNo, int mappedRecordNo, long recordId, BonaPortable record) {
        outputResource.write(record.toString());
    }
}
