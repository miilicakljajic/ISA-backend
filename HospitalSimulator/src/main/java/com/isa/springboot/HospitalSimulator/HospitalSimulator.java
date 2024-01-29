package com.isa.springboot.HospitalSimulator;

import com.isa.springboot.HospitalSimulator.bean.Contract;
import com.isa.springboot.HospitalSimulator.controller.ProducerController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class HospitalSimulator implements CommandLineRunner {
    @Autowired
    ProducerController controller;
    public static void main(String[] args) {
        SpringApplication.run(HospitalSimulator.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        ArrayList<Contract> contracts = new ArrayList<Contract>();
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Enter a company id");
            String companyId = scanner.nextLine();

            System.out.println("Enter a delivery date dd-mm-yyyy");
            String deliveryDate = scanner.nextLine();

            System.out.println("Enter equipment and quantity press c when u're done");
            ArrayList<String> orderItems = new ArrayList<String>();
            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("c")) {
                    break;
                } else {
                    orderItems.add(input);
                }
            }
            Contract newContract = new Contract(Long.parseLong(companyId),orderItems,deliveryDate);
            System.out.println(newContract.toString());
            contracts.add(newContract);

            System.out.println("One more round y/n");
            String stamina = scanner.nextLine();
            if(stamina.equalsIgnoreCase("n")){
                scanner.close();
                break;
            }
        }

        for(Contract c : contracts){
            controller.produce(c);
        }
    }
}