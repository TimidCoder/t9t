package com.arvatosystems.t9t.genconf.be.request;

import com.arvatosystems.t9t.genconf.ConfigDTO;
import com.arvatosystems.t9t.genconf.request.ConfigSearchRequest;
import com.arvatosystems.t9t.genconf.services.IConfigResolver;
import com.arvatosystems.t9t.base.be.impl.AbstractSearchBERequestHandler;
import com.arvatosystems.t9t.base.entities.FullTrackingWithVersion;
import com.arvatosystems.t9t.base.search.ReadAllResponse;

import de.jpaw.dp.Jdp;

public class ConfigSearchRequestHandler extends AbstractSearchBERequestHandler<ConfigDTO, FullTrackingWithVersion, ConfigSearchRequest> {

    // @Inject
    protected final IConfigResolver resolver = Jdp.getRequired(IConfigResolver.class);

    @Override
    public ReadAllResponse<ConfigDTO, FullTrackingWithVersion> execute(ConfigSearchRequest request) throws Exception {
        return execute(resolver.query(
                request.getLimit(),
                request.getOffset(),
                request.getSearchFilter(),
                request.getSortColumns()),
                request.getSearchOutputTarget());
    }
}
