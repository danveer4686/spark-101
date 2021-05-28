name := "spark-101"

version := "0.1"

scalaVersion := "2.12.10"

val sparkVersion = "3.0.2"
val GcpBqVersion = "1.80.0"
val GcpDpVersion = "1.1.2"
val GcpGcsVersion = "1.108.0"
val GcpPubSubVersion = "1.108.1"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion
)

libraryDependencies ++= Seq(
  "com.google.cloud" % "google-cloud-bigquery" % GcpBqVersion,
  "com.google.cloud" % "google-cloud-dataproc" % GcpDpVersion,
  "com.google.cloud" % "google-cloud-storage" % GcpGcsVersion,
  "com.google.cloud" % "google-cloud-pubsub" % GcpPubSubVersion
)