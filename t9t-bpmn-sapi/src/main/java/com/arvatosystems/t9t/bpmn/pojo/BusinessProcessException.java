package com.arvatosystems.t9t.bpmn.pojo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that should be used by any BPMN business exception class.
 * @author LIEE001
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BusinessProcessException {

    String errorCode();
}
