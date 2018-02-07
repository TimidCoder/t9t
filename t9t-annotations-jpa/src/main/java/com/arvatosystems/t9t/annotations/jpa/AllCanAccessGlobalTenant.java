package com.arvatosystems.t9t.annotations.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to be placed as a marker in resolver source xtend files to add an automated tenantId filter which allows the current tenant or the global tenant
 * (for regular tenants). If not defined, only the own tenant will be visible.
 *
 * This corresponds to data category "D" (tenant specific with default)
 *
 * For entities which have a column tenantId, but which should be maintained manually.
 *
 * @author BISC02
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface AllCanAccessGlobalTenant {

}
