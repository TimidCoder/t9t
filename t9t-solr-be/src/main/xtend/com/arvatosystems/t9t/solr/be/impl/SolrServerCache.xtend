package com.arvatosystems.t9t.solr.be.impl

import com.arvatosystems.t9t.solr.be.ISolrServerCache
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import de.jpaw.annotations.AddLogger
import de.jpaw.dp.Singleton
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.impl.HttpSolrClient

/**
 * A cache for http SOLR server instances. They have to be reused, creating servers for each and every request is quite slow and may lead to connection leaks
 */
@AddLogger
@Singleton
class SolrServerCache implements ISolrServerCache {
    //val Map<String, SolrServer> serverCache = new ConcurrentHashMap<String, SolrServer>
    val Cache<String, SolrClient> serverCache = CacheBuilder.newBuilder.build
//     [ url |
//        LOGGER.info('''Creating new SOLR client for URL «url»''')
//        new HttpSolrServer(url)
//    ];

    override get(String solrCoreUrl) {

        return serverCache.get(solrCoreUrl) [
            LOGGER.info('''Creating new SOLR client for URL «solrCoreUrl»''')
            new HttpSolrClient(solrCoreUrl)
        ];
    }
}
