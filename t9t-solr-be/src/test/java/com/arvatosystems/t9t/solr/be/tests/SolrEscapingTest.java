package com.arvatosystems.t9t.solr.be.tests;

import org.apache.solr.client.solrj.util.ClientUtils;
import org.junit.Test;

public class SolrEscapingTest {

    @Test
    public void testEscaping() throws Exception {
        String specialChars = "+ - && || ! ( ) { } [ ] ^ \" ~ * ? : \\";

        String escapedChars = ClientUtils.escapeQueryChars(specialChars);

        System.out.println("Result is " + escapedChars);
    }
}
