package com.idontchop.dateauthservice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.idontchop.dateauthservice.entities.User;
import com.idontchop.dateauthservice.repositories.UserRepository;

@SpringBootTest
class DateAuthServiceApplicationTests {

	@Autowired
	UserRepository userRepository;
	
	@Test
	void contextLoads() {
	}

	@Test
	void createDb() {
		
		User user = new User();
		
		user.setName("Nate");
		user.setPassword("1234");
		
		userRepository.save(user);
		
		assertTrue ( userRepository.findByName("Nate").isPresent());
	}
}
