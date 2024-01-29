package com.isa.springboot.MediShipping.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.springboot.MediShipping.dto.ContractDto;
import com.isa.springboot.MediShipping.util.Consumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    private final Consumer myTopicConsumer;

    public ConsumerController(Consumer myTopicConsumer) {
        this.myTopicConsumer = myTopicConsumer;
    }

    @GetMapping("/get/{companyId}")
    public ContractDto getMessages(@PathVariable long companyId) throws JsonProcessingException {
        List<String> jsonMessages = myTopicConsumer.getMessages();
        if(jsonMessages.isEmpty())
        {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<ContractDto> contracts = new ArrayList<ContractDto>();

        for(String message: jsonMessages)
        {
            ContractDto c = objectMapper.readValue(message,ContractDto.class);
            if(c.companyId == companyId)
            {
                contracts.add(c);
            }
        }
        List<String> dummyList = new ArrayList<>();
        dummyList.add("item;1");
        dummyList.add("item;2");
        return new ContractDto(1,dummyList,"23-5-2024");
        //return contracts.get(contracts.size() - 1);
    }
}