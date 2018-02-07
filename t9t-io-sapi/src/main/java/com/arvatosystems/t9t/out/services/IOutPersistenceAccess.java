package com.arvatosystems.t9t.out.services;

import java.util.List;

import com.arvatosystems.t9t.io.DataSinkDTO;
import com.arvatosystems.t9t.io.OutboundMessageDTO;
import com.arvatosystems.t9t.io.SinkDTO;

/** Defines the communication layer between the backend modules (business logic / persistence layer). */
public interface IOutPersistenceAccess {

    DataSinkDTO getDataSinkDTO(String dataSinkId);

    List<DataSinkDTO> getDataSinkDTOsForEnvironment(String environment);

    /** Assigns a new primary key for the sink (required before persisting it, because it will be used by the OutboundMessageDTOs as well). */
    Long getNewSinkKey();

    void storeNewSink(SinkDTO sink);

    Long getNewOutboundMessageKey();
    void storeOutboundMessage(OutboundMessageDTO sink);
}
