# Objective :
  Implement a KCL application which writes to S3 and orders and de-duplicates records over windows. Then, this logic should be implemented on an S3 app.
   
# This is the Amazon Kinesis Connector Sample for S3

To run this code:

1) Perform a git clone:
 git clone https://github.com/babasomanath/amazon-kinesis-s3-connector-example/

2) Run the code:
```
cd amazon-kinesis-s3-connector-example
mvn assembly:assembly
java -cp target/amazon-kinesis-s3-connector-example-1.0.0-complete.jar com.kinesisboard.amazonaws.client.AppClient
```



# Amazon Kinesis Connector Library

This is a pre-built library that helps you easily integrate Amazon Kinesis Streams with other AWS services and third-party tools. Amazon Kinesis Client Library (KCL) is required for using this library. The current version of this library provides connectors to Amazon DynamoDB, Amazon Redshift, Amazon S3, and Elasticsearch. The library also includes sample connectors of each type, plus Apache Ant build files for running the samples. https://github.com/awslabs/amazon-kinesis-connectors

As per the above documentation, A connector pipeline uses the following interfaces:

+ **IKinesisConnectorPipeline**: The pipeline implementation itself.
+ **ITransformer**: Defines the transformation of records from the Amazon Kinesis stream in order to suit the user-defined data model. Includes methods for custom serializer/deserializers.
+ **IFilter**: IFilter defines a method for excluding irrelevant records from the processing.
+ **IBuffer**: IBuffer defines a system for batching the set of records to be processed. The application can specify three thresholds: number of records, total byte count, and time. When one of these thresholds is crossed, the buffer is flushed and the data is emitted to the destination.
+ **IEmitter**: Defines a method that makes client calls to other AWS services and persists the records stored in the buffer. The records can also be sent to another Amazon Kinesis stream.


I am following these guidelines to develop the application.

# Design Considerations :

1) Receive the Records in list, aggregate them after the batch size is reached, Convert the aggregated list into Set. 
2) Then convert it back to a List.
3) StockTrade.java  -->  equals() method is overridden to avoid duplicates.  
4) StockTrade.java  -->  compareTo() method is to be overridden to manage the ordering using Collections.sort() on the Aggregated List.
