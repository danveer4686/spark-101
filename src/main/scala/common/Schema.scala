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

  val libs: List[String] = List(
    "gs://star-dl-artifacts/jars/dev/etljobs-core-assembly_2.12-0.7.19.jar"
    ,"gs://star-dl-artifacts/jars/dev/loaddata_new.properties"
    ,"gs://star-dl-artifacts/jars/lib_new/spark-bigquery-with-dependencies_2.12-0.19.1.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/ojdbc6-11.2.0.2.0.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/google-cloud-dataproc-1.0.0.jar"
    ,"gs://star-dl-artifacts/jars/dev/postgresql-42.2.20.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/javax.mail-1.6.2.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/spark-excel_2.12-0.13.6.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/commons-collections4-4.4.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/commons-codec-1.13.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/commons-compress-1.20.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/commons-lang3-3.9.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/commons-math3-3.6.1.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/commons-text-1.8.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/curvesapi-1.06.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/excel-streaming-reader-2.3.5.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/h2-1.4.200.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/poi-4.1.2.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/poi-ooxml-4.1.2.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/poi-ooxml-schemas-4.1.2.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/poi-shared-strings-1.0.4.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/scala-library-2.12.10.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/scala-xml_2.12-1.3.0.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/slf4j-api-1.7.30.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/SparseBitSet-1.2.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/spoiwo_2.12-1.8.0.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/xml-apis-1.4.01.jar"
    ,"gs://star-dl-artifacts/jars/lib_new/xmlbeans-3.1.0.jar"
    ,"gs://star-dl-artifacts/jars/dev/delta-core_2.12-0.6.1.jar"
    ,"gs://star-dl-artifacts/jars/dev/clickhouse-jdbc-0.4.6-shaded.jar"
  )

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
