package com.example.demo;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class NoSpringConsumer {

    public static void runConsumer(AmountsRepository amountsRepository) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "my-consumer-group");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        props.put("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        String topic = "payments";
        consumer.subscribe(Collections.singletonList(topic));

        // Poll for messages (in an infinite loop)
        try {
            while (true) {
                var records = consumer.poll(1000);

                records.forEach(record -> {
                    System.out.printf("Consumed message: Key = %s, Value = %s, Partition = %d, Offset = %d\n",
                            record.key(), record.value(), record.partition(), record.offset());
                    amountsRepository.addAmount(0);
                });
            }
        } finally {
            consumer.close();
        }
    }
}
