package com.arvatosystems.t9t.solr;

import com.arvatosystems.t9t.base.CrudViewModel;
import com.arvatosystems.t9t.base.IViewModelContainer;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.solr.request.SolrModuleCfgCrudRequest;
import com.arvatosystems.t9t.solr.request.SolrModuleCfgSearchRequest;

public class T9tSolrModels implements IViewModelContainer {
    public static final CrudViewModel<SolrModuleCfgDTO, FullTrackingWithVersion> SOLR_MODULE_CFG_VIEW_MODEL = new CrudViewModel<SolrModuleCfgDTO, FullTrackingWithVersion>(
        SolrModuleCfgDTO.BClass.INSTANCE,
        FullTrackingWithVersion.BClass.INSTANCE,
        SolrModuleCfgSearchRequest.BClass.INSTANCE,
        SolrModuleCfgCrudRequest.BClass.INSTANCE);

    static {
        IViewModelContainer.CRUD_VIEW_MODEL_REGISTRY.putIfAbsent("solrModuleCfg",  SOLR_MODULE_CFG_VIEW_MODEL);
    }
}
