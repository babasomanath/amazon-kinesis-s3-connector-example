package com.kinesisboard.amazonaws.connectors.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.services.kinesis.connectors.KinesisConnectorConfiguration;
import com.amazonaws.services.kinesis.connectors.UnmodifiableBuffer;
import com.amazonaws.services.kinesis.connectors.interfaces.IEmitter;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

/**
 * @author somanath
 *
 */

public class MyEmitter implements IEmitter<byte[]> {
    private static final Log LOG = LogFactory.getLog(MyEmitter.class);
    protected final String s3Bucket;
    protected final String s3Endpoint;

    protected final AmazonS3Client s3client;

    public MyEmitter(KinesisConnectorConfiguration configuration) {
        s3Bucket = configuration.S3_BUCKET;
        s3Endpoint = configuration.S3_ENDPOINT;
        s3client = new AmazonS3Client(configuration.AWS_CREDENTIALS_PROVIDER);
        if (s3Endpoint != null) {
            s3client.setEndpoint(s3Endpoint);
        }
    }

    protected String getS3FileName(String firstSeq, String lastSeq) {
        return firstSeq + "-" + lastSeq;
    }

    protected String getS3URI(String s3FileName) {
        return "s3://" + s3Bucket + "/" + s3FileName;
    }

    @Override
    public List<byte[]> emit(final UnmodifiableBuffer<byte[]> buffer) throws IOException {
        List<byte[]> records = buffer.getRecords();
        // Write all of the records to a compressed output stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (byte[] record : records) {
            try {
                baos.write(record);
            } catch (Exception e) {
                LOG.error("Error writing record to output stream. Failing this emit attempt. Record: "
                        + Arrays.toString(record),
                        e);
                return buffer.getRecords();
            }
        }
        // Get the Amazon S3 filename
        String s3FileName = getS3FileName(buffer.getFirstSequenceNumber(), buffer.getLastSequenceNumber());
        String s3URI = getS3URI(s3FileName);
        try {
            ByteArrayInputStream object = new ByteArrayInputStream(baos.toByteArray());
            LOG.debug("Starting upload of file " + s3URI + " to Amazon S3 containing " + records.size() + " records.");
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentLength(baos.size());
            s3client.putObject(s3Bucket, s3FileName, object, meta);
            LOG.info("Successfully emitted " + buffer.getRecords().size() + " records to Amazon S3 in " + s3URI);
            return Collections.emptyList();
        } catch (Exception e) {
            LOG.error("Caught exception when uploading file " + s3URI + "to Amazon S3. Failing this emit attempt.", e);
            return buffer.getRecords();
        }
    }

    @Override
    public void fail(List<byte[]> records) {
        for (byte[] record : records) {
            LOG.error("Record failed: " + Arrays.toString(record));
        }
    }

    @Override
    public void shutdown() {
        s3client.shutdown();
    }

}
