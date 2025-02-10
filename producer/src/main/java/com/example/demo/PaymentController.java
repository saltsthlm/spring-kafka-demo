package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payments")
public class PaymentController {

    private final KafkaTemplate<String, String> template;
    private final ObjectMapper mapper;

    public PaymentController(KafkaTemplate<String, String> template) {
        this.template = template;
        mapper = new ObjectMapper();
    }

    @PostMapping
    public void postPayment(@RequestBody Payment payment) throws JsonProcessingException {
        // do other stuff
        final String json = mapper.writeValueAsString(payment);
        template.send("payments", payment.userId, json);
    }

    public record Payment(String id, String userId, int amount, String currency) {}
}
