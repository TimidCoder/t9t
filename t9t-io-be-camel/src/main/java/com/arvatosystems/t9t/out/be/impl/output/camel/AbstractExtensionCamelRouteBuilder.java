package com.arvatosystems.t9t.out.be.impl.output.camel;

import org.apache.camel.builder.RouteBuilder;

/**
 * Abstract base class for all route builders used for generating additional camel routes from an extension module. All derived classes are instantiated at container start and added as route builder
 * to the camel context.
 */
public abstract class AbstractExtensionCamelRouteBuilder extends RouteBuilder {

}
