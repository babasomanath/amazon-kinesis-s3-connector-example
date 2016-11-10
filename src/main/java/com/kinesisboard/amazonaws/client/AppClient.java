/**
 * This is meant for the Client execution 
 */
package com.kinesisboard.amazonaws.client;

import com.amazonaws.services.kinesis.connectors.KinesisConnectorRecordProcessorFactory;
import com.kinesisboard.amazonaws.model.StockTrade;
import com.kinesisboard.amazonaws.s3.KinesisS3Pipeline;

/**
 * @author somanath
 *
 */
public class AppClient extends KinesisConnectorExecutor<StockTrade, byte[]> {
	private static final String CONFIG_FILE = "config.properties";

    /**
     * Creates a new S3Executor.
     * 
     * @param configFile
     *        The name of the configuration file to look for on the classpath
     */
    public AppClient(String configFile) {
        super(configFile);
    }

    @Override
    public KinesisConnectorRecordProcessorFactory<StockTrade, byte[]>
            getKinesisConnectorRecordProcessorFactory() {
        return new KinesisConnectorRecordProcessorFactory<StockTrade, byte[]>(new KinesisS3Pipeline(),this.config);
    }
	/**
	 * Main method to run the S3Executor.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		KinesisConnectorExecutor<StockTrade, byte[]> s3Executor = new AppClient(CONFIG_FILE);
		s3Executor.run();
	}

}
