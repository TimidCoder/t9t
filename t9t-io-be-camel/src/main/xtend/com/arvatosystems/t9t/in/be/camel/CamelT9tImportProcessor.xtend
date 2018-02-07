package com.arvatosystems.t9t.in.be.camel

//import com.arvatosystems.t9t.in.services.IInputSession
//import com.arvatosystems.t9t.io.DataSinkDTO
import de.jpaw.annotations.AddLogger
//import de.jpaw.dp.Jdp
//import java.io.InputStream
//import java.util.UUID
import org.apache.camel.Exchange
import org.apache.camel.Processor

@AddLogger
class CamelT9tImportProcessor implements Processor {

    override process(Exchange exchange) throws Exception {
        // this component might be necessary in the future as it can access apiKeys etc.
        // we need an API key and a dataSinkId in order to start processing.
//        val dataSinkDTO      = exchange.getProperty("dataSinkDTO", DataSinkDTO)
//        val apiKey          = exchange.getProperty("apiKey", String)
//        LOGGER.info("Processor invoked for dataSinkDTO {}", dataSinkDTO)
//
//
//        // process a file / input stream, which can be binary
//        val msg             = exchange.in
//        val inputStream     = msg.getBody(InputStream)
//        val inputSession    = Jdp.getRequired(IInputSession)
//        inputSession.open(dataSinkDTO.dataSinkId, UUID.fromString(apiKey), dataSinkDTO.fileOrQueueNamePattern, null)
//        inputSession.process(inputStream)
//        inputSession.close
//        inputStream.close
    }
}
