package com.diyishuai.storm.kafka;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.spout.KafkaSpout;
import org.apache.storm.kafka.spout.KafkaSpoutConfig;
import org.apache.storm.topology.TopologyBuilder;

public class KafkaTopology {

    public static final String topicName = "order";
    public static final String bootStrapServers = "server01:9092,server02:9092,server03:9092";

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        KafkaSpout kafkaSpout = new KafkaSpout(
                KafkaSpoutConfig.builder(bootStrapServers,topicName)
                .setGroupId("kafkaSpout")
                .build()
        );

        topologyBuilder.setSpout("kafkaSpout",kafkaSpout,1);
        topologyBuilder.setBolt("orderBolt",new OrderBolt(),1)
                        .shuffleGrouping("kafkaSpout");


        Config config = new Config();
        config.setNumWorkers(1);
        //3、提交任务  -----两种模式 本地模式和集群模式
        if (args.length>0) {
            StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());
        }else {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("storm2kafka", config, topologyBuilder.createTopology());
        }
    }

}
