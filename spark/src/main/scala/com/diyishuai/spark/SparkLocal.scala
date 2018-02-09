package com.diyishuai.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Author: Bruce Zhu
  * @Description:
  * @Date: Created in 9:09 2018/2/5
  * @Midified By: 
  */
object SparkLocal {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("ForeachDemo").setMaster("local")
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(List(1,2,3,4,5,6,7,8,9),3)
    rdd1.foreach(println(_))
    sc.stop()
  }
}
