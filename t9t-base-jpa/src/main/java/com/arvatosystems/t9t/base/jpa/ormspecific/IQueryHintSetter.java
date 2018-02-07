package com.arvatosystems.t9t.base.jpa.ormspecific;

import javax.persistence.Query;

/** API to set OR mapper specific query hints.
 * If a hint is not supported by a specific OR mapper, it should just be ignored.
 */
public interface IQueryHintSetter {
    /** Set a query to read-only. This saves space because a second copy per entity is not required, and also no dirty-checking required. */
    void setReadOnly(Query q);

    /** Provide a query comment. This usually appears in the logs and can be used to identify the source. */
    void setComment(Query q, String text);
}
