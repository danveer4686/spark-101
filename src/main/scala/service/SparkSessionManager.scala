package service

import org.apache.spark.sql.SparkSession

object SparkSessionManager {

  val spark = SparkSession.builder()
    .appName("SparkApp")
    .master("local[2]")
    .getOrCreate()

  val sc = spark.sparkContext

  spark.sparkContext.setLogLevel("WARN")


}
