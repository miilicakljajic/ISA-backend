package com.isa.springboot.LocationSimulator.controller;

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
    public void produce(@RequestBody String message) {
        System.out.println(message);
        template.send("location-topic", message);
    }


}