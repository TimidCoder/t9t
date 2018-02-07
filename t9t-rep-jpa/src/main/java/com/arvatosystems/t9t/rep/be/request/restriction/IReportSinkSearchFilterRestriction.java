package com.arvatosystems.t9t.rep.be.request.restriction;

import com.arvatosystems.t9t.base.T9tException;

import de.jpaw.bonaparte.core.ObjectValidationException;
import de.jpaw.bonaparte.pojos.api.FieldFilter;


/**
 * SearchFilter restriction for report specific SinkSearch
 * @author RREN001
 */
public interface IReportSinkSearchFilterRestriction {
    public static final String COMM_TARGET_CHANNEL_FIELD_NAME = "commTargetChannelType";
    public static final String DATA_SINK_CATEGORY_FIELD_NAME = "dataSink.category";
    public static final String COMM_FORMAT_TYPE_FIELD_NAME = "commFormatType";
    public static final String FILE_OR_QUEUE_FIELD_NAME = "fileOrQueueName";

    /**
     * do searchFilter checking depending on the fieldName
     * at the moment only handling filter checking for: commTargetChannelType, dataSink.category, commFormatType, configurationRef, fileOrQueueName
     * @param searchFilter
     *              the searchFilter we are checking
     * @throws ObjectValidationException
     *              if the searchFilter is not of the expected type
     * @throws T9tException
     *              if the searchFilter actually contains value that's outside from the permitted for report search
     */
    boolean isSearchFilterCheckingSuccess(FieldFilter searchFilter);
}
