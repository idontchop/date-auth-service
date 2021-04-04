package com.idontchop.dateauthservice.controllers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.idontchop.dateauthservice.dtos.RestMessage;
import com.idontchop.dateauthservice.services.TestUserService;

@RestController
public class TestUserController {
	
	@Autowired
	TestUserService testUserService;
	
	@GetMapping("/testuser/{access}")
	public RestMessage getTokenForTestUser(@PathVariable String access) {
		
		try {
			return new RestMessage().add( "token", testUserService.retrieveToken(access));
		} catch (NoSuchElementException ex) {
			return RestMessage.build("Access Code Not Found");
		}
	}
	
}
