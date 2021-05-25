package service

import org.apache.spark.sql.SparkSession

object SparkSessionManager {

  val spark = SparkSession.builder()
    .appName("SparkApp")
    .master("local[2]")
    .getOrCreate()

  val sc = spark.sparkContext

  spark.sparkContext.setLogLevel("WARN")

  val spark_for_gcp = SparkSession.builder()
        .appName("SparkApp")
        .master("local[2]")
        .config("fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem")
        .config("fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS")
        .config("fs.gs.project.id", "my-gcp-project")
        .config("fs.gs.auth.service.account.enable", "true")
        .config("google.cloud.auth.service.account.json.keyfile", "path_to_google_service_json_file")
        .config("credentialsFile", "path_to_google_service_json_file")
        .getOrCreate()

  val spark_for_aws = SparkSession.builder()
        .appName("SparkApp")
        .master("local[2]")
        .config("spark.hadoop.fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
        .config("spark.hadoop.fs.s3a.access.key", "path_to_access_key_file")
        .config("spark.hadoop.fs.s3a.secret.key", "path_to_secret_key_file")
        .getOrCreate()

}
