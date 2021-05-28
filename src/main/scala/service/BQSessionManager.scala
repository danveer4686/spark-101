package service

import java.io.FileInputStream

import com.google.auth.oauth2.{GoogleCredentials, ServiceAccountCredentials}
import com.google.cloud.bigquery.{BigQuery, BigQueryOptions}

object BQSessionManager {

  def getBQSession(path: String): BigQuery = {
    val cred_file_path: String = sys.env.getOrElse("GOOGLE_APPLICATION_CREDENTIALS", path)
    val credentials: GoogleCredentials = ServiceAccountCredentials.fromStream(new FileInputStream(cred_file_path))
    BigQueryOptions.newBuilder().setCredentials(credentials).build().getService
  }

}
