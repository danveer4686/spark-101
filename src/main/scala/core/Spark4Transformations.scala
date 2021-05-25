package core

import service.SparkSessionManager._

object Spark4Transformations {

  def main(args: Array[String]): Unit = {
    mapExample
    filterExample
    flatmapExample
    groupByExample
    groupByKeyExample
    groupByKeyVsReduceByKey
    mapPartitionExample
    mapPartitionWithIndexExample
    unionExample
    joinExample
  }

  def mapExample()={
    val x = sc.parallelize(Array("b", "a", "c"))
    val y = x.map(z => (z,1))
    x.collect.foreach(println)
    println(y.collect().mkString(", "))
  }

  def filterExample()={
    val x = sc.parallelize(Array(1,2,3))
    val y = x.filter(n => n%2 == 1)
    y.collect.foreach(println)
  }

  def flatmapExample()={
    val x = sc.parallelize(Array(1,2,3))
    val y = x.flatMap(n => Array(n, n*10, 77))
    println(x.collect().mkString(", "))
    println(y.collect().mkString(", "))
  }


  def groupByExample()={
    val x = sc.parallelize(
      Array("John", "Fred", "Anna", "James"))
    val y = x.groupBy(w => w.charAt(0))
    println(y.collect().mkString(", "))
  }

  def groupByKeyExample()={
    val x = sc.parallelize(
      Array(('B',5),('B',4),('A',3),('A',2),('A',1)))
    val y = x.groupByKey()
    println(x.collect().mkString(", "))
    println(y.collect().mkString(", "))
  }

  def groupByKeyVsReduceByKey()={
    val words = Array("one", "two", "two", "three", "three", "three")
    val wordPairsRDD = sc.parallelize(words).map(word => (word, 1))
    val wordCountsWithReduce = wordPairsRDD
      .reduceByKey(_ + _)
      .collect()
    val wordCountsWithGroup = wordPairsRDD
      .groupByKey()
      .map(t => (t._1, t._2.sum))
      .collect()

    wordCountsWithReduce.foreach(println)
    wordCountsWithGroup.foreach(println)
  }

  //mapPartitions returns a new RDD by applying a function to each partition of this RDD
  def mapPartitionExample()={
    val x = sc.parallelize(Array(1,2,3), 2)
    def f(i:Iterator[Int])={ (i.sum,42).productIterator }
    val y = x.mapPartitions(f)
    // glom() flattens elements on the same partition
    val xOut = x.glom().collect()
    val yOut = y.glom().collect()
    xOut.foreach(println)
    yOut.foreach(println)
  }

  def mapPartitionWithIndexExample()={
    val x = sc.parallelize(Array(1,2,3), 2)
    def f(partitionIndex:Int, i:Iterator[Int]) = {
      (partitionIndex, i.sum).productIterator
    }
    val y = x.mapPartitionsWithIndex(f)
    // glom() flattens elements on the same partition
    val xOut = x.glom().collect()
    val yOut = y.glom().collect()
  }

  def unionExample ()={
    val x = sc.parallelize(Array(1,2,3), 2)
    val y = sc.parallelize(Array(3,4), 1)
    val z = x.union(y)
    val zOut = z.glom().collect()
  }

  def joinExample()={
    val x = sc.parallelize(Array(("a", 1), ("b", 2)))
    val y = sc.parallelize(Array(("a", 3), ("a", 4), ("b", 5)))
    val z = x.join(y)
    println(z.collect().mkString(", "))
  }
}
