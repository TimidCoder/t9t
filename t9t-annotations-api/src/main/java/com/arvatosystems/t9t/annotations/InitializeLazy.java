package com.arvatosystems.t9t.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be placed on injected resolvers / mappers in EntityMappers, to indicate required lazy initialization.
 *
 * @author BISC02
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface InitializeLazy {
}
