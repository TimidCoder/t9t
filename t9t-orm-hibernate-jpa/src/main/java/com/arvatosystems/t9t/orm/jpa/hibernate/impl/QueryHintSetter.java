package com.arvatosystems.t9t.orm.jpa.hibernate.impl;

import javax.persistence.Query;

import com.arvatosystems.t9t.base.jpa.ormspecific.IQueryHintSetter;

import de.jpaw.dp.Singleton;

@Singleton
public class QueryHintSetter implements IQueryHintSetter {

    @Override
    public void setReadOnly(Query query) {
        query.setHint("org.hibernate.readOnly", true);
    }

    @Override
    public void setComment(Query query, String text) {
        query.setHint("org.hibernate.comment", text);
    }
}
