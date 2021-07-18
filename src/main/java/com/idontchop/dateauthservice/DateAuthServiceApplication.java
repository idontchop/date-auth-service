package com.idontchop.dateauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lovemire.messageLibrary.config.EnableMessageLibrary;

@SpringBootApplication
@EnableMessageLibrary
public class DateAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DateAuthServiceApplication.class, args);
	}

}
