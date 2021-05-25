package core

import common.Schema.CC
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

object Spark1RDD {

  // the entry point to the Spark structured API
  val spark = SparkSession.builder()
    .appName("SparkApp")
    .master("local[2]")
    .getOrCreate()

  val sc = spark.sparkContext
  import spark.implicits._


  def main(args: Array[String]): Unit = {
    val numbersRDD: RDD[Int] = sc.parallelize(1 to 100)
    mapExample(numbersRDD)
    filterExample(numbersRDD)
    wordCountRDD()
    conversion(numbersRDD)
  }



  def mapExample(rdd:RDD[Int]):Unit={
    val doubles:RDD[Int] = rdd.map(_ * 2)
    doubles.collect.foreach(println)
  }

  def filterExample(rdd:RDD[Int]):Unit={
    val evens:RDD[Int] = rdd.filter(_%2==0)
    evens.collect.foreach(println)
  }

  def wordCountRDD():Unit={
    val stocksRDD   :RDD[String]        = sc.textFile("src/main/resources/data/stocks/aapl.csv")
    val words       :RDD[String]        = stocksRDD.flatMap(line => line.split(" "))
    val wordCount   :RDD[(String,Int)]  = words.map(a => (a,1)).reduceByKey(_+_)
    wordCount.collect.foreach(println)
  }

  def conversion(rdd:RDD[Int])={

    // RDD -> DF
    val numbersDF:DataFrame = rdd.toDF("number") // you lose type info, you get SQL capability
    numbersDF.show

    // RDD -> DS
    val numbersDS1:Dataset[CC] = numbersDF.as[CC]
    val numbersDS = spark.createDataset(rdd)
    numbersDS1.show()
    numbersDS.show

    // DS -> RDD
    val newRDD = numbersDS.rdd
    newRDD.collect.foreach(println)

    // DF -> RDD
    val carsRDD = numbersDF.rdd // RDD[Row]
  }


}
