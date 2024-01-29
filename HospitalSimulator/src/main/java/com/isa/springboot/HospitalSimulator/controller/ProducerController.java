package com.isa.springboot.HospitalSimulator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.springboot.HospitalSimulator.bean.Contract;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {

    private KafkaTemplate<String, String> template;
    public ProducerController(KafkaTemplate<String, String> template) {
        this.template = template;
    }
    @PostMapping("/send")
    public void produce(@RequestBody Contract contract) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(contract);
        System.out.println(json);
        template.send("test-topic", json);
    }
}