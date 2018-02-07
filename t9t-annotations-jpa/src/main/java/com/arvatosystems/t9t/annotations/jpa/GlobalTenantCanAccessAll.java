package com.arvatosystems.t9t.annotations.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation to be placed as a marker in resolver source xtend files to prevent automated
 * adding of a tenantId filter for search or CRUD operations (of the global tenant).
 *
 * For entities which have a column tenantId, but which should be maintained manually.
 *
 * @author BISC02
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface GlobalTenantCanAccessAll {

}
