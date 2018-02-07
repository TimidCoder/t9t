package com.arvatosystems.t9t.out.services;

import com.arvatosystems.t9t.io.DataSinkDTO;

import de.jpaw.bonaparte.pojos.api.media.MediaTypeDescriptor;

public interface IFileToCamelProducer {
    public void sendFileOverCamel(String fileName, MediaTypeDescriptor fileType, DataSinkDTO sinkCfg);
}
