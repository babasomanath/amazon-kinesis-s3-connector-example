/**
 * 
 */
package com.kinesisboard.amazonaws.test;
 
import java.nio.charset.Charset;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.GetRecordsRequest;
import com.amazonaws.services.kinesis.model.GetRecordsResult;
import com.amazonaws.services.kinesis.model.GetShardIteratorRequest;
import com.amazonaws.services.kinesis.model.GetShardIteratorResult;
import com.amazonaws.services.kinesis.model.Record;
import com.amazonaws.services.kinesis.model.Shard;
 
/**
 * @author somanatn
 *
 */
public class KinesisConsumer {
 
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		String myStreamName = "TestBaba";
		AmazonKinesisClient client = new AmazonKinesisClient().withEndpoint("https://kinesis.eu-west-1.amazonaws.com").
				withRegion(Regions.EU_WEST_1);
		String shardIterator;
		DescribeStreamResult describeStream = client.describeStream(myStreamName);
		Shard shard = describeStream.getStreamDescription().getShards().get(0);
		System.out.println("shard -----  "+shard);
		GetShardIteratorRequest getShardIteratorRequest = new GetShardIteratorRequest();
		getShardIteratorRequest.setStreamName(myStreamName);
		getShardIteratorRequest.setShardId(shard.getShardId());
		getShardIteratorRequest.setShardIteratorType("LATEST");
 
		GetShardIteratorResult getShardIteratorResult = client.getShardIterator(getShardIteratorRequest);
		System.out.println(getShardIteratorResult);
		shardIterator = getShardIteratorResult.getShardIterator();
		List<Record> records;
		while (true) {
			   
			  // Create a new getRecordsRequest with an existing shardIterator 
			  // Set the maximum records to return to 10
			  GetRecordsRequest getRecordsRequest = new GetRecordsRequest();
			  getRecordsRequest.setShardIterator(shardIterator);
			  getRecordsRequest.setLimit(10); 
 
			  GetRecordsResult result = client.getRecords(getRecordsRequest);
			 // System.out.println("result ====    "+result);
			  // Put the result into record list. The result can be empty.
			  records = result.getRecords();
			  if(records.size()>0) {
				  System.out.println("  RECORDS ==>  "+new String(records.get(0).getData().array(),Charset.forName("UTF-8")));
			  }
			  else
				  System.out.println("******* RECORDS EMPTY ******* ");
			  try {
			    Thread.sleep(300000); // Explicit expiry of the Shard Iterator
			  } 
			  catch (InterruptedException exception) {
			    throw new RuntimeException(exception);
			  }catch(Exception ex){
				  System.out.println(ex.getMessage());
			  }
			  
			  shardIterator = result.getNextShardIterator();
			  //System.out.println("Next Shard Iterator = === =    "+shardIterator);
			  Thread.sleep(1);
			}
	}
 
}