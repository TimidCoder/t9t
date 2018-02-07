package com.arvatosystems.t9t.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be placed as a marker in DTOs to mark a class as a unique key (or primary key) class. The value is the DTO to which it is a key. (If that DTO
 * inherits this class, it is a PK, else a secondary key).
 *
 * @author BISC02
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface IsUniqueKey {
    public String value();
}
