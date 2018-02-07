package com.arvatosystems.t9t.rep.services;

import com.arvatosystems.t9t.doc.api.DocumentSelector;
import com.arvatosystems.t9t.rep.ReportConfigDTO;
import com.arvatosystems.t9t.rep.ReportParamsDTO;

/**
 * Reponsible to send the email containing the report to user's email.
 * @author NGTZ001
 *
 */
public interface IReportMailNotifier {

    /**
     * @param reportConfigDTO report config used
     * @param reportParamsDTO report param used
     * @param mailGroup userIds separated by ","
     * @param docConfigId docConfig used for the document generation
     * @param sinkRef sink ref for the generated report
     * @param selector document selector
     */
    void sendEmail(ReportConfigDTO reportConfigDTO, ReportParamsDTO reportParamsDTO, String mailGroup, String docConfigId, Long sinkRef, DocumentSelector selector);

}
