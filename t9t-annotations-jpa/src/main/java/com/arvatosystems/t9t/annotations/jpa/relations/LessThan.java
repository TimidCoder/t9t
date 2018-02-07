package com.arvatosystems.t9t.annotations.jpa.relations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation to be placed as a marker in resolver source xtend files to allow specification of relations for findBy methods.
 *
 * @author BISC02
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface LessThan {
}
