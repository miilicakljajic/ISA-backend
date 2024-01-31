package com.isa.springboot.MediShipping.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isa.springboot.MediShipping.bean.Contract;
import com.isa.springboot.MediShipping.dto.ContractDto;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ContractService {
    public static ArrayList<ContractDto> activeContracts = new ArrayList<>();
    public ContractService() {
        try {
            activeContracts = loadFromFile();
        }
        catch(IOException e) {
            activeContracts = new ArrayList<ContractDto>();
        }
    }

    public static ContractDto getActiveContract(long companyId){
        for(ContractDto c : activeContracts){
            if(c.getCompanyId() == companyId){
                return  c;
            }
        }
        return null;
    }

     public static void removeOldContract(long companyId){
        for(ContractDto c : activeContracts){
            if(c.getCompanyId() == companyId){
                activeContracts.remove(c);
                break;
            }
        }
    }

    public static ContractDto update(ContractDto updatedContract){
        ContractDto currentContract = null;

        for(ContractDto c : activeContracts){
            if(c.getCompanyId() == updatedContract.getCompanyId()){
                c.setCanDeliver(updatedContract.getCanDeliver());
                currentContract = c;
                break;
            }
        }

        try {
            saveToFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  currentContract;
    }
    public static void saveToFile() throws IOException {
        // Serialize the HashMap to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(activeContracts);

        // Write the JSON string to a file
        try (FileWriter fileWriter = new FileWriter("a.txt")) {
            fileWriter.write(jsonString);
        }
    }

    public ArrayList<ContractDto> loadFromFile() throws JsonProcessingException {
        // Read the JSON content from the file
        String jsonContent = "";
        try {
            jsonContent = readJsonFromFile("a.txt");
        }
        catch(IOException e)
        {
            jsonContent = "";
        }
        // Deserialize the JSON content into a HashMap
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonContent, new TypeReference<ArrayList<ContractDto>>() {});
    }

    private static String readJsonFromFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}