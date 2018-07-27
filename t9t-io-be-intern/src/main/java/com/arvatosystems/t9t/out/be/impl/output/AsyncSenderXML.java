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
package com.arvatosystems.t9t.out.be.impl.output;

import java.io.ByteArrayOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arvatosystems.t9t.io.AsyncChannelDTO;
import com.arvatosystems.t9t.out.be.IStandardNamespaceWriter;
import com.arvatosystems.t9t.out.services.IAsyncSender;

import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.HttpPostResponseObject;
import de.jpaw.bonaparte.sock.HttpPostClient;
import de.jpaw.bonaparte.util.IMarshaller;
import de.jpaw.bonaparte.util.ToStringHelper;
import de.jpaw.dp.Dependent;
import de.jpaw.dp.Jdp;
import de.jpaw.util.ByteArray;
import de.jpaw.util.ByteBuilder;
import de.jpaw.util.ByteUtil;
import de.jpaw.util.ExceptionUtil;

// implementation is dependent, not Singleton, because HttpPostClient is stateful and therefore not thread safe
@Dependent
public class AsyncSenderXML<R extends BonaPortable> implements IAsyncSender<R> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncSenderXML.class);
    protected final XMLMarshaller<R> xmlMarshaller = new XMLMarshaller<R>();
    protected final HttpPostClient httpClient = new HttpPostClient("http://localhost/", false, false, false, false, xmlMarshaller);

    public AsyncSenderXML() {
        LOGGER.info("initializing base implementation of AsyncSenderXML");
    }

    // constructor which is used in case of superclasses
    protected AsyncSenderXML(Class<R> responseClass) {
        xmlMarshaller.setResponseClass(responseClass);
    }

    private final class XMLMarshaller<S> implements IMarshaller {
        private final IStandardNamespaceWriter jaxbContextProvider = Jdp.getRequired(IStandardNamespaceWriter.class);
        protected JAXBContext unmarshalContext = null;

        protected void setResponseClass(Class<S> responseClass) {
            try {
                unmarshalContext = JAXBContext.newInstance(responseClass);
            } catch (JAXBException e) {
                LOGGER.error("JAXB error: cannot set response class to {}: {}", responseClass.getCanonicalName(), ExceptionUtil.causeChain(e));
            }
        }

        @Override
        public String getContentType() {
            return "application/xml";
        }

        @Override
        public ByteArray marshal(BonaPortable obj) throws Exception {
            Marshaller marshaller = jaxbContextProvider.getStandardJAXBContext().createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            marshaller.marshal(obj, os);
            return ByteArray.fromByteArrayOutputStream(os);
        }

        @Override
        public BonaPortable unmarshal(ByteBuilder buffer) throws Exception {
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("Received data\n{}\n", ByteUtil.dump(buffer.getBytes(), 64));
            }
            if (unmarshalContext == null)
                return null;   // cannot parse without a target object in XML
            try {
                Unmarshaller unmarshaller = unmarshalContext.createUnmarshaller();
                return (BonaPortable)unmarshaller.unmarshal(buffer.asByteArrayInputStream());
            } catch (Exception e ) {
                LOGGER.error("Could not unmarshal message due to {}. Message was <{}>", ExceptionUtil.causeChain(e), buffer.toString());
                throw e;
            }
        }
    }

    @Override
    public HttpPostResponseObject fireMessage(AsyncChannelDTO channelDto, int timeout, BonaPortable payload) {
        // configure the http client
        httpClient.setBaseUrl(channelDto.getUrl());
        httpClient.setTimeoutInMs(channelDto.getTimeoutInMs() == null ? timeout : channelDto.getTimeoutInMs().intValue());  // set the request specific timeout or fall back to the default
        if (channelDto.getAuthParam() == null || channelDto.getAuthParam().trim().length() == 0) {
            httpClient.setAuthentication(null);
        } else {
            httpClient.setAuthentication(channelDto.getAuthParam());
        }

        // log message if desired (expensive!)
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("Sending payload {}", ToStringHelper.toStringML(payload));
        }

        try {
            return httpClient.doIO2(payload);
        } catch (Exception e) {
            String error = ExceptionUtil.causeChain(e);
            LOGGER.error("Exception in external http: {}", error);
            return new HttpPostResponseObject(999, error, null);
        }
    }
}
