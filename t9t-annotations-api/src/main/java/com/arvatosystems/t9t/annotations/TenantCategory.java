package com.arvatosystems.t9t.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation to be placed as a marker in DTOs to mark the treatment of the tenant.
 *
 * @author BISC02
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface TenantCategory {
    public String value();
}
