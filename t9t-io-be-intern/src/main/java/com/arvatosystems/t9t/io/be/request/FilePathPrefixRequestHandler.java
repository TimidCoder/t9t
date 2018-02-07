package com.arvatosystems.t9t.io.be.request;

import com.arvatosystems.t9t.base.api.ServiceResponse;
import com.arvatosystems.t9t.base.services.AbstractRequestHandler;
import com.arvatosystems.t9t.base.services.IFileUtil;
import com.arvatosystems.t9t.io.request.FilePathPrefixRequest;
import com.arvatosystems.t9t.io.request.FilePathPrefixResponse;

import de.jpaw.dp.Jdp;

public class FilePathPrefixRequestHandler extends AbstractRequestHandler<FilePathPrefixRequest> {

    protected final  IFileUtil fileUtil = Jdp.getRequired(IFileUtil.class);

    @Override
    public ServiceResponse execute(FilePathPrefixRequest request) throws Exception {
        FilePathPrefixResponse response = new FilePathPrefixResponse();
        response.setReturnCode(0);
        response.setPrefix(fileUtil.getFilePathPrefix());
        return response;
    }
}
