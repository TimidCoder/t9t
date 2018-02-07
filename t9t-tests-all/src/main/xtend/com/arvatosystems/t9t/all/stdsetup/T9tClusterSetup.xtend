package com.arvatosystems.t9t.all.stdsetup

import com.arvatosystems.t9t.base.ITestConnection
import de.jpaw.annotations.AddLogger
import org.eclipse.xtend.lib.annotations.Data
import com.arvatosystems.t9t.event.SubscriberConfigDTO

import static extension com.arvatosystems.t9t.misc.extensions.MiscExtensions.*
import com.arvatosystems.t9t.base.event.InvalidateCacheEvent

@AddLogger
@Data
class T9tClusterSetup {
    ITestConnection dlg

    public def void setupCacheInvalidationEntry() {
        new SubscriberConfigDTO => [
            eventID          = InvalidateCacheEvent.BClass.INSTANCE.pqon // "t9t.base.event.InvalidateCacheEvent"
            handlerClassName = "cacheInvalidation"
            isActive         = true
            merge(dlg)
        ]
    }
}
