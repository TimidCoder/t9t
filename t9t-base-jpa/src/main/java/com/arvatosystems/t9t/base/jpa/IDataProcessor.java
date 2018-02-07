package com.arvatosystems.t9t.base.jpa;

import java.io.Serializable;

import de.jpaw.bonaparte.jpa.BonaPersistableKey;
import de.jpaw.bonaparte.jpa.BonaPersistableTracking;
import de.jpaw.bonaparte.pojos.api.TrackingBase;

/** To be applied for sequential data processing of large result sets. Can be supplied to generic search,
 * in order to convert to some DTO and output through IOutputSession, for example.
 *
 * @param <KEY>
 * @param <TRACKING>
 * @param <ENTITY>
 */
public interface IDataProcessor<
    KEY extends Serializable,
    TRACKING extends TrackingBase,
    ENTITY extends BonaPersistableKey<KEY> & BonaPersistableTracking<TRACKING>> {

    void process(ENTITY entity);
}
