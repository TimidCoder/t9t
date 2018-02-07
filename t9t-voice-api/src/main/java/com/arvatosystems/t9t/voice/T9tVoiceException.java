package com.arvatosystems.t9t.voice;

import com.arvatosystems.t9t.base.T9tException;

/**
 * exception class for all t9t voice module specific exceptions.
 *
 */
public class T9tVoiceException extends T9tException {

    /**
     *
     */
    private static final long serialVersionUID = -866589603331210L;
    private static final int OFFSET = (CL_PARAMETER_ERROR * CLASSIFICATION_FACTOR) + 178000;

    // Error codes
    public static final int UNKNOWN_SKU_KEY_IMPLEMENTATION = OFFSET + 59;

    static {
        codeToDescription.put(UNKNOWN_SKU_KEY_IMPLEMENTATION, "unknown sku key implementation");
    }

    public T9tVoiceException(final int errorCode, final Object... detailParameters) {
        super(errorCode, detailParameters);
    }

    public T9tVoiceException(final int errorCode) {
        super(errorCode);
    }
}
