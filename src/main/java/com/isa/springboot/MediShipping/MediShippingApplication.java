package com.isa.springboot.MediShipping;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MediShippingApplication {

	@Bean
	public ModelMapper getModelMapper() {return  new ModelMapper();}
	public static void main(String[] args) {
		SpringApplication.run(MediShippingApplication.class, args);
	}

}
