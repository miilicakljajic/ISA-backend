package com.isa.springboot.MediShipping.controller;
import com.isa.springboot.MediShipping.util.Consumer;
import com.isa.springboot.MediShipping.util.LocationConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;




@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    private final Consumer myTopicConsumer;
    @Autowired
    private LocationConsumer locationTopicConsumer;

    public ConsumerController(Consumer myTopicConsumer) {
        this.myTopicConsumer = myTopicConsumer;
    }

    @GetMapping("/get")
    public List<String> getMessages() {
        return myTopicConsumer.getMessages();
    }

    @GetMapping("/location/get")
    public String getLocationMessages() {
        String startingLocation = "19.842430,45.254344";
        String startingLocationReversed = "45.254344,19.842430";

        List<String> messages = locationTopicConsumer.getMessages();
        if(messages.isEmpty())
            return startingLocationReversed;
        String lastLocation = messages.get(messages.size()-1);
        return lastLocation.equals("DONE") ? startingLocationReversed:lastLocation;
    }

}