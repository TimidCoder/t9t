package com.arvatosystems.t9t.doc.be.pdf.tests

import com.arvatosystems.t9t.doc.services.IDocConverter
import com.arvatosystems.t9t.jdp.Init
import de.jpaw.bonaparte.pojos.api.media.MediaData
import de.jpaw.bonaparte.pojos.api.media.MediaType
import org.junit.Test

import static extension de.jpaw.dp.Jdp.*

class HtmlToPdfTest {

    @Test
    def public void testHtmlToPdf() {
        Init.initializeT9t
        val src = new MediaData => [
            text = '''
                <html>
                    <head>
                        <title>Mego-Test</title>
                    </head>
                    <body>
                        <h1>Title</h1>
                        Some text
                    </body>
                </html>
            '''
            mediaType = MediaType.HTML
        ]
        val dst = IDocConverter.getRequired(MediaType.PDF.name)?.convert(src)
        if (dst === null || dst.rawData === null)
            throw new Exception("Could not convert HTML to PDF")
        println('''Length of generated PDF is «dst.rawData.length» bytes''')
    }
}
