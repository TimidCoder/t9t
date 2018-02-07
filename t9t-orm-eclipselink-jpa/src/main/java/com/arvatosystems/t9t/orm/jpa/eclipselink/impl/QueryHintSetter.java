package com.arvatosystems.t9t.orm.jpa.eclipselink.impl;

import javax.persistence.Query;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import com.arvatosystems.t9t.base.jpa.ormspecific.IQueryHintSetter;

import de.jpaw.dp.Singleton;

@Singleton
public class QueryHintSetter implements IQueryHintSetter {

    @Override
    public void setReadOnly(Query query) {
        query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
    }

    @Override
    public void setComment(Query query, String text) {
    }
}
