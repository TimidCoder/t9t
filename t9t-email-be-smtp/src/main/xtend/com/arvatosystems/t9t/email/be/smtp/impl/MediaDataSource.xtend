package com.arvatosystems.t9t.email.be.smtp.impl

import de.jpaw.bonaparte.api.media.MediaTypeInfo
import de.jpaw.bonaparte.pojos.api.media.MediaData
import java.io.ByteArrayInputStream
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.Map
import javax.activation.DataSource

class MediaDataSource implements DataSource {
    private final MediaData data
    private final String name

    def private static String defaultName(Map<String, Object> z) {
        val cid = z?.get("cid")
        if (cid !== null && cid instanceof String)
            return cid as String
        return null
    }

    new(MediaData data) {
        this.data = data
        this.name = defaultName(data.z) //?: "X" + Integer.toString(counter.incrementAndGet)
    }

    override getContentType() {
        return MediaTypeInfo.getFormatByType(data.mediaType).mimeType
    }

    override getInputStream() throws IOException {
        if (data.text !== null)
            return new ByteArrayInputStream(data.text.getBytes(StandardCharsets.UTF_8))
        else
            return data.rawData.asByteArrayInputStream
    }

    override getName() {
        return name
    }

    override getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Cannot write to MediaDataSource")
    }
}
