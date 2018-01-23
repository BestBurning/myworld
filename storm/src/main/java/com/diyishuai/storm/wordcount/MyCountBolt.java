package com.diyishuai.storm.wordcount;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class MyCountBolt extends BaseRichBolt{

    OutputCollector outputCollector;
    Map<String, Integer> map = new HashMap<String, Integer>();


    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        this.outputCollector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        String word = tuple.getString(0);
        Integer num = tuple.getInteger(1);
        System.out.println(Thread.currentThread().getId() + "    word:"+word);
        if (map.containsKey(word)){
            Integer count = map.get(word);
            map.put(word,count + num);
        }else {
            map.put(word,num);
        }
        System.out.println("count:"+map);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
//        outputFieldsDeclarer.declare(new Fields(""));
    }
}