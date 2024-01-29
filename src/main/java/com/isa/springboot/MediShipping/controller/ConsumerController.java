package com.isa.springboot.MediShipping.controller;
import com.isa.springboot.MediShipping.util.Consumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;




@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    private final Consumer myTopicConsumer;

    public ConsumerController(Consumer myTopicConsumer) {
        this.myTopicConsumer = myTopicConsumer;
    }

    @GetMapping("/get")
    public List<String> getMessages() {
        return myTopicConsumer.getMessages();
    }

}