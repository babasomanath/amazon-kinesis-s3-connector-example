/**
 * 
 */
package com.kinesisboard.amazonaws.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

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
	public static void main(String[] args) throws Exception{
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
		StockTrade st1 = new StockTrade("GE",TradeType.SELL,21.29,4072,62738);
		//Thread.sleep(1L);
		StockTrade st2 = new StockTrade("GE",TradeType.SELL,21.29,4072,62738);
		//Thread.sleep(1L);
		StockTrade st3 = new StockTrade("RE",TradeType.SELL,21.29,4074,62740);
		//Thread.sleep(1L);
		StockTrade st4 = new StockTrade("TE",TradeType.SELL,21.29,4075,62741);
		System.out.println(st1+" "+st1.hashCode());
		System.out.println(st2+" "+st2.hashCode());
		System.out.println(st3+" "+st3.hashCode());
		System.out.println(st4+" "+st4.hashCode());
		System.out.println(st1.equals(st2));
		Thread.sleep(100L);
		
		HashSet<StockTrade> myHashSet = new HashSet<StockTrade>();
		myHashSet.add(st3);
		System.out.println(myHashSet);
		myHashSet.add(st4);
		System.out.println(myHashSet);myHashSet.add(st1);myHashSet.add(st2);
		System.out.println(myHashSet);
		TreeSet<StockTrade> myTreeSet = new TreeSet<StockTrade>();
		myTreeSet.addAll(myHashSet);
		System.out.println(myTreeSet);
	}

}
