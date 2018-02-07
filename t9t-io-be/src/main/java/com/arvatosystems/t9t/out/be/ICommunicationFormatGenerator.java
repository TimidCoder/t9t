package com.arvatosystems.t9t.out.be;

import java.io.IOException;
import java.nio.charset.Charset;

import com.arvatosystems.t9t.base.output.OutputSessionParameters;
import com.arvatosystems.t9t.io.DataSinkDTO;
import com.arvatosystems.t9t.out.be.impl.output.FoldableParams;
import com.arvatosystems.t9t.out.services.IOutputResource;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.pojos.api.media.MediaXType;
import de.jpaw.util.ApplicationException;

/**
 * Communication format generators convert structured records into byte or character streams.
 * Implementations are responsible for pushing the data into the destination.
 *
 */
public interface ICommunicationFormatGenerator {

    /**
     * Generate header datas in a specific format.
     * @param sinkCfg data sink configuration
     * @param outputSessionParameters output session parameters
     * @throws IOException, ApplicationException if there is an issue generating header data
     */
    void open(DataSinkDTO sinkCfg, OutputSessionParameters outputSessionParameters, MediaXType effectiveType, FoldableParams fp, IOutputResource destination, Charset encoding,
            String tenantId) throws IOException, ApplicationException;

    /**
     * Generate record datas in a specific format.
     * @param recordNo source record no (counts records submitted by the application)
     * @param mappedRecordNo mapped record no (after preoutput transformer step)
     * @param recordId unique record id
     * @param record the record
     * @throws IOException, ApplicationException if there is an issue generating record data
     */
    void generateData(int recordNo, int mappedRecordNo, long recordId, BonaPortable record) throws IOException, ApplicationException;

    /**
     * Generate footer datas in a specific format. Does not close the underlying OutputStream.
     * @throws IOException, ApplicationException if there is an issue generating footer data
     */
    void close() throws IOException, ApplicationException;
}
