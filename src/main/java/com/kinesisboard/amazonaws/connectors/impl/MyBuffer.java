/**
 * 
 */
package com.kinesisboard.amazonaws.connectors.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.amazonaws.services.kinesis.connectors.KinesisConnectorConfiguration;
import com.amazonaws.services.kinesis.connectors.interfaces.IBuffer;

/**
 * @author somanatn
 *
 */
public class MyBuffer<T> implements IBuffer<T> {

    private final long bytesPerFlush;
    private final long numMessagesToBuffer;
    private final long millisecondsToBuffer;

    private final List<T> buffer;
    private final AtomicLong byteCount;

    private String firstSequenceNumber;
    private String lastSequenceNumber;

    private long previousFlushTimeMillisecond;

    public MyBuffer(KinesisConnectorConfiguration configuration, List<T> buffer) {
        bytesPerFlush = configuration.BUFFER_BYTE_SIZE_LIMIT;
        numMessagesToBuffer = configuration.BUFFER_RECORD_COUNT_LIMIT;
        millisecondsToBuffer = configuration.BUFFER_MILLISECONDS_LIMIT;
        this.buffer = buffer;
        byteCount = new AtomicLong();
        previousFlushTimeMillisecond = getCurrentTimeMilliseconds();
        System.out.println("MyBuffer : Buffer Constructor... ");
    }

    public MyBuffer(KinesisConnectorConfiguration configuration) {
        this(configuration, new LinkedList<T>());
    }

    @Override
    public long getBytesToBuffer() {
        return bytesPerFlush;
    }

    @Override
    public long getNumRecordsToBuffer() {
        return numMessagesToBuffer;
    }

    @Override
    public long getMillisecondsToBuffer() {
        return millisecondsToBuffer;
    }

    @Override
    public void consumeRecord(T record, int recordSize, String sequenceNumber) {
        if (buffer.isEmpty()) {
            firstSequenceNumber = sequenceNumber;
        }
        lastSequenceNumber = sequenceNumber;
        buffer.add(record);
        byteCount.addAndGet(recordSize);
    }

    @Override
    public void clear() {
        buffer.clear();
        byteCount.set(0);
        previousFlushTimeMillisecond = getCurrentTimeMilliseconds();
    }

    @Override
    public String getFirstSequenceNumber() {
        return firstSequenceNumber;
    }

    @Override
    public String getLastSequenceNumber() {
        return lastSequenceNumber;
    }

    /**
     * By default, we flush once we have exceeded the number of messages or maximum bytes to buffer.
     * However, subclasses can use their own means to determine if they should flush.
     * 
     * @return true if either the number of records in the buffer exceeds max number of records or
     *         the size of the buffer exceeds the max number of bytes in the buffer.
     */
    @Override
    public boolean shouldFlush() {
        long timelapseMillisecond = getCurrentTimeMilliseconds() - previousFlushTimeMillisecond;
        return (!buffer.isEmpty())
                && ((buffer.size() >= getNumRecordsToBuffer()) || (byteCount.get() >= getBytesToBuffer()) || (timelapseMillisecond >= getMillisecondsToBuffer()));
    }

    @Override
    public List<T> getRecords() {
        return buffer;
    }

    // This method has protected access for unit testing purposes.
    protected long getCurrentTimeMilliseconds() {
        return System.currentTimeMillis();
    }

}
