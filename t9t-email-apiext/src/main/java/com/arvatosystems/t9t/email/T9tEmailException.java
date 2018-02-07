package com.arvatosystems.t9t.email;

import com.arvatosystems.t9t.base.T9tException;

/**
 * This class contains all exception codes used in email module.
 */
public class T9tEmailException extends T9tException {

    /**
     *
     */
    private static final long serialVersionUID = -71286196818412432L;
    /**
     * generated serial version id.
     */

    /*
     * Offset for all codes in this class.
     */
    private static final int CORE_OFFSET = 190000;
    private static final int OFFSET = (CL_PARAMETER_ERROR * CLASSIFICATION_FACTOR) + CORE_OFFSET;

    public static final int SMTP_IMPLEMENTATION_MISSING      = OFFSET + 966;
    public static final int EMAIL_SEND_ERROR                 = OFFSET + 967;
    public static final int SMTP_ERROR                       = OFFSET + 968;
    public static final int MIME_MESSAGE_COMPOSITION_PROBLEM = OFFSET + 969;

    /**
     * static initialization of all error codes
     */
    static {
        codeToDescription.put(SMTP_IMPLEMENTATION_MISSING,      "Configured STMP backend implementation not available.");
        codeToDescription.put(EMAIL_SEND_ERROR,                 "Error occured during sending email.");
        codeToDescription.put(SMTP_ERROR,                       "Error occured during sending email. (SMTP layer)");
        codeToDescription.put(MIME_MESSAGE_COMPOSITION_PROBLEM, "Problem composing MIME message.");
    }

    public T9tEmailException(int errorCode) {
        super(errorCode);
    }


    /**
     * Create an exception for a specific error code. Please do not put redundant text (duplicating the text of the error code) into detailParameter, only
     * additional info.
     *
     * @param errorCode
     *            The unique code describing the error cause.
     * @param detailParameter
     *            Any additional information / parameter. Do not put redundant text from the error code itself here! In most cases this should be just the value
     *            causing the problem.
     */
    public T9tEmailException(int errorCode, Object... detailParameters) {
        super(errorCode, detailParameters);
    }
}
