package com.arvatosystems.t9t.all.be.request

import com.arvatosystems.t9t.all.request.GetT9tModuleConfigsRequest
import com.arvatosystems.t9t.all.request.GetT9tModuleConfigsResponse
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.email.services.IEmailModuleCfgDtoResolver
import de.jpaw.dp.Inject
import com.arvatosystems.t9t.all.T9tModuleConfigs
import com.arvatosystems.t9t.auth.services.IAuthModuleCfgDtoResolver
import com.arvatosystems.t9t.doc.services.IDocModuleCfgDtoResolver
import com.arvatosystems.t9t.solr.services.ISolrModuleCfgDtoResolver

class GetT9tModuleConfigsRequestHandler extends AbstractReadOnlyRequestHandler<GetT9tModuleConfigsRequest> {
    @Inject protected IAuthModuleCfgDtoResolver  authModuleCfgResolver
    @Inject protected IDocModuleCfgDtoResolver   docModuleCfgResolver
    @Inject protected IEmailModuleCfgDtoResolver emailModuleCfgResolver
    @Inject protected ISolrModuleCfgDtoResolver  solrModuleCfgResolver

    override GetT9tModuleConfigsResponse execute(RequestContext ctx, GetT9tModuleConfigsRequest rq) {
        return new GetT9tModuleConfigsResponse => [
            moduleConfigs = new T9tModuleConfigs => [
                authModuleConfig    = authModuleCfgResolver.moduleConfiguration
                docModuleConfig     = docModuleCfgResolver.moduleConfiguration
                emailModuleConfig   = emailModuleCfgResolver.moduleConfiguration
                solrModuleConfig    = solrModuleCfgResolver.moduleConfiguration
            ]
        ]
    }
}
