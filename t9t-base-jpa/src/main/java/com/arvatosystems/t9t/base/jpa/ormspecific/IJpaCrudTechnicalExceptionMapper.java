package com.arvatosystems.t9t.base.jpa.ormspecific;

import javax.persistence.PersistenceException;

import com.arvatosystems.t9t.base.T9tException;

/**
 * Defines interface for mappers mapping technical exceptions which can occur during crud operation on entity into business exceptions defined in
 * com.arvatosystems.t9t.core.T9tException class.
 *
 * @author dzie003
 *
 */
public interface IJpaCrudTechnicalExceptionMapper {

    /**
     * Checks if mapper is able to handle given technical exception.
     *
     * @param technicalException
     *            technical exception thrown during entity crud operation
     * @return <code>true</code> if mapper can map given exception into business exception, <code>false</code> otherwise.
     */
    public boolean handles(PersistenceException technicalException);

    /**
     * Maps given technical exception into business exception.
     *
     * @param technicalException
     *            technical exception thrown during entity crud operation
     * @return corresponding business exception
     */
    public T9tException mapException(PersistenceException technicalException);

}
