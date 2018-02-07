package com.arvatosystems.t9t.barcode.be.api;

import com.arvatosystems.t9t.barcode.api.GenerateBarcodeRequest;
import com.arvatosystems.t9t.barcode.api.GenerateBarcodeResponse;
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler;
import com.arvatosystems.t9t.doc.services.IBarcodeGenerator;
import com.arvatosystems.t9t.doc.services.valueclass.ImageParameter;

import de.jpaw.bonaparte.pojos.api.media.MediaData;
import de.jpaw.dp.Jdp;

public class GenerateBarcodeRequestHandler extends AbstractReadOnlyRequestHandler<GenerateBarcodeRequest> {

    protected final IBarcodeGenerator generator = Jdp.getRequired(IBarcodeGenerator.class);

    @Override
    public GenerateBarcodeResponse execute(GenerateBarcodeRequest rq) throws Exception {
        final ImageParameter params = new ImageParameter(rq.getWidth(), rq.getHeight(), rq.getRotation(), rq.getFlipMode(), rq.getScale());
        MediaData m = generator.generateBarcode(rq.getBarcodeFormat(), rq.getText(), params);

        GenerateBarcodeResponse r = new GenerateBarcodeResponse();
        r.setBarcode(m);
        return r;
    }
}
