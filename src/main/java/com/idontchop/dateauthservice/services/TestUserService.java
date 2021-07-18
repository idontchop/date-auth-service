package com.idontchop.dateauthservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idontchop.dateauthservice.entities.TestUser;
import com.idontchop.dateauthservice.repositories.TestUserRepository;
import com.lovemire.messageLibrary.config.enums.LoveMireEvents;

@Service
public class TestUserService {
	
	@Autowired
	TestUserRepository testUserRepository;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	MessageService messageService;
	
	public String retrieveToken (String accessCode) {
		
		return jwtService.buildToken(
		testUserRepository.findByAccessCode(accessCode)
				.orElseThrow()
				.getUsername());
					
	}
	
	public String addTestUser (String username, String accessCode) {
		
		TestUser newTestUser = new TestUser(username, accessCode);
		
		testUserRepository.save(newTestUser);
		
		messageService.sendAccountEvent(LoveMireEvents.ACCOUNT_CREATED, newTestUser);
		
		return retrieveToken(newTestUser.getAccessCode());
	}

}
