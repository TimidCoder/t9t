package com.arvatosystems.t9t.msglog.services;

import java.util.List;

import com.arvatosystems.t9t.msglog.MessageDTO;

public interface IMsglogPersistenceAccess {
    void open();
    void write(List<MessageDTO> entries);
    void close();
}
