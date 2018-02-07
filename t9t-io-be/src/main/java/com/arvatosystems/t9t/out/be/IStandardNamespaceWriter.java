package com.arvatosystems.t9t.out.be;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface IStandardNamespaceWriter {
    void writeApplicationNamespaces(XMLStreamWriter writer) throws XMLStreamException;
}
