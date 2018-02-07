package com.arvatosystems.t9t.solr.be;

import org.apache.solr.client.solrj.SolrClient;

public interface ISolrServerCache {
    SolrClient get(String solrCoreUrl);
}
