package com.arvatosystems.t9t.doc.services;

import com.arvatosystems.t9t.doc.services.valueclass.ImageParameter;

import de.jpaw.bonaparte.pojos.api.media.MediaData;

// implementations are qualified by name / type of the image, for example QR_CODE
public interface IImageGenerator {
    MediaData generateImage(String text, ImageParameter params) throws Exception;
}
