package com.idontchop.dateauthservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.idontchop.dateauthservice.dtos.UserDto;
import com.idontchop.dateauthservice.entities.SecurityProvider;
import com.idontchop.dateauthservice.entities.User;
import com.idontchop.dateauthservice.entities.UserSecurity;
import com.idontchop.dateauthservice.repositories.SecurityProviderRepository;
import com.idontchop.dateauthservice.repositories.UserRepository;
import com.idontchop.dateauthservice.services.IdService;
import com.idontchop.dateauthservice.services.UserService;

@SpringBootTest
class DateAuthServiceApplicationTests {
	
	Logger logger = LoggerFactory.getLogger(DateAuthServiceApplicationTests.class);

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	SecurityProviderRepository spRepository;
	
	@Autowired
	IdService idService;
	
	@Test
	void contextLoads() {
		
		for ( int i = 0; i < 50; i++) {
			logger.info(idService.createUid());
		}
	}
	
	@Test
	void testUid () {
		
	}

	@Test
	void createDb() {
		
		String password = passwordEncoder.encode("1234");
		
		SecurityProvider sp = spRepository.findById(1L).get();
		UserSecurity us1 = new UserSecurity(sp).pw(password).login("Nate");
		UserSecurity us2 = new UserSecurity(sp).pw(password).login("Nate2");
		
		User user = userRepository.findByName("Nate").orElse( new User("Nate") );
		User user2 = userRepository.findByName("Nate2").orElse( new User("Nate2"));
		
		user.clearUserSecurity();
		//assertEquals(0,user.getUserSecurity().size());
		user.getUserSecurity().add(us1);
		user2.clearUserSecurity();
		user2.getUserSecurity().add(us2);
		

		userRepository.save(user);
		userRepository.save(user2);
		
		assertTrue ( userRepository.findByName("Nate").isPresent());
	}
	
	@Test
	@Transactional
	void testQuery () {
		
		SecurityProvider sp = spRepository.findById(1L).get();
		assertTrue ( userRepository.findFormUser("Nate", sp).isPresent() );
		User user = userRepository.findFormUser("Nate", sp).get();
		assertEquals(1, user.getUserSecurity().size());
		assertTrue ( userRepository.findFormUser("Nate2", sp).isPresent() );
	}
	
	@Test
	@Transactional
	void testDb () {
		
		SecurityProvider sp = spRepository.findById(1L).get();
		String username = "Nate";
		String password = "1234";
		
		UserDto userDto = new UserDto();
		userDto.setName(username);
		userDto.setPassword(password);
		
		User user = userRepository.findFormUser(username, sp).orElseThrow();
		
		assertEquals(1, user.getUserSecurity().size());
		
		UserDto userdto = userService.loginForm(userDto);
		
		assertEquals("username", userdto.getName());
		
	}
}
