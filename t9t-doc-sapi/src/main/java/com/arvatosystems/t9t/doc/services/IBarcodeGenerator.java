package com.arvatosystems.t9t.doc.services;

import com.arvatosystems.t9t.barcode.api.BarcodeFormat;
import com.arvatosystems.t9t.doc.services.valueclass.ImageParameter;

import de.jpaw.bonaparte.pojos.api.media.MediaData;

// special case of IImageGenerator
public interface IBarcodeGenerator {
    MediaData generateBarcode(BarcodeFormat fmt, String text, ImageParameter params) throws Exception;
}
