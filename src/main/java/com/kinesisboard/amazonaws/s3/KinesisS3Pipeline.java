/**
 * This class is to define the pipeline for step by step execution
 */
package com.kinesisboard.amazonaws.s3;

import com.amazonaws.services.kinesis.connectors.KinesisConnectorConfiguration;
import com.amazonaws.services.kinesis.connectors.interfaces.IBuffer;
import com.amazonaws.services.kinesis.connectors.interfaces.IEmitter;
import com.amazonaws.services.kinesis.connectors.interfaces.IFilter;
import com.amazonaws.services.kinesis.connectors.interfaces.IKinesisConnectorPipeline;
import com.amazonaws.services.kinesis.connectors.interfaces.ITransformerBase;
import com.kinesisboard.amazonaws.connectors.impl.MyBuffer;
import com.kinesisboard.amazonaws.connectors.impl.MyEmitter;
import com.kinesisboard.amazonaws.connectors.impl.MyFilter;
import com.kinesisboard.amazonaws.connectors.impl.MyTransformer;
import com.kinesisboard.amazonaws.model.StockTrade;

/**
 * @author somanath
 *
 */
public class KinesisS3Pipeline implements IKinesisConnectorPipeline<StockTrade, byte[]> {

	@Override
	public IBuffer<StockTrade> getBuffer(KinesisConnectorConfiguration configuration) {
		return new MyBuffer<StockTrade>(configuration);
	}

	@Override
	public IEmitter<byte[]> getEmitter(KinesisConnectorConfiguration configuration) {
		return new MyEmitter(configuration);
	}

	@Override
	public IFilter<StockTrade> getFilter(KinesisConnectorConfiguration configuration) {
		return new MyFilter<StockTrade>();
	}

	@Override
	public ITransformerBase<StockTrade, byte[]> getTransformer(
			KinesisConnectorConfiguration configuration) {
		return new MyTransformer<StockTrade>(StockTrade.class);
	}

}
