package com.diyishuai.spark.streaming

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{HashPartitioner, SparkConf}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author: Bruce Zhu
  * @description:
  * @date: 2018/2/19
  * @midified By: 
  */
object KafkaWordCount {


  val updateFunction = (iter: Iterator[(String, Seq[Int], Option[Int])]) => {
    //    iter.flatMap(it => Some(it._2.sum + it._3.getOrElse(0)).map(x => (it._1,x)))
    iter.flatMap { case (x, y, z) => Some(y.sum + z.getOrElse(0)).map((m => (x, m))) }
    //    iter.map(t => (t._1, t._2.sum, t._3.getOrElse(0)))

  }


  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))
    val sc = ssc.sparkContext
    ssc.checkpoint("c://ck2")
    sc.setLogLevel("error")

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "server01:9092,server02:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "kafkaStreamingWordCount",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("test", "diyishuai")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)
    )
    //    stream.map(record => (record.key, record.value)).print()
    stream.map(_.value)
      .flatMap(_.split(" "))
      .map((_, 1))
      .updateStateByKey(updateFunction, new HashPartitioner(sc.defaultParallelism), true)
      .print()

    ssc.start() // Start the computation
    ssc.awaitTermination() // Wait for the computation to terminate

  }
}
