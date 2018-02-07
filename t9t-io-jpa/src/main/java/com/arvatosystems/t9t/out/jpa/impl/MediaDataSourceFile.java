package com.arvatosystems.t9t.out.jpa.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.base.services.IFileUtil;
import com.arvatosystems.t9t.base.services.RequestContext;
import com.arvatosystems.t9t.base.services.SimplePatternEvaluator;
import com.arvatosystems.t9t.io.services.IMediaDataSource;

import de.jpaw.dp.Jdp;
import de.jpaw.dp.Named;
import de.jpaw.dp.Singleton;

// file download handler implementation for FILE
@Singleton
@Named("FILE")
public class MediaDataSourceFile implements IMediaDataSource {
    protected final IFileUtil fileUtil = Jdp.getRequired(IFileUtil.class);

    @Override
    public InputStream open(String path) throws Exception {
        return new FileInputStream(path);
    }

    /**
     * Attach absolute path prefix and tenantId.
     *
     * @param sink
     *            sink holding relative file path
     * @return absolute file path
     * @throws T9tException
     *             thrown when building absolute path failed
     */
    @Override
    public String getAbsolutePath(String relativePath, RequestContext ctx) {
        String absolutePath = fileUtil.getAbsolutePathForTenant(ctx.tenantId, relativePath);
        Map<String, Object> patternReplacements = new HashMap<>();
        patternReplacements.put("tenantId", ctx.tenantId);
        return SimplePatternEvaluator.evaluate(absolutePath, patternReplacements);
    }

    @Override
    public boolean hasMore(InputStream is) throws IOException {
        return is.available() > 0;
    }
}
