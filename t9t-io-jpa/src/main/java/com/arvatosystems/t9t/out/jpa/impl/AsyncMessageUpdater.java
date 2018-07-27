/*
 * Copyright (c) 2012 - 2018 Arvato Systems GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arvatosystems.t9t.out.jpa.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.joda.time.Instant;

import com.arvatosystems.t9t.base.output.ExportStatusEnum;
import com.arvatosystems.t9t.io.AsyncMessageDTO;
import com.arvatosystems.t9t.io.jpa.entities.AsyncMessageEntity;
import com.arvatosystems.t9t.out.services.IAsyncMessageUpdater;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Singleton;

/**
 * Class which updates a single message entity.
 * This is a special implementation because no request context is available.
 * It is comparable to MsglogPersistenceAccess in t9t-msglog-jpa.
 */
@Singleton
public class AsyncMessageUpdater implements IAsyncMessageUpdater {
    protected final EntityManagerFactory emf = Jdp.getRequired(EntityManagerFactory.class);

    @Override
    public void updateMessage(Long objectRef, ExportStatusEnum newStatus, Integer httpCode, Integer clientCode, String clientReference) {
        final EntityManager em = emf.createEntityManager();
        final int allowedLength = AsyncMessageDTO.meta$$reference.getLength();
        if (clientReference != null && clientReference.length() > allowedLength)
            clientReference = clientReference.substring(0, allowedLength);
        em.getTransaction().begin();
        final AsyncMessageEntity m = em.find(AsyncMessageEntity.class, objectRef);
        if (m != null) {
            m.setAttempts(m.getAttempts() + 1);
            m.setLastAttempt(new Instant());
            m.setStatus(newStatus);
            m.setHttpResponseCode(httpCode);
            m.setReturnCode(clientCode);
            m.setReference(clientReference);
        }
        em.getTransaction().commit();
        em.clear();
        em.close();
    }
}
