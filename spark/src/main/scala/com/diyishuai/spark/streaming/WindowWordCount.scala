package com.diyishuai.spark.streaming

import com.diyishuai.spark.streaming.StageFullWordCount.updateFunction
import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author: Bruce Zhu
  * @description:
  * @date: 2018/2/19
  * @midified By: 
  */
object WindowWordCount {
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
      .reduceByKeyAndWindow((a:Int,b:Int) => (a + b), Seconds(30), Seconds(10))
    wordCounts.print()
    // Print the first ten elements of each RDD generated in this DStream to the console
    //    wordCounts.print()
    ssc.start() // Start the computation
    ssc.awaitTermination() // Wait for the computation to terminate
    // nc -lk 9999


  }
}
