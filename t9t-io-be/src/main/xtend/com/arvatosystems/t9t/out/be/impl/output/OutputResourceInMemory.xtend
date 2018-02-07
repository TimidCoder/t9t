package com.arvatosystems.t9t.out.be.impl.output

import com.arvatosystems.t9t.base.output.OutputSessionParameters
import com.arvatosystems.t9t.io.DataSinkDTO
import com.arvatosystems.t9t.out.services.IOutputResource
import de.jpaw.bonaparte.pojos.api.media.MediaTypeDescriptor
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

/** for testing mainly */
class OutputResourceInMemory implements IOutputResource {
    protected final ByteArrayOutputStream baos = new ByteArrayOutputStream
    protected Charset encoding;

    override close() {
    }

    override getOutputStream() {
        return baos
    }

    override open(DataSinkDTO config, OutputSessionParameters params, Long sinkRef, String targetName, MediaTypeDescriptor mediaType, Charset encoding) {
        this.encoding = encoding;
    }

    override write(byte[] buffer, int offset, int len, boolean isDataRecord) {
        baos.write(buffer, offset, len);
    }

    override write(String data) {
        baos.write(data.getBytes(encoding))
    }

    def getBytes() {
        return baos.toByteArray
    }

    override toString() {
        return baos.toString(encoding.name)
    }
}
