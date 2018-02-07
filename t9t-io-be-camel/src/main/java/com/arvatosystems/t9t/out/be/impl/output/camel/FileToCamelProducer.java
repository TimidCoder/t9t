package com.arvatosystems.t9t.out.be.impl.output.camel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.file.FileConsumer;
import org.apache.camel.component.file.GenericFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.base.services.SimplePatternEvaluator;
import com.arvatosystems.t9t.io.CamelPostProcStrategy;
import com.arvatosystems.t9t.io.DataSinkDTO;
import com.arvatosystems.t9t.out.services.IFileToCamelProducer;
import com.google.common.collect.ImmutableMap;

import de.jpaw.bonaparte.pojos.api.media.MediaTypeDescriptor;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Jdp;
import de.jpaw.dp.Provider;

/**
 *
 * Class for producing a message for file export to the generic camel export route 'direct:outputFile';
 */
@Dependent
public class FileToCamelProducer implements IFileToCamelProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileToCamelProducer.class);

    private final Provider<CamelContext> camelContext = Jdp.getProvider(CamelContext.class);
    /**
     * Produces a message containing file information to the camel export route.
     *
     * @param fileName
     *            file name
     * @param fileType
     *            file type
     * @param sinkCfg
     *            configuration parameters
     */
    @Override
    public void sendFileOverCamel(String fileName, MediaTypeDescriptor fileType, DataSinkDTO sinkCfg) {

        ProducerTemplate producerTemplate = camelContext.get().createProducerTemplate();
        File file = new File(fileName);
        GenericFile<File> genericFile = FileConsumer.asGenericFile("test", file, null);
        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("fileName", fileName);
        headerMap.put("fileType", fileType);
        headerMap.put("camelRoute", sinkCfg.getCamelRoute());
        headerMap.put(Exchange.FILE_NAME, genericFile.getFileNameOnly());

        try {
            producerTemplate.sendBodyAndHeaders("direct:outputFile", genericFile, headerMap);
            successfulRoutingPostProcessing(fileName, fileType, sinkCfg);
        } catch (CamelExecutionException e) {
            failedRoutingPostProcessing(fileName, fileType, sinkCfg);
            throw e;
        }
    }

    // stuff for Camel
    private static String resolveSimpleFileName(String filePath) {
        String normalizedPath = filePath.replaceAll("\\\\", "/");
        int startIdx = normalizedPath.lastIndexOf("/");
        int endIdx = normalizedPath.lastIndexOf(".");

        if ((startIdx > -1) && (endIdx > -1)) {
            return normalizedPath.substring(startIdx + 1, endIdx);
        }

        return "";
    }

    private static String resolveFileExtension(String filePath) {
        if (filePath.contains(".")) {
            return filePath.substring(filePath.lastIndexOf(".") + 1);
        }

        return "";
    }

    private static String expandForCamel(String srcFilePath) {
        return SimplePatternEvaluator.evaluate(srcFilePath, ImmutableMap.of(
                "fileExtension", resolveFileExtension(srcFilePath),
                "simpleFileName", resolveSimpleFileName(srcFilePath))
        );
    }

    private void successfulRoutingPostProcessing(String fileName, MediaTypeDescriptor fileType, DataSinkDTO sinkCfg) {
        LOGGER.debug("Post processing after camel successful routing");

        if (sinkCfg.getSuccessRoutingStrategy() == null) {
            return;
        }

        switch (sinkCfg.getSuccessRoutingStrategy()) {
        case DELETE:
            deleteFile(fileName);
            break;
        case MOVE:
            if (sinkCfg.getSuccessDestPattern() != null) {
                String dstFileName = expandForCamel(sinkCfg.getSuccessDestPattern());
                moveFile(fileName, dstFileName);
            } else {
                LOGGER.error("SuccessRoutingStrategy is set to {} but SuccessDestPath is null", CamelPostProcStrategy.MOVE);
            }
            break;
        default:
            break;

        }
    }

    private void failedRoutingPostProcessing(String fileName, MediaTypeDescriptor fileType, DataSinkDTO sinkCfg) {
        LOGGER.debug("Post processing after camel failed routing");
        LOGGER.debug("Filename: {}, FileType: {}",fileName, fileType);
        LOGGER.debug("SinkCfg: {}", sinkCfg);
        if (sinkCfg.getFailedRoutingStrategy() == null) {
            return;
        }

        switch (sinkCfg.getFailedRoutingStrategy()) {
        case DELETE:
            deleteFile(fileName);
            break;
        case MOVE:
            if (sinkCfg.getFailureDestPattern() != null) {
                String dstFileName = expandForCamel(sinkCfg.getFailureDestPattern());
                moveFile(fileName, dstFileName);
            } else {
                LOGGER.error("SuccessRoutingStrategy is set to {} but SuccessDestPath is null", CamelPostProcStrategy.MOVE);
            }
            break;
        default:
            break;

        }
    }

    private void moveFile(String fileName, String destination) {
        createDestinationFolder(destination);
        Path srcFile = Paths.get(fileName);
        Path targetFile = Paths.get(destination);

        try {
            Files.move(srcFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOGGER.error("File {} couldn't be moved!", fileName);
            LOGGER.error("An error occurred while moving file.", e);
        }
    }

    private void createDestinationFolder(String path) {
        File file = new File(path);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }

    private void deleteFile(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            LOGGER.error("File {} couldn't be deleted!", fileName);
            LOGGER.error("An error occurred while deleting file.", e);
        }
    }
}
