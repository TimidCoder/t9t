package com.arvatosystems.t9t.unittest;

import de.jpaw.dp.Jdp;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.configuration.InjectingAnnotationEngine;

import java.lang.reflect.Field;

/**
 * Annotation injection bridge to combine JDP with Mockito
 */
public class JdpMockitoInjectingAnnotationEngine extends InjectingAnnotationEngine {

    @Override
    public void injectMocks(Object testClassInstance) {
        initJdp(testClassInstance);
        super.injectMocks(testClassInstance);
    }

    private void initJdp(Object testClassInstance) {
        Jdp.reset();

        scanAndBind(testClassInstance, testClassInstance.getClass());
    }

    @SuppressWarnings("unchecked")
    private void scanAndBind(Object testClassInstance, Class<?> clazz) {
        if (clazz.getSuperclass() != null) {
            scanAndBind(testClassInstance, clazz.getSuperclass());
        }

        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Mock.class)
                || field.isAnnotationPresent(Spy.class)) {

                try {
                    field.setAccessible(true);
                    Jdp.bindInstanceTo(field.get(testClassInstance), (Class<Object>)field.getType());
                } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                    throw new RuntimeException("Error during getting field " + field + " to bind in JDP", e);
                }
            }
        }
    }

}
