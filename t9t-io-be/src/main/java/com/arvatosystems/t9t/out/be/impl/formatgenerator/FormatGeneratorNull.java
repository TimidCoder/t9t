package com.arvatosystems.t9t.out.be.impl.formatgenerator;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Named;

@Dependent
@Named("NULL")
public class FormatGeneratorNull extends AbstractFormatGenerator {

    @Override protected void openHook() {}  // no exception if called with foldable parameters
    @Override public void generateData(int recordNo, int mappedRecordNo, long recordId, BonaPortable record) {}
}
