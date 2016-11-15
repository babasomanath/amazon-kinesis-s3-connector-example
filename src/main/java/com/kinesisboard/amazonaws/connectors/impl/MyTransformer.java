/**
 * 
 */
package com.kinesisboard.amazonaws.connectors.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.services.kinesis.connectors.BasicJsonTransformer;
import com.amazonaws.services.kinesis.connectors.impl.JsonToByteArrayTransformer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author somanatn
 *
 */
public class MyTransformer<T> extends BasicJsonTransformer<T, byte[]> {
    private static final Log LOG = LogFactory.getLog(MyTransformer.class);

    public MyTransformer(Class<T> inputClass) {
        super(inputClass);
    }

    @Override
    public byte[] fromClass(T record) throws IOException {
    	System.out.println("MyTransformer : Record received : "+record);
        try {
            return new ObjectMapper().writeValueAsString(record).getBytes();
        } catch (JsonProcessingException e) {
            String message = "Error parsing record to JSON";
            LOG.error(message, e);
            throw new IOException(message, e);
        }

    }

}
