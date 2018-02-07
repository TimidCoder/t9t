package com.arvatosystems.t9t.email.be.vertx.impl

import de.jpaw.bonaparte.api.media.MediaTypeInfo
import de.jpaw.bonaparte.pojos.api.media.MediaData
import io.vertx.core.buffer.Buffer
import java.util.Map

class MediaDataSource {
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

    def getContentType() {
        return MediaTypeInfo.getFormatByType(data.mediaType).mimeType
    }

    def Buffer asBuffer() {
        if (data.text !== null)
            return Buffer.buffer(data.text)
        else
            return Buffer.buffer(data.rawData.bytes)  // duplicate copy, unfortunately
    }

    def getName() {
        return name
    }
}
