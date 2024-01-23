package cloud

import com.google.cloud.dataproc.v1.{Batch, BatchControllerClient, CreateBatchRequest, LocationName, _}
import com.google.protobuf.Duration
import common.Schema.{DataprocProperties, libs}
import com.google.api.core.ApiFuture
import com.google.api.gax.core.FixedCredentialsProvider
import com.google.longrunning.Operation
import common.Config.fixedCredentialsProvider

import scala.collection.JavaConverters._


object GcpDataProcBatch {

  def main(args:Array[String]):Unit={

    val sparkBatch = SparkBatch.newBuilder()
      .addAllJarFileUris(libs.asJava)
      //.putAllProperties(spark_conf.asJava)
      .setMainClass("etljobs.LoadData")
      .addAllArgs(List("run_job", "--job_name", "EtlJobRegProductImpact").asJava)
      .build()
    val batch  = Batch.newBuilder().setSparkBatch(sparkBatch)
    val batchControllerSettings = BatchControllerSettings.newBuilder()
        //.setCredentialsProvider(fixedCredentialsProvider)
        .setEndpoint("asia-south1-dataproc.googleapis.com:443")
        .build();
    val batchControllerClient = BatchControllerClient.create(batchControllerSettings)
    val request =
      CreateBatchRequest.newBuilder()
        .setParent(LocationName.of("mint-bi-reporting-non-prod", "asia-south1").toString())
        .setBatch(batch)
        .setBatchId("batchId-331744779")
        .setRequestId("requestId693933066")
        .build()

    val future = batchControllerClient.createBatchCallable().futureCall(request)
    val response = future.get()

  }


}
