package com.arvatosystems.t9t.in.be.impl

import de.jpaw.annotations.AddLogger
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

@AddLogger
abstract class AbstractTextFormatConverter extends AbstractInputFormatConverter {

    def abstract void process(String textLine);

    override process(InputStream is) {
        val streamReader    = new BufferedReader(new InputStreamReader(is))
        try {
            for (var String line = streamReader.readLine(); line !== null; line = streamReader.readLine()) {
                process(line)
            }
        } finally {
            try {
                streamReader.close();
            } catch (IOException f) {
                // should (hopefully) never happen because its already caught beforehand.
            }
        }
    }
}
