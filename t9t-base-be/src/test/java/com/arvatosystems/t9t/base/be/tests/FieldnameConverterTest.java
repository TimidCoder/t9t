package com.arvatosystems.t9t.base.be.tests;

import org.junit.Assert;
import org.junit.Test;

import com.arvatosystems.t9t.base.T9tException;

public class FieldnameConverterTest {
    // method taken from AbstractEntityMapper....
    private String unrollIndexedFieldNames(final String fieldName) {
        int dotPos = fieldName.indexOf('[');
        if (dotPos < 0)
            return fieldName;  // no change: shortcut!
        // has to replace at least one array index
        final int l = fieldName.length();
        StringBuilder newName = new StringBuilder(l);
        int currentSrc = 0;
        while (dotPos >= 0) {
            // copy all until the array index start
            newName.append(fieldName.substring(currentSrc, dotPos));
            currentSrc = dotPos + 1;
            dotPos = fieldName.indexOf(']', currentSrc);
            if (dotPos <= currentSrc)
                throw new T9tException(T9tException.MALFORMATTED_FIELDNAME, fieldName);
            // insert the index + 1 as 2 digit number
            int num = Integer.parseInt(fieldName.substring(currentSrc, dotPos));
            newName.append(String.format("%02d", num+1));
            currentSrc = dotPos + 1;
            dotPos = fieldName.indexOf('[', currentSrc);
        }
        // all instances found, copy any remaining characters
        if (currentSrc < l)
            newName.append(fieldName.substring(currentSrc));
        return newName.toString();  // temporary var to avoid duplicate construction of string when log level is debug
    }

    @Test
    public void testConverter() throws Exception {
        String in = "extraCustomerIds[0]";

        String converted = unrollIndexedFieldNames(in);
        Assert.assertEquals("extraCustomerIds01", converted);
    }

}
