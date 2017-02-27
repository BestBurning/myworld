package com.diyishuai.kafka.case3;

import javafx.animation.KeyValue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Bruce
 * @date 2016/11/3
 */
public class MyProducer3 {

    public static void main(String[] args) {
        List textLines = Arrays.asList("WasdasdadadaWdadsdadadWadsadadadasWdasdadadasdasdasdasd");

        Map wordCounts = textLines
                .stream()
                // Split each text line, by whitespace, into words.
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))

                // Ensure the words are available as record keys for the next aggregate operation.
                .map((key, value) -> new KeyValue(value, value))

                // Count the occurrences of each word (record key) and store the results into a table named "Counts".
                .countByKey("Counts")
    }
}
