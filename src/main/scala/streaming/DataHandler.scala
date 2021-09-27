package streaming

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.streaming._
import org.apache.spark.sql.types._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import scala.reflect.io.File

/**
 *
 * @author Danveer
 *
 *
 * Steps: (on mac cd /usr/local/opt/kafka  and properties file would be in /usr/local/etc/kafka)
 * bin/zookeeper-server-start.sh config/zookeeper.properties   (starting zookeeper)
   bin/kafka-server-start.sh config/server.properties    (starting broker server)
   bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic sampleTopic     (creating topic with name as "sampleTopic")
   bin/kafka-console-producer.sh  --broker-list localhost:9092  --topic sampleTopic   (starting console producer)
   bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic sampleTopic --from-beginning   (starting console consumer)
 */

object DataHandler {
  def main(args: Array[String]) {
    val customSchema = StructType(Array(
      StructField("col1", StringType, true),
      StructField("col2", StringType, true),
      StructField("col3", StringType, true),
      StructField("col4", StringType, true)))

    val sparkSess = SparkSession.builder().appName("kafkaHandler").getOrCreate()
    val sc = sparkSess.sparkContext
    val ssc = new StreamingContext(sc, Seconds(1))

    val streamDf = sparkSess.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers","localhost:9092")
      .option("subscribe","sampleTopic")
      .load()

    val kvDF = streamDf
      .select(col("key").cast("string"),from_json(col("value").cast("string"), customSchema))


    //========= aggregating input dataframe into minute timeframe ===========//


    kvDF.createOrReplaceTempView("kvDF")

    //======== converting 1 minute timeframe from given time column and aggregating data on time ===========//

    val aggdfperminute =  sparkSess.sql("select * from kvDF")

    //======== storing data to hdfs location in csv format ========= //
    aggdfperminute.write.format("csv").option("header","true").save("/home/danveer/workspace/myApp1/target/resultData.txt")
  }

}
