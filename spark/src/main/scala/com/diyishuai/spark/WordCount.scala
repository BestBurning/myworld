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

    // textFile 产生HadoopRDD和MapPartitionsRDD

    sc.textFile(args(0))
      //MapPartitionsRDD
      .flatMap(_.split(" "))
      //MapPartitionsRDD
      .map((_,1))
      //ShuffledRDD
      .reduceByKey(_+_)
      //MapPartitionsRDD & ShuffledRDD
      .sortBy(_._2,false)
      //MapPartitionsRDD
      .saveAsTextFile(args(1))
    sc.stop()

//    val logFile = args(0) // Should be some file on your system
//    val spark = SparkSession.builder.appName("WordCount").getOrCreate()
//    val logData = spark.read.textFile(logFile).cache()
//    val wordCount = logData.flatMap(_.split(" ")).map((_,1))
//    spark.stop()
  }

}
