/**
 * 
 */
package com.kinesisboard.amazonaws.connectors.impl;

import com.amazonaws.services.kinesis.connectors.interfaces.IFilter;

/**
 * @author somanath
 *
 */
public class MyFilter<StockTrade> implements IFilter<StockTrade> {

	@Override
	public boolean keepRecord(StockTrade record) {
		System.out.println("I am here .. .. "+record);
		return false;
	}
}
