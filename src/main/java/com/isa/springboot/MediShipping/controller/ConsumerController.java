package com.isa.springboot.MediShipping.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.springboot.MediShipping.dto.ContractDto;
import com.isa.springboot.MediShipping.service.ContractService;
import com.isa.springboot.MediShipping.service.SimulatorService;
import com.isa.springboot.MediShipping.util.Consumer;
import com.isa.springboot.MediShipping.util.LocationConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {
    private final Consumer myTopicConsumer;
    @Autowired
    private LocationConsumer locationTopicConsumer;
    @Autowired
    private SimulatorService simulatorService;

    public ConsumerController(Consumer myTopicConsumer) {
        this.myTopicConsumer = myTopicConsumer;
    }


    public void getMessages(long companyId) throws JsonProcessingException {
        List<String> jsonMessages = myTopicConsumer.getMessages();

        if(jsonMessages.isEmpty())
        {
            return;
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

        ContractDto activeContract = contracts.get(contracts.size() - 1);
        ContractService.removeOldContract(companyId);
        ContractService.activeContracts.add(activeContract);

        try {
            ContractService.saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @GetMapping("/get/{companyId}")
    public ContractDto getActiveContract(@PathVariable long companyId){
        try {
            getMessages(companyId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ContractService.getActiveContract(companyId);
    }

    @PostMapping("/start")
    public void start(@RequestBody String coords){
        simulatorService.sendMessage(coords);
    }
}