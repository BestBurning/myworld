package com.diyishuai.spark.debug

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author: Bruce Zhu
  * @description:
  *              spark远程debug
  *              1 先用maven打好包
  *              2 SparkConf要设置jars和Master
  *              3 断点
  *              4 debug run
  * @date: 2018/2/10
  * @midified By: 
  */
object SparkRemoteDebug {

  val sourceFile = "hdfs://server01:9000/wc"
  val targetFile = "hdfs://server01:9000/out11"

  def main(args: Array[String]): Unit ={

    val conf = new SparkConf().setAppName("SparkRemoteDebug")
      //先要用maven打包好
      .setJars(Array("C:\\Users\\Administrator\\IdeaProjects\\myworld\\spark\\target\\spark-1.0-SNAPSHOT.jar"))
      .setMaster("spark://server01:7077")
      .setIfMissing("spark.driver.host", "192.168.0.26")
    val sc = new SparkContext(conf)
    println("=====  start  ========")
    sc.textFile(sourceFile)
        .cache()
      .flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
      .saveAsTextFile(targetFile)
//      .collect()
    println("=====   end   ========")
//    println(result.toBuffer)
    sc.stop()
  }

}
