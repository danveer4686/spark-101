package common

object Schema {

  case class CC(number:Int)
  case class Stocks(name: String, date: String, price: Double)

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



}
