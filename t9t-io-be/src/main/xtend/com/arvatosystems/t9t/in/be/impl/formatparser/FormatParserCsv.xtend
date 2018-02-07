package com.arvatosystems.t9t.in.be.impl.formatparser

import com.arvatosystems.t9t.in.be.impl.AbstractTextFormatConverter
import com.arvatosystems.t9t.in.services.IInputSession
import com.arvatosystems.t9t.io.CsvConfigurationDTO
import com.arvatosystems.t9t.io.DataSinkDTO
import com.arvatosystems.t9t.io.services.CSVTools
import com.arvatosystems.t9t.server.services.IStatefulServiceSession
import de.jpaw.annotations.AddLogger
import de.jpaw.bonaparte.core.BonaPortableClass
import de.jpaw.bonaparte.core.CSVConfiguration
import de.jpaw.bonaparte.core.StaticMeta
import de.jpaw.bonaparte.core.StringCSVParser
import de.jpaw.dp.Dependent
import de.jpaw.dp.Named
import java.util.Map

@Dependent
@Named("CSV")
@AddLogger
class FormatParserCsv extends AbstractTextFormatConverter {
    protected CSVConfiguration csvCfg
    protected StringCSVParser parser

    override open(IInputSession inputSession, DataSinkDTO cfg, IStatefulServiceSession session, Map<String, Object> params, BonaPortableClass<?> baseBClass) {
        super.open(inputSession, cfg, session, params, baseBClass)
        csvCfg = CSVTools.getCsvConfiguration(cfg.csvConfigurationRef as CsvConfigurationDTO);
        parser = new StringCSVParser(csvCfg, "")
        if (Boolean.TRUE == cfg.nationalNumberFormat)
            parser.setNationalBigDecimal();
    }

    override process(String textLine) {
        parser.source= textLine
//        val dto = parser.readRecord
        val dto = parser.readObject(StaticMeta.OUTER_BONAPORTABLE_FOR_CSV, baseBClass.bonaPortableClass)
        inputSession.process(dto)
    }
}
