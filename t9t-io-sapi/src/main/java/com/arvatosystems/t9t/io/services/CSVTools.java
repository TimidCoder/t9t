package com.arvatosystems.t9t.io.services;

import java.util.Locale;

import org.joda.time.DateTimeZone;

import com.arvatosystems.t9t.io.CsvConfigurationDTO;
import com.arvatosystems.t9t.io.CsvDateTimeStyleType;

import de.jpaw.bonaparte.core.CSVConfiguration;
import de.jpaw.bonaparte.core.CSVStyle;

public class CSVTools {
    private static CSVStyle t2b(CsvDateTimeStyleType t) {
        return t == null ? null : CSVStyle.factory(t.getToken());
    }

    public static CSVConfiguration getCsvConfiguration(CsvConfigurationDTO cfgDTO) {
        if (cfgDTO == null)
            return CSVConfiguration.CSV_DEFAULT_CONFIGURATION;
        else {
            // create a custom configuration
            Integer quote = cfgDTO.getQuote();

            return new CSVConfiguration(
                cfgDTO.getSeparator(),
                quote == null ? null : Character.valueOf((char)quote.intValue()),
                cfgDTO.getQuoteReplacement(),
                cfgDTO.getCtrlReplacement(),
                cfgDTO.getQuoteDates(),
                cfgDTO.getRemovePoint(),
                cfgDTO.getMapStart(),
                cfgDTO.getMapEnd(),
                cfgDTO.getArrayStart(),
                cfgDTO.getArrayEnd(),
                cfgDTO.getObjectStart(),
                cfgDTO.getObjectEnd(),
                cfgDTO.getBooleanTrue(),
                cfgDTO.getBooleanFalse(),
                cfgDTO.getLanguageCode()!= null? Locale.forLanguageTag(cfgDTO.getLanguageCode()): null,
                cfgDTO.getTimeZone() != null ? DateTimeZone.forID(cfgDTO.getTimeZone()): null,
                t2b(cfgDTO.getDayStyle()),
                t2b(cfgDTO.getTimeStyle()),
                cfgDTO.getCustomDayFormat(),
                cfgDTO.getCustomTimeFormat(),
                cfgDTO.getCustomTimeWithMsFormat(),
                cfgDTO.getCustomTsFormat(),
                cfgDTO.getCustomTsWithMsFormat(),
                cfgDTO.getZeroPadNumbers(),
                cfgDTO.getRightPadNumbers(),
                cfgDTO.getUseGrouping()
            );
        }
    }
}
