package com.isa.springboot.MediShipping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SimulatorService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendMessage(String message){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create an HttpEntity with the data and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(message, headers);

        // Make the POST request
        //ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:4338/api/producer/start", requestEntity, String.class);
        restTemplate.postForEntity("http://localhost:4338/api/producer/start", requestEntity, String.class);

        // Process the response as needed
        //String responseData = responseEntity.getBody();
    }
}
