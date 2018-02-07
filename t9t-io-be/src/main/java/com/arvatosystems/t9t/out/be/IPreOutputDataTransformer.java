package com.arvatosystems.t9t.out.be;

import java.util.List;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.output.OutputSessionParameters;
import com.arvatosystems.t9t.base.services.IOutputSession;
import com.arvatosystems.t9t.io.DataSinkDTO;

import de.jpaw.bonaparte.core.BonaPortable;

/**
 * An {@linkplain IOutputSession} hook which allow transformation of output data before it gets stored.
 *
 * @author LIEE001
 */
@FunctionalInterface
public interface IPreOutputDataTransformer {

    /**
     * Returns fixed header data.
     * @param sinkCfg data sink configuration
     * @param outputSessionParameters output session parameters
     * @return header data
     * @throws T9tException if there is an issue creating header data
     */
    default List<BonaPortable> headerData(DataSinkDTO sinkCfg, OutputSessionParameters outputSessionParameters) {
        return null;
    }

    /**
     * Transformed a record into possible N transformed record.
     * @param record the record
     * @param sinkCfg data sink configuration
     * @param outputSessionParameters output session parameters
     * @return transformed records
     * @throws T9tException if there is an issue transforming record data
     */
    List<BonaPortable> transformData(BonaPortable record, DataSinkDTO sinkCfg, OutputSessionParameters outputSessionParameters);

    /**
     * Returns fixed footer data.
     * @param sinkCfg data sink configuration
     * @param outputSessionParameters output session parameters
     * @return footer data
     * @throws T9tException if there is an issue creating footer data
     */
    default List<BonaPortable> footerData(DataSinkDTO sinkCfg, OutputSessionParameters outputSessionParameters) {
        return null;
    }
}
