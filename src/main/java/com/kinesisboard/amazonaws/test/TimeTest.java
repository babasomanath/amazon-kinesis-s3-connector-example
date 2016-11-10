/**
 * 
 */
package com.kinesisboard.amazonaws.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.kinesisboard.amazonaws.model.StockTrade;
import com.kinesisboard.amazonaws.model.StockTrade.TradeType;

/**
 * @author somanath
 *
 */
public class TimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Long timeInMillis = System.currentTimeMillis();
		List<Long> longList = new ArrayList<Long>();
		longList.add(timeInMillis);
		System.out.println("Time in Millis :  "+timeInMillis);
		timeInMillis = System.currentTimeMillis();
		longList.add(timeInMillis);
		System.out.println("Time in Millis :  "+timeInMillis);
		timeInMillis = System.currentTimeMillis();
		longList.add(timeInMillis);
		System.out.println("Time in Millis :  "+timeInMillis);
		Collections.sort(longList);
		System.out.println(longList);
		StockTrade st = new StockTrade("a",TradeType.BUY,2.30,123L,1L);
		Date date = new Date(timeInMillis);
		System.out.println(date);
		System.out.println(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss,SSS").format(st.getTimeInMillis()));
		System.out.println(st);
		System.out.println(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss,SSS").format(timeInMillis));
	}

}
