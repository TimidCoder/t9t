package com.arvatosystems.t9t.annotations.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be placed as a marker in active annotations to control the creation of request handlers. The parameter string specifies, which request handler
 * to create:
 *
 * C=CrudRequestHandler, A=ReadAllRequestHandler, R=RefResolverRequestHandler, S=SearchRequestHandler
 *
 * Often you may want to omit the crud handler due to added manual methods.
 *
 * @author BISC02
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface AutoHandler {
    String value();
}
