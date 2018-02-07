package com.arvatosystems.t9t.in.be.jackson.impl

import com.fasterxml.jackson.core.JsonToken
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Dependent
import de.jpaw.dp.Named
import java.io.InputStream
import de.jpaw.bonaparte.core.MessageParserException
import com.arvatosystems.t9t.in.be.jackson.AbstractJsonFormatConverter

@AddLogger
@Dependent
@Named("JSON") // generic JSON reader
class JsonStreamFormatConverter extends AbstractJsonFormatConverter {

    override process(InputStream is) {
        var parser = factory.createParser(is)
        var JsonToken current;
        current = parser.nextToken
        var recordName = cfg.xmlRecordName // will be in this case the records field like {'tenantId': "X", 'records':[{bonaparte object}, {bona...}]}
        var baseClass = baseBClass.bonaPortableClass
        if (current !== JsonToken.START_OBJECT) {
            LOGGER.error("Json did not start with a start token!")
            throw new MessageParserException(MessageParserException.BAD_TRANSMISSION_START)
        }

        while (parser.nextToken() !== JsonToken.END_OBJECT) { // parse until the end of the object
            var fieldName = parser.getCurrentName()
            current = parser.nextToken();
            if (fieldName.equals(recordName)) { // search for the records field
                if (current == JsonToken.START_ARRAY) { // we expect this to be an array
                    // For each of the records in the array
                    while (parser.nextToken() !== JsonToken.END_ARRAY) {
                        var node = parser.readValueAs(baseClass) // now parse into the bonaparte object with the objectmapper
                        inputSession.process(node) // process it in the subsequent transformer etc.
                    }
                } else {
                    LOGGER.warn("Error: records should be an array: skipping.");
                    parser.skipChildren();
                }
            } else {
                inputSession.setHeaderData(fieldName, parser.text)
            }
        }
    }
}
