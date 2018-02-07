package com.arvatosystems.t9t.doc.services.valueclass;

import com.arvatosystems.t9t.barcode.api.FlipMode;

public class ImageParameter {
    public final int       width;
    public final int       height;
    public final Integer   rotation;   // rotation in degrees
    public final FlipMode  flipMode;
    public final Double    scale;

    public ImageParameter(int width, int height, Integer rotation, FlipMode flipMode, Double scale) {
        this.width    = width;
        this.height   = height;
        this.rotation = rotation;
        this.flipMode = flipMode;
        this.scale    = scale;
    }
}
