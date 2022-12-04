package streaming

import common.Schema.stocksSchema
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, length}
import org.apache.spark.sql.streaming.Trigger
import service.SparkSessionManager._

import scala.concurrent.duration.DurationInt


object SparkStream1 {

  /**
   * Lazy evaluation
   * Input sources: Kafka flume filesystem sockets
   * Output sources: File kafka databases console
   * Output modes: Append update complete // append and update not supported on aggregations without watermark
   * Trigger :
   * Default write as soon as current micro batch is completed
   * Once: write a single micro batch and stop
   * processing-time: look for new data at fixed intervals
   * continuous
   *
   * df.isStreaming will tell whether streaming or not

   */

  def readFromSocket() = {
    // reading a DF
    val lines: DataFrame = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 12345)
      .load()

    // transformation
    val shortLines: DataFrame = lines.filter(length(col("value")) <= 5)

    // tell between a static vs a streaming DF
    println(shortLines.isStreaming)

    // consuming a DF
    val query = shortLines.writeStream
      .format("console")
      .outputMode("append")
      .start()

    // wait for the stream to finish
    query.awaitTermination()
  }

  def readFromFiles() = {
    val stocksDF: DataFrame = spark.readStream
      .format("csv")
      .option("header", "false")
      .option("dateFormat", "MMM d yyyy")
      .schema(stocksSchema)
      .load("src/main/resources/data/stocks")

    stocksDF.writeStream
      .format("console")
      .outputMode("append")
      .start()
      .awaitTermination()
  }

  def demoTriggers() = {
    val lines: DataFrame = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 12345)
      .load()

    // write the lines DF at a certain trigger
    lines.writeStream
      .format("console")
      .outputMode("append")
      .trigger( // if trigger is not specified then by default spark processes records as soon as they arrive
        // Trigger.ProcessingTime(2.seconds) // every 2 seconds one batch is run
        // Trigger.Once() // single batch, then terminate (in cloud environment to save resources, we can start the source once and process it once and shutdown the source to save some cloud billing)
        Trigger.Continuous(2.seconds) // It creates ever-running tasks which process data as soon as it arrives but after every 2 seconds it writes records in checkpointing directory to recover failure.
      )
      .start()
      .awaitTermination()
  }




}
