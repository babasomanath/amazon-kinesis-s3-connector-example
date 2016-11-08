/**
 * 
 */
package com.kinesisboard.amazonaws.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author somanath
 *
 */
public class TimeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Long nanoTime = System.nanoTime();
		List<Long> longList = new ArrayList<Long>();
		longList.add(nanoTime);
		System.out.println("Nano Time :  "+nanoTime);
		nanoTime = System.nanoTime();
		longList.add(nanoTime);
		System.out.println("Nano Time :  "+nanoTime);
		nanoTime = System.nanoTime();
		longList.add(nanoTime);
		System.out.println("Nano Time :  "+nanoTime);
		Collections.sort(longList);
		System.out.println(longList);
	}

}
