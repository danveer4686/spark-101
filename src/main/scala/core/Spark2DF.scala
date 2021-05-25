package core

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object Spark2DF {

  // the entry point to the Spark structured API
  val spark = SparkSession.builder()
    .appName("SparkApp")
    .master("local[2]")
    .getOrCreate()

  // read a DF
  val cars = spark.read
    .format("json")
    .option("inferSchema", "true")
    .load("src/main/resources/data/cars")

  import spark.implicits._

  def main(args: Array[String]): Unit = {
    // showing a DF to the console
    cars.show()
    cars.printSchema()

    sparkSql()
    filter_agg_grouping()
    joiningDF()

  }


  def sparkSql()={
    cars.createOrReplaceTempView("cars")
    val americanCars = spark.sql(
      """
        |select Name from cars where Origin = 'USA'
    """.stripMargin
    )
  }

  def filter_agg_grouping():Unit={
    val usefulCarsData = cars.select(
      col("Name"), // column object
      $"Year", // another column object (needs spark implicits)
      (col("Weight_in_lbs") / 2.2).as("Weight_in_kg"),
      expr("Weight_in_lbs / 2.2").as("Weight_in_kg_2")
    )

    val carsWeights = cars.selectExpr("Weight_in_lbs / 2.2")

    // filter
    val europeanCars = cars.where(col("Origin") =!= "USA")

    // aggregations
    val averageHP = cars.select(avg(col("Horsepower")).as("average_hp")) // sum, meam, stddev, min, max

    // grouping
    val countByOrigin = cars
      .groupBy(col("Origin")) // a RelationalGroupedDataset
      .count()
  }

  def joiningDF()={
    /*
      join types
      - inner: only the matching rows are kept
      - left/right/full outer join
      - semi/anti
     */
    // joining
    val guitarPlayers = spark.read
      .option("inferSchema", "true")
      .json("src/main/resources/data/guitarPlayers")

    val bands = spark.read
      .option("inferSchema", "true")
      .json("src/main/resources/data/bands")

    val guitaristsBands = guitarPlayers.join(bands, guitarPlayers.col("band") === bands.col("id"))
    guitaristsBands.show()
  }

}
