package com.diyishuai.spark.streaming

import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author: Bruce Zhu
  * @description:
  * @date: 2018/2/18
  * @midified By: 
  */
object StageFullWordCount {

  val updateFunction = (iter: Iterator[(String, Seq[Int], Option[Int])]) => {
//    iter.flatMap(it => Some(it._2.sum + it._3.getOrElse(0)).map(x => (it._1,x)))
    iter.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map((m => (x, m))) }
//    iter.map(t => (t._1, t._2.sum, t._3.getOrElse(0)))

  }

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(5))
    val sc = ssc.sparkContext
    sc.setLogLevel("error")
    sc.setCheckpointDir("c://ck")
    // Create a DStream that will connect to hostname:port, like localhost:9999
    val lines = ssc.socketTextStream("localhost", 9999)
    // Split each line into words
    val wordCounts = lines.flatMap(_.split(" "))
      .map(word => (word, 1))
      .updateStateByKey(updateFunction, new HashPartitioner(sc.defaultParallelism), true)
    wordCounts.print()
    // Print the first ten elements of each RDD generated in this DStream to the console
    //    wordCounts.print()
    ssc.start() // Start the computation
    ssc.awaitTermination() // Wait for the computation to terminate
    // nc -lk 9999

  }

}
