package com.diyishuai.storm.wordcount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class TopologyMain {

    public static void main(String[] args) {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("mySpout",new MySpout(),1);
        topologyBuilder.setBolt("mybolt1",new MySplitBolt(),10).shuffleGrouping("mySpout");
        topologyBuilder.setBolt("mybolt2",new MyCountBolt(),2).fieldsGrouping("mybolt1", new Fields("word"));

        //2、创建一个configuration，用来指定当前topology 需要的worker的数量
        Config config =  new Config();
        config.setNumWorkers(2);

        //3、提交任务  -----两种模式 本地模式和集群模式
//        StormSubmitter.submitTopology("mywordcount",config,topologyBuilder.createTopology());
        LocalCluster localCluster = new LocalCluster();
        localCluster.submitTopology("mywordcount",config,topologyBuilder.createTopology());

    }

}
