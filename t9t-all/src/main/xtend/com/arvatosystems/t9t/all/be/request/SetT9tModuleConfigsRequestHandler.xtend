package com.arvatosystems.t9t.all.be.request

import com.arvatosystems.t9t.all.request.SetT9tModuleConfigsRequest
import com.arvatosystems.t9t.auth.services.IAuthModuleCfgDtoResolver
import com.arvatosystems.t9t.base.api.ServiceResponse
import com.arvatosystems.t9t.base.services.AbstractReadOnlyRequestHandler
import com.arvatosystems.t9t.base.services.RequestContext
import com.arvatosystems.t9t.doc.services.IDocModuleCfgDtoResolver
import com.arvatosystems.t9t.email.services.IEmailModuleCfgDtoResolver
import com.arvatosystems.t9t.solr.services.ISolrModuleCfgDtoResolver
import de.jpaw.dp.Inject

class SetT9tModuleConfigsRequestHandler extends AbstractReadOnlyRequestHandler<SetT9tModuleConfigsRequest> {
    @Inject protected IAuthModuleCfgDtoResolver  authModuleCfgResolver
    @Inject protected IDocModuleCfgDtoResolver   docModuleCfgResolver
    @Inject protected IEmailModuleCfgDtoResolver emailModuleCfgResolver
    @Inject protected ISolrModuleCfgDtoResolver  solrModuleCfgResolver

    override ServiceResponse execute(RequestContext ctx, SetT9tModuleConfigsRequest rq) {
        val it = rq.moduleConfigs
        if (authModuleConfig  !== null) authModuleCfgResolver .updateModuleConfiguration(authModuleConfig)
        if (docModuleConfig   !== null) docModuleCfgResolver  .updateModuleConfiguration(docModuleConfig)
        if (emailModuleConfig !== null) emailModuleCfgResolver.updateModuleConfiguration(emailModuleConfig)
        if (solrModuleConfig  !== null) solrModuleCfgResolver .updateModuleConfiguration(solrModuleConfig)
        return ok
    }
}
