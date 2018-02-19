package com.diyishuai.spark.sql

import org.apache.spark.sql.SparkSession

/**
  * @author: Bruce Zhu
  * @description:
  * @date: 2018/2/13
  * @midified By: 
  */
object SQLDemo {
  def main(args: Array[String]): Unit ={
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
//      .config("spark.some.config.option", "some-value")
      .master("local[2]")
      .getOrCreate()
    val sqlContext = spark.sqlContext
    val sc = spark.sparkContext
    System.setProperty("user.name","root")

    val personRdd = sc.textFile("hdfs://server01:9000/person.txt").map(line => {
      val fields = line.split(",")
      Person(fields(0).toLong,fields(1),fields(2).toInt)
    })

    import sqlContext.implicits._
    val personDF = personRdd.toDF
//    personDF.registerTempTable("person")
    personDF.createOrReplaceTempView("person")
    spark.sql("select * from person where age>= 20 order by age desc limit 2").show()
    spark.sql("select * from person").write.json("hdfs://server01:9000/json")


    spark.close()
  }
}


case class Person(id: Long, name: String, age: Int)
