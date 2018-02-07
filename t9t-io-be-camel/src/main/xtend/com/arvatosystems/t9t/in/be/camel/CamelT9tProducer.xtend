package com.arvatosystems.t9t.in.be.camel

import com.arvatosystems.t9t.base.services.IFileUtil
import com.arvatosystems.t9t.base.services.SimplePatternEvaluator
import com.arvatosystems.t9t.in.services.IInputSession
import com.arvatosystems.t9t.io.DataSinkDTO
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Inject
import de.jpaw.dp.Jdp
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.util.HashMap
import java.util.Map
import java.util.UUID
import org.apache.camel.Exchange
import org.apache.camel.impl.DefaultProducer
import org.joda.time.LocalDateTime
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.FileUtils

@AddLogger
class CamelT9tProducer extends DefaultProducer{

    @Inject
    IFileUtil fileUtil;

    new(CamelT9tEndpoint endpoint) {
        super(endpoint)
    }


    override process(Exchange exchange) throws Exception {
        val dataSinkDTO      = exchange.getProperty("dataSinkDTO", DataSinkDTO)
        val apiKey          = (endpoint as CamelT9tEndpoint).apiKey
        LOGGER.info("Processor invoked for dataSinkDTO {}", dataSinkDTO)

        val importFilename = deriveImportFilename(dataSinkDTO, exchange);

        // process a file / input stream, which can be binary
        val inputSession    = Jdp.getRequired(IInputSession)
        inputSession.open(dataSinkDTO.dataSinkId, UUID.fromString(apiKey), importFilename, null)

        var InputStream inputStream;

        if (dataSinkDTO.storeImportUsingFilepattern !== null) {
            val File storageFile = new File(fileUtil.getAbsolutePathForTenant(inputSession.tenantId, importFilename));

            storeImport(exchange.in.getBody(InputStream), storageFile)

            inputStream = new BufferedInputStream(new FileInputStream(storageFile));
        } else {
            inputStream = exchange.in.getBody(InputStream)
        }

        inputSession.process(inputStream)
        inputSession.close
        inputStream.close
    }

    def String deriveImportFilename(DataSinkDTO dataSinkDTO, Exchange e) throws IOException {
        val String importAsFilepattern = dataSinkDTO.getStoreImportUsingFilepattern();

        var String filename = e.in.getHeader(Exchange.FILE_NAME, String);
        var String ext = null;
        var String basename = null;

        if (filename === null) {
            ext = dataSinkDTO.getCommFormatName();
            basename = dataSinkDTO.getDataSinkId() + '_' + e.getExchangeId();
            filename = basename + '.' + ext;
        } else {
            filename =  FilenameUtils.getName(filename);
            ext = FilenameUtils.getExtension(filename);
            basename = FilenameUtils.getBaseName(filename);
        }

        if (importAsFilepattern === null) {
            return filename;
        }

        val Map<String, Object> patternReplacements = new HashMap();
        val LocalDateTime       now                 = LocalDateTime.now();
        patternReplacements.put("now", now);
        patternReplacements.put("today", formatDate(now, "yyyy-MM-dd"));
        patternReplacements.put("year", formatDate(now, "yyyy"));
        patternReplacements.put("month", formatDate(now, "MM"));
        patternReplacements.put("day", formatDate(now, "dd"));
        patternReplacements.put("timestamp", formatDate(now, "yyyyMMddHHmmssSSS"));
        patternReplacements.put("id", e.getExchangeId());
        patternReplacements.put("filename", filename);
        patternReplacements.put("extension", ext);
        patternReplacements.put("basename", basename);

        return SimplePatternEvaluator.evaluate(importAsFilepattern, patternReplacements);
    }

    def void storeImport(InputStream in, File storageFile) {
        val File storageDir  = storageFile.getParentFile();

        if (!storageDir.exists() && !storageDir.mkdirs()) {
            throw new IOException("Error creating storage dir for import file " + storageFile);
        }

        try {
            FileUtils.copyToFile(in, storageFile);
        } finally {
            in.close
        }
    }

    def static String formatDate(LocalDateTime dt, String datePattern) {
        if (dt === null) {
            return datePattern.replaceAll("\\w", "0");
        } else {
            return dt.toString(datePattern);
        }
    }
}
