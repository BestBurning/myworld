package com.diyishuai.spark.jdbc

import java.sql.{Connection, Date, DriverManager, PreparedStatement}

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author: Bruce Zhu
  * @description:IP归属地查询
  * 1 加载IP记录到内存
  * 2 将IP字典广播给各个Executor
  * 3 加载访问记录到内存
  * 4 在partition级别对mysql进行连接，并对数据进行统计
  * 5 将得到的结果信息存入jdbc
  * @date: 2018/2/7
  * @midified By: 
  */
object IPLocation {

  def main(args: Array[String]): Unit = {
    //  1 加载IP记录到内存
    val conf = new SparkConf().setAppName("JdbcRDDDemo").setMaster("local[2]")
    val sc = new SparkContext(conf)

    val ipRulesRdd = sc.textFile("C:\\Users\\Administrator\\IdeaProjects\\myworld\\spark\\src\\main\\resources\\ip.txt")
      .map(line => {
        val lineArray = line.split("\\|")
        val ipStart = lineArray(2)
        val ipEnd = lineArray(3)
        val province = lineArray(6)
        (ipStart,ipEnd,province)
      })
    val ipRulesArray = ipRulesRdd.collect
    //  2 将IP字典广播给各个Executor
    val ipRulesRddBroadcast = sc.broadcast(ipRulesArray)
    //  3 加载访问记录到内存
    val ipsRDD = sc.textFile("C:\\Users\\Administrator\\IdeaProjects\\myworld\\spark\\src\\main\\resources\\20090121000132.394251.http.format")
      .map(line => {
        val fields = line.split("\\|")
        fields(1)
      })
    //  4 对数据进行统计
    val result = ipsRDD.map(ip => {
      val ipNum = ip2Long(ip)
      val index = binarySearch(ipRulesRddBroadcast.value,ipNum)
      val info = ipRulesRddBroadcast.value(index)
      info
    }).map(t => (t._3,1)).reduceByKey(_+_)
    //  5 将得到的结果信息存入jdbc
    result.foreachPartition(data2MySQL)
    sc.stop()
  }





  val data2MySQL = (iterator: Iterator[(String, Int)]) => {
    var conn: Connection = null
    var ps : PreparedStatement = null
    val sql = "INSERT INTO location_info (location, counts, accesse_date) VALUES (?, ?, ?)"
    try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata", "root", "admin")
      iterator.foreach(line => {
        ps = conn.prepareStatement(sql)
        ps.setString(1, line._1)
        ps.setInt(2, line._2)
        ps.setDate(3, new Date(System.currentTimeMillis()))
        ps.executeUpdate()
      })
    } catch {
      case e: Exception => println("Mysql Exception")
    } finally {
      if (ps != null)
        ps.close()
      if (conn != null)
        conn.close()
    }
  }

  def ip2Long(ip: String): Long = {
    val fragments = ip.split("[.]")
    var ipNum = 0L
    for (i <- 0 until fragments.length){
      ipNum =  fragments(i).toLong | ipNum << 8L
    }
    ipNum
  }

  def binarySearch(lines: Array[(String, String, String)], ip: Long) : Int = {
    var low = 0
    var high = lines.length - 1
    while (low <= high) {
      val middle = (low + high) / 2
      if ((ip >= lines(middle)._1.toLong) && (ip <= lines(middle)._2.toLong))
        return middle
      if (ip < lines(middle)._1.toLong)
        high = middle - 1
      else {
        low = middle + 1
      }
    }
    -1
  }

}
