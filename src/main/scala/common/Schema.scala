package common

import org.apache.spark.sql.types.{DateType, DoubleType, IntegerType, LongType, StringType, StructField, StructType, TimestampType}

object Schema {

  case class CC(number:Int)
  case class Stocks(name: String, date: String, price: Double)

  val onlinePurchaseSchema = StructType(Array(
    StructField("id", StringType),
    StructField("time", TimestampType),
    StructField("item", StringType),
    StructField("quantity", IntegerType)
  ))

  case class Car(
                  Name: String,
                  Miles_per_Gallon: Option[Double],
                  Cylinders: Option[Long],
                  Displacement: Option[Double],
                  Horsepower: Option[Long],
                  Weight_in_lbs: Option[Long],
                  Acceleration: Option[Double],
                  Year: String,
                  Origin: String
                )

  val carsSchema = StructType(Array(
    StructField("Name", StringType),
    StructField("Miles_per_Gallon", DoubleType),
    StructField("Cylinders", LongType),
    StructField("Displacement", DoubleType),
    StructField("Horsepower", LongType),
    StructField("Weight_in_lbs", LongType),
    StructField("Acceleration", DoubleType),
    StructField("Year", StringType),
    StructField("Origin", StringType)
  ))

  case class DataprocProperties (
                                  project: String = "",
                                  region: String = "",
                                  endpoint: String = "",
                                  cluster_name: String = "",
                                  bucket_name: String = "",
                                  subnet_uri: Option[String] = None,
                                  network_tags: List[String] = List.empty,
                                  service_account: Option[String] = None,
                                  idle_deletion_duration_sec: Option[Long] = Some(1800L),
                                  master_machine_type_uri: String = "n1-standard-4",
                                  worker_machine_type_uri: String = "n1-standard-4",
                                  image_version: String = "1.5.4-debian10",
                                  boot_disk_type: String = "pd-ssd",
                                  master_boot_disk_size_gb: Int = 400,
                                  worker_boot_disk_size_gb: Int = 200,
                                  master_num_instance: Int = 1,
                                  worker_num_instance: Int = 3
                                )

  val stocksSchema = StructType(Array(
    StructField("company", StringType),
    StructField("date", DateType),
    StructField("value", DoubleType)
  ))



}
