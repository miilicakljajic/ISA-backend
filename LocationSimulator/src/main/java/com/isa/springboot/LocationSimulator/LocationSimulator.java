package com.isa.springboot.LocationSimulator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.isa.springboot.LocationSimulator.controller.ProducerController;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class LocationSimulator {
        //implements CommandLineRunner {
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    ProducerController controller;
    public static void main(String[] args) {
        SpringApplication.run(LocationSimulator.class, args);
    }

    /*@Override
    public void run(String... args) throws Exception {

    }*/
}