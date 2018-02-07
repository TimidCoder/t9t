package com.arvatosystems.t9t.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Annotation to be placed as a marker in DTOs to mark fields which should use a specific dropdown in the UI
 * instead of text entry fields. Examples are countryCode, currencyCode in t9t, but also locationId, carrierId etc in OMS...
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UseDropDownInUI {
    public String value();
}
