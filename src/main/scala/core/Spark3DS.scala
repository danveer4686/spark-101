package core

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

    case class Stocks(id: Long, name: String, guitars: Seq[Long], band: Long)
    import org.apache.spark.sql._
    val mapping = Encoders.product[Stocks]

    val guitarPlayers = spark
      .read.format("csv")
      .schema(mapping.schema)
      .option("header",true)
      .option("delimiter",",")
      .load("src/main/resources/data/stocks/aapl.csv")
      .as[Stocks](mapping)

    guitarPlayers.show()

  }

}
