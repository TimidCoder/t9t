package com.arvatosystems.t9t.doc.be.tests

import com.arvatosystems.t9t.barcode.api.FlipMode
import com.arvatosystems.t9t.barcode.be.impl.BarcodeCode128Generator
import com.arvatosystems.t9t.doc.services.valueclass.ImageParameter
import com.google.common.io.Files
import java.io.File
import org.junit.Ignore
import org.junit.Test

class BarcodeRotationTest {

    @Ignore
    @Test
    def public void testBarcodeNormal() {
        val generator = new BarcodeCode128Generator
        val barcode = generator.generateImage("752173572135", new ImageParameter(128, 32, 0, FlipMode.NO_FLIPPING, 1.0))
        val file = new File("c:/git/barcodeN.png");
        val sink = Files.asByteSink(file);
        sink.write(barcode.rawData.bytes);
    }

    @Ignore
    @Test
    def public void testBarcodeRotation() {
        val generator = new BarcodeCode128Generator
        val barcode = generator.generateImage("752173572135", new ImageParameter(128, 32, 90, FlipMode.NO_FLIPPING, 1.0))
        val file = new File("c:/git/barcodeR.png");
        val sink = Files.asByteSink(file);
        sink.write(barcode.rawData.bytes);
    }

    @Ignore
    @Test
    def public void testBarcodeRotationAndMirrored() {
        val generator = new BarcodeCode128Generator
        val barcode = generator.generateImage("752173572135", new ImageParameter(128, 32, 90, FlipMode.FLIP_HORIZONTALLY, 1.0))
        val file = new File("c:/git/barcodeRH.png");
        val sink = Files.asByteSink(file);
        sink.write(barcode.rawData.bytes);
    }

    @Ignore
    @Test
    def public void testBarcodeHorizontalFlip() {
        val generator = new BarcodeCode128Generator
        val barcode = generator.generateImage("752173572135", new ImageParameter(128, 32, 0, FlipMode.FLIP_HORIZONTALLY, 1.0))
        val file = new File("c:/git/barcodeH.png");
        val sink = Files.asByteSink(file);
        sink.write(barcode.rawData.bytes);
    }
}
