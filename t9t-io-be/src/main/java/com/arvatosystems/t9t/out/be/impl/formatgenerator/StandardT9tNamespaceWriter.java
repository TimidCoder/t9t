package com.arvatosystems.t9t.out.be.impl.formatgenerator;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.arvatosystems.t9t.out.be.IStandardNamespaceWriter;

import de.jpaw.dp.Fallback;
import de.jpaw.dp.Singleton;

@Fallback
@Singleton
public class StandardT9tNamespaceWriter implements IStandardNamespaceWriter {

    @Override
    public void writeApplicationNamespaces(XMLStreamWriter writer) throws XMLStreamException {
        // no additional namespaces used in the framework itself
    }
}
