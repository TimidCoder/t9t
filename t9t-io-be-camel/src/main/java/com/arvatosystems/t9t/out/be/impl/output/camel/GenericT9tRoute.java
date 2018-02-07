package com.arvatosystems.t9t.out.be.impl.output.camel;

import org.apache.camel.builder.RouteBuilder;

import com.arvatosystems.t9t.base.services.IFileUtil;
import com.arvatosystems.t9t.io.DataSinkDTO;

public class GenericT9tRoute extends RouteBuilder {

    private DataSinkDTO dataSinkDTO;
    private IFileUtil fileUtil;

    public GenericT9tRoute(DataSinkDTO dataSinkDTO, IFileUtil fileUtil) {
        this.dataSinkDTO = dataSinkDTO;
        this.dataSinkDTO.freeze();

        this.fileUtil = fileUtil;
    }

    @Override
    public void configure() throws Exception {
        if (dataSinkDTO.getIsInput()) { // this has to be extended in the future for outgoing routes.
            if (dataSinkDTO.getImportQueueName() != null) {
                final String queueDirectory = fileUtil.getAbsolutePath(".import-queue/" + dataSinkDTO.getImportQueueName()).replace('\\', '/');

                from(dataSinkDTO.getFileOrQueueNamePattern())
                    .routeId("DataSink-" + dataSinkDTO.getDataSinkId() + "-" + dataSinkDTO.getObjectRef() + "-QueueIn")
                    .to("file://"+queueDirectory+"?autoCreate=true&tempFileName=${file:name}.intrans&flatten=true");

                from("file://"+queueDirectory+"?initialDelay=1000&delay=10000&delete=true&moveFailed=.failed&antExclude=*.intrans&sortBy=${file:modified}")
                    .routeId("DataSink-" + dataSinkDTO.getDataSinkId() + "-" + dataSinkDTO.getObjectRef() + "-QueueOut")
                    .setProperty("dataSinkDTO", constant(dataSinkDTO))
                    .to(dataSinkDTO.getCamelRoute());
            } else {
                from(dataSinkDTO.getFileOrQueueNamePattern())
                    .routeId("DataSink-" + dataSinkDTO.getDataSinkId() + "-" + dataSinkDTO.getObjectRef())
                    .setProperty("dataSinkDTO", constant(dataSinkDTO))
                    .to(dataSinkDTO.getCamelRoute());
            }
        } else {
            throw new UnsupportedOperationException("Output Route with t9t: component is currently not supported!");
        }
    }
}
