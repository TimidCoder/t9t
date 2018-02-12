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
package com.arvatosystems.t9t.cfg.be

import de.jpaw.annotations.AddLogger
import java.io.File
import java.io.PrintWriter
import java.io.StringReader
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

@AddLogger
class ConfigProvider {
    static private final String DEFAULT_CFG_FILENAME = System.getProperty("user.home") + "/.t9tconfig.xml"
    static private final ConcurrentMap<String,String> customParameters = new ConcurrentHashMap<String,String>(16);
    static private final char EQUALS_SIGN = '=';

    static private val postgresConfig = new T9tServerConfiguration => [
        persistenceUnitName         = "t9t-DS"    // hibernate / resourceLocal / postgres
        databaseConfiguration       = new RelationalDatabaseConfiguration => [
            username                = "fortytwo"
            password                = "EEtz9Ae0"
            databaseBrand           = DatabaseBrandType.POSTGRES
            jdbcDriverClass         = "org.postgresql.Driver"
            jdbcConnectString       = "jdbc:postgresql://10.6.237.202:8432/fortytwo"
        ]
        noSqlConfiguration          = new NoSqlDatabaseConfiguration => [
            hostname                = "localhost"
            port                    = 3000
            schemaName              = "test"
        ]
        keyPrefetchConfiguration    = new KeyPrefetchConfiguration => [
            strategy                = "lazySequenceJDBC"
            defaultKeyChunkSize     = 100
            prefetchedKeyChunkSize  = 20
            numKeyFetchThreads      = 1
//            prefetchByRtti          = new HashMap<Integer,Integer>(50) => [
//                put(1, 10)      // session
//                put(2, 500)     // messages
//            ]
        ]
        logWriterConfiguration      = new LogWriterConfiguration => [
            strategy                = "asynchronous"
            queueSize               = 1000  // currently ignore
        ]
        bpmConfiguration            = new BpmExecutionConfiguration => [
            strategy                = "noop"                   // for now...
        ]
        awsConfiguration            = new AWSConfiguration => [
            snsEndpoint             = "https://sns.eu-central-1.amazonaws.com"
            sqsEndpoint             = "https://sqs.eu-central-1.amazonaws.com"
        ]
        uplinkConfiguration         = new UplinkConfiguration => [
            url                     = "http://localhost:8880/dev/rest/fortytwobonsl"
        ]
        searchConfiguration         = new SearchConfiguration => [
            strategy                = "SOLR"
            defaultUrl              = "http://localhost:8880/solr6"
        ]
    ]

    static private T9tServerConfiguration myConfiguration = postgresConfig

    def private static getContext() {
        return JAXBContext.newInstance("com.arvatosystems.t9t.cfg.be")
    }

    def public static configToFile(T9tServerConfiguration cfg, String filename) {
        val m = context.createMarshaller
        val writer = new PrintWriter(filename, "UTF-8")
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
        m.marshal(cfg, writer)
        writer.close
    }

    def public static configFromFile(String filename) {
//        val reader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8)
        val u = context.createUnmarshaller
        val cfg = u.unmarshal(new File(filename))
        return cfg as T9tServerConfiguration
    }

    def public static configFromString(String data) {
        val u = context.createUnmarshaller
        val cfg = u.unmarshal(new StringReader(data))
        return cfg as T9tServerConfiguration
    }

    def public static T9tServerConfiguration getConfiguration() {
        return myConfiguration
    }

    def public static boolean isMocked(String what) {
        if (myConfiguration.mocks === null)
           return false
        return myConfiguration.mocks.contains(what)
    }

    def private static void mergeConfigurations(T9tServerConfiguration a, T9tServerConfiguration b) {
        myConfiguration = new T9tServerConfiguration => [
            persistenceUnitName     = a.persistenceUnitName     ?: b.persistenceUnitName
            databaseConfiguration   = a.databaseConfiguration   ?: b.databaseConfiguration
            noSqlConfiguration      = a.noSqlConfiguration      ?: b.noSqlConfiguration
            keyPrefetchConfiguration= a.keyPrefetchConfiguration?: b.keyPrefetchConfiguration
            logWriterConfiguration  = a.logWriterConfiguration  ?: b.logWriterConfiguration
            applicationConfiguration= a.applicationConfiguration?: b.applicationConfiguration
            serverConfiguration     = a.serverConfiguration     ?: b.serverConfiguration
            bpmConfiguration        = a.bpmConfiguration        ?: b.bpmConfiguration
            awsConfiguration        = a.awsConfiguration        ?: b.awsConfiguration
            uplinkConfiguration     = a.uplinkConfiguration     ?: b.uplinkConfiguration
            searchConfiguration     = a.searchConfiguration     ?: b.searchConfiguration
            mocks                   = a.mocks                   ?: b.mocks
            runInCluster            = a.runInCluster            ?: b.runInCluster
            disableScheduler        = a.disableScheduler        ?: b.disableScheduler
            importEnvironment       = a.importEnvironment       ?: b.importEnvironment
            eventEnvironment        = a.eventEnvironment        ?: b.eventEnvironment
            z                       = a.z                       ?: b.z
        ]

        // preprocess any custom fields into map for later easier access
        if (myConfiguration.z !== null) {
            customParameters.clear
            myConfiguration.z.forEach[
                val equalsPos = indexOf(EQUALS_SIGN)
                if (equalsPos > 0) {
                    // store key/value pair
                    customParameters.put(substring(0, equalsPos).trim, substring(equalsPos+1).trim)
                } else {
                    LOGGER.warn("Custom (z field) entry {} has no '=' delimiter, ignoring entry", it)
                }
            ]
            LOGGER.info("Read {} custom parameters from config file", customParameters.size)
        }
    }

    def public static String getCustomParameter(String key) {
        return customParameters.get(key)
    }

    // read the configuration from the provided file, or fallback to the home cfg file if none has been specified
    def public static void readConfiguration(String filename) {
        if (filename === null) {
            LOGGER.info("No configuration filename specified, using default {}", DEFAULT_CFG_FILENAME)
            try {
                val cfgFromFile = configFromFile(DEFAULT_CFG_FILENAME)
                mergeConfigurations(cfgFromFile, postgresConfig)
            } catch (Exception e) {
                // ignore issues because we are working on the default
                LOGGER.info("Falling back to default configuration due to {}", e.message ?: e.class.simpleName)
                return
            }
        } else {
            LOGGER.info("Using configuration file {}", filename)
            try {
                val cfgFromFile = configFromFile(filename)
                mergeConfigurations(cfgFromFile, postgresConfig)
            } catch (Exception e) {
                // fail on ignore issues because the cfg file was requested specifically
                LOGGER.info("{}: Cannot read configuration due to {}", e.class.simpleName, e.message)
                System.exit(1)
            }
        }
    }
}
