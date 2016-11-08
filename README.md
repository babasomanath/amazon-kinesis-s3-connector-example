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


```


# Amazon Kinesis Connector Library

This is a pre-built library that helps you easily integrate Amazon Kinesis Streams with other AWS services and third-party tools. Amazon Kinesis Client Library (KCL) is required for using this library. The current version of this library provides connectors to Amazon DynamoDB, Amazon Redshift, Amazon S3, and Elasticsearch. The library also includes sample connectors of each type, plus Apache Ant build files for running the samples. https://github.com/awslabs/amazon-kinesis-connectors

``` 