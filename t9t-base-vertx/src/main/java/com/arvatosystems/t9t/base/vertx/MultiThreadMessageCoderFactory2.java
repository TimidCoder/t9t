package com.arvatosystems.t9t.base.vertx;

import de.jpaw.bonaparte.api.codecs.IMessageCoderFactory;
import de.jpaw.bonaparte.api.codecs.IMessageDecoder;
import de.jpaw.bonaparte.api.codecs.IMessageEncoder;
import de.jpaw.bonaparte.api.codecs.impl.BonaparteRecordDecoder;
import de.jpaw.bonaparte.api.codecs.impl.BonaparteRecordEncoder;
import de.jpaw.bonaparte.api.codecs.impl.CompactRecordDecoder;
import de.jpaw.bonaparte.api.codecs.impl.CompactRecordEncoder;
import de.jpaw.bonaparte.api.codecs.impl.JsonDecoder;
import de.jpaw.bonaparte.api.codecs.impl.JsonEncoder;
import de.jpaw.bonaparte.core.BonaPortable;
import de.jpaw.bonaparte.core.MimeTypes;

// the factory becomes multithreaded by just avoiding the caching.
public class MultiThreadMessageCoderFactory2<D extends BonaPortable, E extends BonaPortable> implements IMessageCoderFactory<D, E, byte []> {

    private final Class<D> decoderClass;

    public MultiThreadMessageCoderFactory2(Class<D> decoderClass, Class<E> encoderClass) {
        this.decoderClass = decoderClass;
    }

    // override to add additional methods
    protected IMessageEncoder<E, byte []> createNewEncoderInstance(String mimeType) {
        if (mimeType.equals(MimeTypes.MIME_TYPE_BONAPARTE))
            return new BonaparteRecordEncoder<E>();
        if (mimeType.equals(MimeTypes.MIME_TYPE_COMPACT_BONAPARTE))
            return new CompactRecordEncoder<E>();
        if (mimeType.equals(MimeTypes.MIME_TYPE_JSON))
            return new JsonEncoder<E>();
        return null;
    }

    // override to add additional methods
    protected IMessageDecoder<D, byte []> createNewDecoderInstance(String mimeType) {
        if (mimeType.equals(MimeTypes.MIME_TYPE_BONAPARTE))
            return new BonaparteRecordDecoder<D>();
        if (mimeType.equals(MimeTypes.MIME_TYPE_COMPACT_BONAPARTE))
            return new CompactRecordDecoder<D>();
        if (mimeType.equals(MimeTypes.MIME_TYPE_JSON))
            return new JsonDecoder<D>(decoderClass);
        return null;
    }

    @Override
    public final IMessageEncoder<E, byte []> getEncoderInstance(String mimeType) {
        return createNewEncoderInstance(mimeType);
    }

    @Override
    public IMessageDecoder<D, byte []> getDecoderInstance(String mimeType) {
        return createNewDecoderInstance(mimeType);
    }
}
