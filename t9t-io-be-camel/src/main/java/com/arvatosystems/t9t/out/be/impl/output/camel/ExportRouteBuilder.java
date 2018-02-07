package com.arvatosystems.t9t.out.be.impl.output.camel;

import de.jpaw.dp.Dependent;


/**
 *
 * Route that should be triggered for every camel export. Entry point is 'direct:outputFile'.
 */
@Dependent
public class ExportRouteBuilder extends AbstractExtensionCamelRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:outputFile").bean(CamelOutputProcessor.class);

    }

}
