package com.idontchop.dateauthservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idontchop.dateauthservice.entities.TestUser;
import com.idontchop.dateauthservice.repositories.TestUserRepository;

@Service
public class TestUserService {
	
	@Autowired
	TestUserRepository testUserRepository;
	
	@Autowired
	JwtService jwtService;
	
	public String retrieveToken (String accessCode) {
		
		return jwtService.buildToken(
		testUserRepository.findByAccessCode(accessCode)
				.orElseThrow()
				.getUsername());
					
	}
	
	public String addTestUser (String username, String accessCode) {
		
		TestUser newTestUser = new TestUser(username, accessCode);
		
		testUserRepository.save(newTestUser);
		
		return retrieveToken(newTestUser.getAccessCode());
	}

}
