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
package com.arvatosystems.t9t.bpmn2.be.deployer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.arvatosystems.t9t.bpmn.T9tBPMException;
import com.arvatosystems.t9t.bpmn2.be.impl.InternalProcessDefinitionIdGenerator;

import de.jpaw.dp.Singleton;

/**
 * Responsible for analyzing/decorating/undecorating process definition XML content &lt;process&gt; tags with tenant id and process id information.
 * Example:
 * <pre>
 *   &lt;definitions&gt;
 *     &lt;process id="process1"&gt;
 *     &lt;/process&gt;
 *   &lt;/definitions&gt;
 * </pre>
 * will be decorated into
 * <pre>
 *   &lt;definitions&gt;
 *     &lt;process id="TenantId_process1"&gt;
 *     &lt;/process&gt;
 *   &lt;/definitions&gt;
 * </pre>
 * and vice versa!
 * @author LIEE001
 */
@Singleton
public class ProcessDefinitionContentDecorator {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProcessDefinitionContentDecorator.class);

    private interface ProcessIdValueProcessor extends Serializable {
        String getNewIdValue(String currentIdValue);
    }

    private class ProcessIdCorrectorProcessor implements ProcessIdValueProcessor {

        private static final long serialVersionUID = 9191719654260981543L;

        private String processDefinitionId;

        public ProcessIdCorrectorProcessor(final String processDefinitionId) {
            this.processDefinitionId = processDefinitionId;
        }

        @Override
        public String getNewIdValue(final String currentIdValue) {
            return processDefinitionId;
        }

    }

    private class TenantIdPrependerProcessor implements ProcessIdValueProcessor {

        private static final long serialVersionUID = -6112359757480228544L;
        private String tenantId;
        private String processDefinitionId;

        public TenantIdPrependerProcessor(final String tenantId, final String processDefinitionId) {
            this.tenantId = tenantId;
            this.processDefinitionId = processDefinitionId;
        }

        @Override
        public String getNewIdValue(final String currentIdValue) {
            return InternalProcessDefinitionIdGenerator.generateId(tenantId, processDefinitionId);
        }

    }

    private class TenantIdRemovalProcessor implements ProcessIdValueProcessor {

        private static final long serialVersionUID = -4323991627074735065L;

        @Override
        public String getNewIdValue(final String currentIdValue) {
            return currentIdValue.substring(currentIdValue.indexOf("_") + 1);
        }

    }

    /**
     * Get the available "id" attribute value of &lt;process&gt; tag.
     * @param inputStream target input stream
     * @return process IDs
     * @throws T9tBPMException if there is any error occurred during retrieval
     */
    public List<String> getProcessDefinitionIds(final InputStream inputStream) {
        try {
            List<String> ids = new ArrayList<>();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);

            // get all <process> tags
            NodeList processNodeList = document.getElementsByTagName("process");

            // loop through each <process> tag
            for (int i = 0; i < processNodeList.getLength(); i++) {
                Node processNode = processNodeList.item(i);

                // get <process> "id" attribute
                Node idAttr = processNode.getAttributes().getNamedItem("id");

                ids.add(idAttr.getTextContent());
            }

            return ids;
        } catch (DOMException | ParserConfigurationException | IOException | SAXException ex) {
            LOGGER.error("Failed to retrieve process definition(s) IDs", ex);
            throw new T9tBPMException(T9tBPMException.BPM_PROCESS_CONTENT_ERROR);
        }
    }

    private InputStream processProcessDefinitionContentInternal(final InputStream inputStream, final ProcessIdValueProcessor processor) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);

            // get all <process> tags
            NodeList processNodeList = document.getElementsByTagName("process");

            // loop through each <process> tag
            for (int i = 0; i < processNodeList.getLength(); i++) {
                Node processNode = processNodeList.item(i);

                // get <process> "id" attribute
                Node idAttr = processNode.getAttributes().getNamedItem("id");

                // process current "id" attribute value
                idAttr.setTextContent(processor.getNewIdValue(idAttr.getTextContent()));

            }

            // store decorated document in byte array
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(outputStream);
            transformer.transform(source, result);

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (DOMException | TransformerException |
                ParserConfigurationException | IOException | SAXException ex) {
            LOGGER.error("Failed to decoreate/undecorate process definition XML content with tenant id information", ex);
            throw new T9tBPMException(T9tBPMException.BPM_PROCESS_CONTENT_ERROR);
        }
    }

    /**
     * Decorate process definition XML content &lt;process&gt; tags with tenant id AND correct process definition id.
     * @param inputStream target input stream
     * @param tenantId target tenant id
     * @param processDefinitionId process definition id
     * @return input stream with decorated content
     * @throws T9tBPMException if there is any error occurred during decoration
     */
    public InputStream decorateProcessDefinitionContent(final InputStream inputStream, final String tenantId,
            final String processDefinitionId) {
        return processProcessDefinitionContentInternal(inputStream, new TenantIdPrependerProcessor(tenantId, processDefinitionId));
    }

    /**
     * Decorate process definition XML content &lt;process&gt; tags with the correct process definition id.
     * @param inputStream target input stream
     * @param processDefinitionId process definition id
     * @return input stream with decorated content
     * @throws T9tBPMException if there is any error occurred during decoration
     */
    public InputStream decorateProcessDefinitionContent(final InputStream inputStream, final String processDefinitionId) {
        return processProcessDefinitionContentInternal(inputStream, new ProcessIdCorrectorProcessor(processDefinitionId));
    }

    /**
     * Undecorate process definition XML content i.e. reversing the process of decoration.
     * @param inputStream target input stream
     * @return input stream with undecorated content
     * @throws T9tBPMException if there is any error occurred during undecoration
     */
    public InputStream undecorateProcessDefinitionContent(final InputStream inputStream) {
        return processProcessDefinitionContentInternal(inputStream, new TenantIdRemovalProcessor());
    }

}
