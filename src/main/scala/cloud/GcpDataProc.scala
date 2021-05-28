package cloud

import com.google.cloud.dataproc.v1.{Cluster, ClusterConfig, ClusterControllerClient, ClusterControllerSettings, DiskConfig, EndpointConfig, GceClusterConfig, InstanceGroupConfig, LifecycleConfig, SoftwareConfig}
import com.google.protobuf.Duration
import common.Schema.DataprocProperties

import scala.collection.JavaConverters._


object GcpDataProc {

  def main(args:Array[String]):Unit={

    createDataproc( DataprocProperties())
    deleteDataproc(DataprocProperties())

  }

   def createDataproc(props: DataprocProperties):  Unit =  {
    val end_point_config = EndpointConfig.newBuilder().setEnableHttpPortAccess(true)
    val cluster_controller_settings = ClusterControllerSettings.newBuilder.setEndpoint(props.endpoint).build
    val cluster_controller_client = ClusterControllerClient.create(cluster_controller_settings)
    val software_config = SoftwareConfig.newBuilder().setImageVersion(props.image_version)
    val disk_config_m = DiskConfig.newBuilder().setBootDiskType(props.boot_disk_type).setBootDiskSizeGb(props.master_boot_disk_size_gb)
    val disk_config_w = DiskConfig.newBuilder().setBootDiskType(props.boot_disk_type).setBootDiskSizeGb(props.worker_boot_disk_size_gb)

    val gce_cluster_builder = props.subnet_uri match {
      case Some(value) => GceClusterConfig.newBuilder()
        .setInternalIpOnly(true)
        .setSubnetworkUri(value)
        .addAllTags(props.network_tags.asJava)
        .addServiceAccountScopes("https://www.googleapis.com/auth/cloud-platform")
      case None => GceClusterConfig.newBuilder()
        .setInternalIpOnly(true)
        .addAllTags(props.network_tags.asJava)
        .addServiceAccountScopes("https://www.googleapis.com/auth/cloud-platform")
    }

    val gce_cluster_config = props.service_account match {
      case Some(value) => gce_cluster_builder.setServiceAccount(value)
      case _ => gce_cluster_builder
    }

    try {
      val master_config = InstanceGroupConfig.newBuilder.setMachineTypeUri(props.master_machine_type_uri).setNumInstances(props.master_num_instance).setDiskConfig(disk_config_m).build
      val worker_config = InstanceGroupConfig.newBuilder.setMachineTypeUri(props.worker_machine_type_uri).setNumInstances(props.worker_num_instance).setDiskConfig(disk_config_w).build
      val cluster_config_builder = ClusterConfig.newBuilder
        .setMasterConfig(master_config)
        .setWorkerConfig(worker_config)
        .setSoftwareConfig(software_config)
        .setConfigBucket(props.bucket_name)
        .setGceClusterConfig(gce_cluster_config)
        .setEndpointConfig(end_point_config)

      val cluster_config = props.idle_deletion_duration_sec match {
        case Some(value) => cluster_config_builder.setLifecycleConfig(
          LifecycleConfig.newBuilder().setIdleDeleteTtl(Duration.newBuilder().setSeconds(value))
        ).build
        case _       => cluster_config_builder.build
      }

      val cluster = Cluster.newBuilder.setClusterName(props.cluster_name).setConfig(cluster_config).build
      val create_cluster_async_request = cluster_controller_client.createClusterAsync(props.project, props.region, cluster)
      val response = create_cluster_async_request.get
    } catch {
      case e: Throwable =>
        throw e
    } finally if (cluster_controller_client != null) cluster_controller_client.close()
  }

   def deleteDataproc(props: DataprocProperties): Unit =  {
    val cluster_controller_settings = ClusterControllerSettings.newBuilder.setEndpoint(props.endpoint).build
    val cluster_controller_client = ClusterControllerClient.create(cluster_controller_settings)

    try {
      val delete_cluster_async_request = cluster_controller_client.deleteClusterAsync(props.project, props.region, props.cluster_name)
      val response = delete_cluster_async_request.get
    } catch {
      case e: Throwable =>
        throw e
    } finally if (cluster_controller_client != null) cluster_controller_client.close()
  }

}
