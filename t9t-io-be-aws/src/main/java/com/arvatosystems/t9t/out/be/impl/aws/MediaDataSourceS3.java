package com.arvatosystems.t9t.out.be.impl.aws;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3Object;
import com.arvatosystems.t9t.base.T9tException;
import com.arvatosystems.t9t.io.services.IMediaDataSource;

import de.jpaw.dp.Named;
import de.jpaw.dp.Singleton;

// file download handler implementation for S3 buckets
@Singleton
@Named("S3")
public class MediaDataSourceS3 implements IMediaDataSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaDataSourceS3.class);
    private static final char DELIMITER = ':';
    protected final AmazonS3Client s3Client = new AmazonS3Client();

    @Override
    public InputStream open(String targetName) throws Exception {
        final int ind = targetName.indexOf(DELIMITER);
        if (ind < 1 || ind == targetName.length()-1) {
            LOGGER.error("file pattern not good, expected (something):(something else), got {}", targetName);
            throw new T9tException(T9tException.BAD_S3_BUCKET_NAME, targetName);
        }

        // determine the target
        final String bucket = targetName.substring(0, ind).trim();
        final String path = targetName.substring(ind+1).trim();

         S3Object o = s3Client.getObject(bucket, path);
         return o.getObjectContent();
    }
}
