package com.arvatosystems.t9t.msglog.jpa.mapping.impl

import com.arvatosystems.t9t.annotations.jpa.AutoHandler
import com.arvatosystems.t9t.annotations.jpa.AutoMap42
import com.arvatosystems.t9t.msglog.MessageDTO
import com.arvatosystems.t9t.msglog.jpa.entities.MessageEntity
import com.arvatosystems.t9t.msglog.jpa.persistence.IMessageEntityResolver

@AutoMap42
public class MessageMappers {
    IMessageEntityResolver resolver

    @AutoHandler("SP42")
    def void e2dMessageDTO(MessageEntity it, MessageDTO dto) {}
}
