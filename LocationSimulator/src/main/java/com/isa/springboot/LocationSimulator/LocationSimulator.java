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
public class LocationSimulator implements CommandLineRunner {
    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    ProducerController controller;
    public static void main(String[] args) {
        SpringApplication.run(LocationSimulator.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //var test = restClient.get().uri("https://cat-fact.herokuapp.com/facts/random?animal_type=cat").retrieve().body(String.class);
        //System.out.println(test);
        String fooResourceUrl
                = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf62484c6ea22c8cf443c29c16b2d1fa248cc6&start=19.84243,45.254344&end=20.924065925098496,44.664650800000004";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl, String.class);
        JSONObject obj = new JSONObject(response.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        var jsonNode = objectMapper.readTree(response.getBody());
        JsonNode coordinatesNode = jsonNode.path("features").get(0).path("geometry").path("coordinates");
        for (JsonNode coordinatePair : coordinatesNode) {
            double longitude = coordinatePair.get(0).asDouble();
            double latitude = coordinatePair.get(1).asDouble();

            // Ovde možete raditi šta god vam je potrebno sa svakim parom koordinata
            //System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
            System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
            Thread.sleep(500);
            controller.produce(latitude+","+longitude);
        }
        controller.produce("DONE");
        System.out.println("Done");
    }
}