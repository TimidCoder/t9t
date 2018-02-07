package com.arvatosystems.t9t.base.be.aws.events.tests;

import org.junit.Ignore;
import org.junit.Test;

import com.arvatosystems.t9t.base.services.IEventImpl;
import com.arvatosystems.t9t.jdp.Init;

import de.jpaw.bonaparte.api.media.MediaTypeInfo;
import de.jpaw.bonaparte.api.media.MediaTypes;
import de.jpaw.bonaparte.pojos.api.media.MediaData;
import de.jpaw.dp.Jdp;

public class AwsTest {

    // mock caches on startup
    public static void initAndMockCaches() {
        Jdp.reset();
        Init.initializeT9t();
//      Jdp.bindClassToQualifier(NoTenantCache.class, ICacheTenant.class, null);
//      Jdp.bindClassToQualifier(NoUserCache.class, ICacheUser.class, null);
    }

    private MediaData createJson() {
        return new MediaData(MediaTypes.MEDIA_XTYPE_JSON, "{ \"hello\": 3.14 }", null, null);
    }

    @Test
    @Ignore
    public void testS3Put() throws Exception {
        initAndMockCaches();
        IEventImpl s3Impl = Jdp.getRequired(IEventImpl.class, "S3");

        s3Impl.asyncEvent("t9t-test:samples/test2.json", createJson(), MediaTypeInfo.getFormatByType(MediaTypes.MEDIA_XTYPE_JSON));
    }

    @Ignore
    @Test
    public void testSQSSend() throws Exception {
        initAndMockCaches();
        IEventImpl sqsImpl = Jdp.getRequired(IEventImpl.class, "SQS");

        sqsImpl.asyncEvent("t9t-test", createJson(), MediaTypeInfo.getFormatByType(MediaTypes.MEDIA_XTYPE_JSON));
    }
}
