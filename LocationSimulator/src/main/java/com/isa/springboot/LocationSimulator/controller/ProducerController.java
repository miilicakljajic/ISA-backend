package com.isa.springboot.LocationSimulator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {

    private KafkaTemplate<String, String> template;
    RestTemplate restTemplate = new RestTemplate();
    public ProducerController(KafkaTemplate<String, String> template) {
        this.template = template;
    }


    @PostMapping("/send")
    public void produce(@RequestBody String message) {
        System.out.println(message);
        template.send("location-topic", message);
    }


    @PostMapping("/start")
    public void start(@RequestBody String coords) throws JsonProcessingException, InterruptedException {
        //var test = restClient.get().uri("https://cat-fact.herokuapp.com/facts/random?animal_type=cat").retrieve().body(String.class);
        //System.out.println(test);
        //20.924065925098496,44.664650800000004
        System.out.println(coords);
        CompletableFuture.runAsync(() -> {
            String fooResourceUrl
                    = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf62484c6ea22c8cf443c29c16b2d1fa248cc6&start="+coords+"&end=19.84243,45.254344";
            ResponseEntity<String> response
                    = restTemplate.getForEntity(fooResourceUrl, String.class);
            JSONObject obj = new JSONObject(response.getBody());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = null;
            try {
                jsonNode = objectMapper.readTree(response.getBody());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            JsonNode coordinatesNode = jsonNode.path("features").get(0).path("geometry").path("coordinates");
            for (JsonNode coordinatePair : coordinatesNode) {
                double longitude = coordinatePair.get(0).asDouble();
                double latitude = coordinatePair.get(1).asDouble();

                // Ovde možete raditi šta god vam je potrebno sa svakim parom koordinata
                //System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
                System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                produce(latitude + "," + longitude);
            }
            produce("DONE");
            System.out.println("Done");
        });
        return;
    }
}