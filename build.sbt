name := "spark-101"

version := "0.1"

scalaVersion := "2.12.10"

val sparkVersion = "3.0.2"
val GcpBqVersion = "1.80.0"
val GcpDpVersion = "4.29.0"
val GcpGcsVersion = "1.108.0"
val GcpPubSubVersion = "1.108.1"
val cassandraConnectorVersion = "3.0.0"
val postgresVersion = "42.2.2"
val kafkaVersion = "2.4.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion
)

libraryDependencies ++= Seq(
  "com.google.cloud" % "google-cloud-bigquery" % GcpBqVersion,
  "com.google.cloud" % "google-cloud-dataproc" % GcpDpVersion,
  "com.google.cloud" % "google-cloud-storage" % GcpGcsVersion,
  "com.google.cloud" % "google-cloud-pubsub" % GcpPubSubVersion
)

libraryDependencies ++= Seq(
  "com.datastax.spark" %% "spark-cassandra-connector" % cassandraConnectorVersion,
  "org.postgresql" % "postgresql" % postgresVersion,
)

libraryDependencies ++= Seq(
  "org.apache.kafka" %% "kafka" % kafkaVersion,
  "org.apache.kafka" % "kafka-streams" % kafkaVersion,
  "org.apache.spark" % "spark-sql-kafka-0-10_2.12" % sparkVersion
)