package cloud

import java.util.UUID
import com.google.cloud.bigquery.{ CsvOptions, FieldValueList, FormatOptions, Job, JobConfiguration, JobId, JobInfo, LoadJobConfiguration, QueryJobConfiguration, Schema, StandardTableDefinition, TableId, TableResult}
import scala.sys.process._
import JobInfo.WriteDisposition._
import JobInfo.CreateDisposition._
import service.BQSessionManager.getBQSession

object GcpBQ {

  def main(args:Array[String]):Unit={
    executeQueryInBQ("")
    getDataFromBQ("")
    loadDataIntoBQFromFile()
    //loadIntoBQTable("","","","","",WRITE_TRUNCATE,CREATE_IF_NEEDED,new Schema())

  }

  def executeQueryInBQ(query: String): Unit = {
    val queryConfig: QueryJobConfiguration = QueryJobConfiguration.newBuilder(query)
      .setUseLegacySql(false)
      .build()

    val jobId = JobId.of(UUID.randomUUID().toString)
    var queryJob = getBQSession("path_to_cred_file").create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build())

    queryJob = queryJob.waitFor()

  }

  def getDataFromBQ(query: String): Iterable[FieldValueList] =  {
    val queryConfig: QueryJobConfiguration = QueryJobConfiguration.newBuilder(query)
      .setUseLegacySql(false)
      .build()

    val jobId = JobId.of(UUID.randomUUID().toString)
    val queryJob = getBQSession("path_to_cred_file").create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build())

    // Wait for the query to complete.
    queryJob.waitFor()

    import scala.collection.JavaConverters._
    val result: TableResult = queryJob.getQueryResults()
    result.iterateAll().asScala
  }

  def loadDataIntoBQFromFile()={
    val bq_load_cmd =s"""bq load --replace --source_format=csv full_table_name path_to_input_file""".stripMargin
    val x = s"$bq_load_cmd".!

  }

  def loadIntoBQTable(source_path: String,
                      input_type: String,
                      destination_project: String,
                      destination_dataset: String,
                      destination_table: String,
                      write_disposition: JobInfo.WriteDisposition,
                      create_disposition: JobInfo.CreateDisposition,
                      schema: Schema): Map[String, Long] =  {
    // Create Output BQ table instance
    val tableId =  TableId.of(destination_project, destination_dataset, destination_table)

    val jobConfiguration: JobConfiguration = input_type match {
      case "BQ" => QueryJobConfiguration.newBuilder(source_path)
        .setUseLegacySql(false)
        .setDestinationTable(tableId)
        .setWriteDisposition(write_disposition)
        .setCreateDisposition(create_disposition)
        .setAllowLargeResults(true)
        .build()
      case "ORC" | "PARQUET" | "CSV" =>  LoadJobConfiguration
          .builder(tableId, source_path)
          .setFormatOptions(getFormatOptions(input_type))
          .setSchema(schema)
          .setWriteDisposition(write_disposition)
          .setCreateDisposition(create_disposition)
          .build()

      case _ => throw new Exception("Unsupported Input Type")
    }

    // Create BQ job
    val jobId: JobId = JobId.of(UUID.randomUUID().toString)
    val job: Job = getBQSession("path_to_cred_file").create(JobInfo.newBuilder(jobConfiguration).setJobId(jobId).build())

    // Wait for the job to complete
    val completedJob = job.waitFor()
    val destinationTable = getBQSession("path_to_cred_file").getTable(tableId).getDefinition[StandardTableDefinition]

    Map(destination_table -> destinationTable.getNumRows)
  }

  def getFormatOptions(input_type: String): FormatOptions = input_type match {
    case "PARQUET" => FormatOptions.parquet
    case "ORC" => FormatOptions.orc
    case "CSV" => CsvOptions.newBuilder()
      .setSkipLeadingRows(1)
      .setFieldDelimiter(",")
      .build()
    case _ => FormatOptions.parquet
  }

}
