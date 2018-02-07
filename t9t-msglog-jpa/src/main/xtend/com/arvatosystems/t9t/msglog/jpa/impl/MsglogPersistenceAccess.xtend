package com.arvatosystems.t9t.msglog.jpa.impl

import com.arvatosystems.t9t.msglog.MessageDTO
import com.arvatosystems.t9t.msglog.services.IMsglogPersistenceAccess
import de.jpaw.dp.Inject
import de.jpaw.dp.Singleton
import java.util.List
import javax.persistence.EntityManagerFactory
import com.arvatosystems.t9t.msglog.jpa.entities.MessageEntity
import de.jpaw.annotations.AddLogger

@Singleton
@AddLogger
class MsglogPersistenceAccess implements IMsglogPersistenceAccess {
    @Inject EntityManagerFactory emf

    override open() {
    }

    override close() {
    }

    override write(List<MessageDTO> entries) {
        val em = emf.createEntityManager
        em.transaction.begin
        for (m : entries) {
            val e = new MessageEntity
            e.put$Data(m)
            // sanity check / sanitizing
            if (e.errorDetails !== null && e.errorDetails.length > MessageDTO.meta$$errorDetails.length)
                e.errorDetails = e.errorDetails.substring(0, MessageDTO.meta$$errorDetails.length)
            e.put$Key(m.objectRef)
            em.persist(e)
        }
        em.transaction.commit
        em.clear
    }
}
