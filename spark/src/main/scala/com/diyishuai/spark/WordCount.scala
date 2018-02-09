package com.diyishuai.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: Bruce Zhu
  * @Description:
  * @Date: Created in 21:29 2018/2/2
  * @Midified By: 
  */
object WordCount {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("WC")
    val sc = new SparkContext(conf)
    sc.textFile(args(0))
      .flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .sortBy(_._2,false)
      .saveAsTextFile(args(1))
    sc.stop()

//    val logFile = args(0) // Should be some file on your system
//    val spark = SparkSession.builder.appName("WordCount").getOrCreate()
//    val logData = spark.read.textFile(logFile).cache()
//    val wordCount = logData.flatMap(_.split(" ")).map((_,1))
//    spark.stop()
  }

}
