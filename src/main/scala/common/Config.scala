package common

import org.apache.spark.sql.types._

import java.io.FileInputStream

object Config {


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

  import com.google.api.gax.core.FixedCredentialsProvider
  import com.google.auth.oauth2.GoogleCredentials

  var credentials: GoogleCredentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/etlflow-dev-local.json"))
  val fixedCredentialsProvider: FixedCredentialsProvider = FixedCredentialsProvider.create(credentials)

}
