package com.diyishuai.kafka.case2;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Bruce
 * @date 16/9/25
 */
public class Main {
    public static void main(String[] args) {
        new Producer<String, String>() {
            @Override
            public Future<RecordMetadata> send(ProducerRecord<String, String> record) {
                return new Future<RecordMetadata>() {
                    @Override
                    public boolean cancel(boolean mayInterruptIfRunning) {
                        return false;
                    }

                    @Override
                    public boolean isCancelled() {
                        return false;
                    }

                    @Override
                    public boolean isDone() {
                        return false;
                    }

                    @Override
                    public RecordMetadata get() throws InterruptedException, ExecutionException {
                        return null;
                    }

                    @Override
                    public RecordMetadata get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                        return null;
                    }
                };
            }

            @Override
            public Future<RecordMetadata> send(ProducerRecord<String, String> record, Callback callback) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public List<PartitionInfo> partitionsFor(String topic) {
                return null;
            }

            @Override
            public Map<MetricName, ? extends Metric> metrics() {
                return null;
            }

            @Override
            public void close() {

            }

            @Override
            public void close(long timeout, TimeUnit unit) {

            }
        };

    }
}
