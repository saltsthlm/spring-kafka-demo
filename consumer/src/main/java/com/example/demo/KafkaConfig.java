package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class KafkaConfig {

    private final ObjectMapper mapper;
    private final AmountsRepository amountsRepository;

    public KafkaConfig(AmountsRepository amountsRepository) {
        this.amountsRepository = amountsRepository;
        mapper = new ObjectMapper();
    }

    @KafkaListener(id = "myId", topics = "payments")
    public void listen(ConsumerRecord<String, String> record) {
        // injecting ConsumerRecord in case we need message metadata
        // otherwise can just inject a String
        String value = record.value();
        try {
            Payment payment = mapper.readValue(value, Payment.class);
            System.out.println("payment = " + payment);
            amountsRepository.addAmount(payment.amount);
        } catch (JsonProcessingException e) {
            System.out.println("EXCEPTION DESERIALIZING MESSAGE!");
            e.printStackTrace();
        }
    }

    public record Payment(String id, String userId, int amount, String currency) {}
}
