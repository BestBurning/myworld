package com.di1shuai.kafka.admin;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartitionInfo;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @author: Shea
 * @date: 2020/7/25
 * @description:
 */
public class Admin {
    static Properties props = new Properties();
    static AdminClient adminClient = null;
    static final String streamInTopic = "TextLinesTopic";
    static final String streamOutTopic = "WordsWithCountsTopic";

    static {
        props.setProperty("bootstrap.servers", "kafka:9092,kafka:9093,kafka:9094");
        adminClient = KafkaAdminClient.create(props);
    }

    public static void main(String[] args) {

        //topic
        describeTopic(Collections.singleton("__consumer_offsets"));
        describeTopic(Arrays.asList(streamInTopic, streamOutTopic));
        System.out.println("============");
        topicList();
        System.out.println("============");
        dropTopic();
        System.out.println("============");
        createTopic();
        System.out.println("============");
        topicList();
    }

    private static void dropTopic() {
        DeleteTopicsResult deleteTopicsResult = adminClient.deleteTopics(Arrays.asList(streamInTopic, streamOutTopic));
        KafkaFuture<Void> all = deleteTopicsResult.all();
        while (!all.isDone()) {

        }
        try {
            System.out.println(all.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void createTopic() {
        CreateTopicsResult topics = adminClient.createTopics(Arrays.asList(
                new NewTopic(streamInTopic, 3, (short) 3),
                new NewTopic(streamOutTopic, 3, (short) 3)
                )
        );
//        topics.all().whenComplete(new KafkaFuture.BiConsumer<Void, Throwable>() {
//            @Override
//            public void accept(Void aVoid, Throwable throwable) {
//                if (throwable == null){
//                    System.out.println("ok");
//                }else {
//                    System.out.println(throwable.getMessage());
//                }
//            }
//        });
        try {
            KafkaFuture<Void> all = topics.all();
            while (!all.isDone()) {

            }
            System.out.println(all.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void topicList() {
        ListTopicsResult listTopicsResult = adminClient.listTopics();
        KafkaFuture<Collection<TopicListing>> listings = listTopicsResult.listings();
        while (!listings.isDone()) {

        }
        try {
            Collection<TopicListing> topicListings = listings.get();
            topicListings.stream().forEach(t -> {
                System.out.println(t.name());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private static void describeTopic(Collection<String> topicNames) {
        DescribeTopicsResult consumer_offsets = adminClient.describeTopics(topicNames);
        try {
            Map<String, TopicDescription> stringTopicDescriptionMap = consumer_offsets.all().get();
            for (String s : stringTopicDescriptionMap.keySet()) {
                TopicDescription topicDescription = stringTopicDescriptionMap.get(s);
                System.out.println("name -> " + topicDescription.name());
                System.out.println("partitions -> ");
                List<TopicPartitionInfo> partitions = topicDescription.partitions();
                for (TopicPartitionInfo partition : partitions) {
                    System.out.println(partition);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static void getTopics() {


    }


}
