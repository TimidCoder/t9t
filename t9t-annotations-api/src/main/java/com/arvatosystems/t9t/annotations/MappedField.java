package com.arvatosystems.t9t.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be placed as a comment in external interfaces, to define corresponding internal fields.
 * The value should be of format SimpleClassName.fieldName
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface MappedField {
    public String value();
}
