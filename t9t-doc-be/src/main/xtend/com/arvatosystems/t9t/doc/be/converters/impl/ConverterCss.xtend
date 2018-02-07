package com.arvatosystems.t9t.doc.be.converters.impl

import com.arvatosystems.t9t.doc.services.IDocComponentConverter
import de.jpaw.bonaparte.api.media.MediaTypeInfo
import de.jpaw.bonaparte.pojos.api.media.MediaCategory
import de.jpaw.bonaparte.pojos.api.media.MediaData
import de.jpaw.dp.Named
import de.jpaw.dp.Singleton
import java.util.Map

import static extension com.arvatosystems.t9t.doc.be.converters.impl.ConverterCssUtil.*

final class ConverterCssUtil {
    def static public getDimension(Map<String, Object> z, String key) {
        val o = z.get(key)
        if (o !== null && o instanceof Number)
            return o.toString + "px"
        else
            return o?.toString
    }

    def static public String sizeSpec(Map<String, Object> z) {
        if (z !== null) {
            val widthStr    = z.getDimension("width")
            val heightStr   = z.getDimension("height")
            if (widthStr !== null && heightStr !== null)
                return '''    width:«widthStr»;\n    height:«heightStr»;\n'''
        }
        return null
    }
}


// extend this class to add conversions from additional format types
@Singleton
@Named("CSS")
class ConverterToCss implements IDocComponentConverter {

    override convertFrom(MediaData src) {
        val descriptor = MediaTypeInfo.getFormatByType(src.mediaType)
        val z = src.z

        if (src.rawData !== null) {
            if (descriptor !== null && descriptor.formatCategory == MediaCategory.IMAGE) {
                return '''«z.sizeSpec»    background-image:url(data:«descriptor.mimeType»;base64,«src.rawData.asBase64»);\n'''
            }
        }
        if (src.text !== null) {
            if (descriptor !== null && descriptor.formatCategory == MediaCategory.IMAGE) {
                return '''«z.sizeSpec»    background-image:url(«src.text»);\n'''
            }

        }
        return null;        // policy is to skip unknowns, this will be reported as a warning
    }
}
