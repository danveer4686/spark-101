package core

import common.Schema.Stocks
import org.apache.spark.sql.SparkSession

object Spark3DS {

  // the entry point to the Spark structured API
  val spark = SparkSession.builder()
    .appName("Spark Recap")
    .master("local[2]")
    .getOrCreate()

  def main(args: Array[String]): Unit = {
    readingDataSet
  }

  def readingDataSet():Unit ={

    import org.apache.spark.sql._
    val mapping = Encoders.product[Stocks]

    val stocksDS = spark
      .read.format("csv")
      .schema(mapping.schema)
      .option("header",false)
      .option("delimiter",",")
      .load("src/main/resources/data/stocks/aapl.csv")
      .as[Stocks](mapping)

    stocksDS.show()

  }

}
