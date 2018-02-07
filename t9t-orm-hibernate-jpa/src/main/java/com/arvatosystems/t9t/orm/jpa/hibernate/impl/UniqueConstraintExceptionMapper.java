package com.arvatosystems.t9t.orm.jpa.hibernate.impl;

import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.jpa.ormspecific.IJpaCrudTechnicalExceptionMapper;

import de.jpaw.dp.Singleton;


/**
 * Maps broken unique constraint technical exception to com.arvatosystems.fortytwo.base.exceptions.T9tException.UNIQUE_CONSTRAINT_VIOLATION.
 *
 * @author dzie003
 *
 */
@Singleton
public class UniqueConstraintExceptionMapper implements IJpaCrudTechnicalExceptionMapper {

    @Override
    public boolean handles(PersistenceException e) {
        return e.getCause() instanceof ConstraintViolationException;
    }

    @Override
    public T9tException mapException(PersistenceException e) {
        return new T9tException(T9tException.UNIQUE_CONSTRAINT_VIOLATION);
    }

}
