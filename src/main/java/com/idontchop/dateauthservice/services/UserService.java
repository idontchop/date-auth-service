package com.idontchop.dateauthservice.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.idontchop.dateauthservice.dtos.UserDto;
import com.idontchop.dateauthservice.entities.SecurityProvider;
import com.idontchop.dateauthservice.entities.User;
import com.idontchop.dateauthservice.entities.UserSecurity;
import com.idontchop.dateauthservice.repositories.SecurityProviderRepository;
import com.idontchop.dateauthservice.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SecurityProviderRepository securityProviderRepository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	IdService idService;
	
	public User newUser( User user ) throws IllegalArgumentException {
		
		checkUser ( user.getName() );
		return userRepository.save(user);
		
	}
	
	public User newFormUser ( String name, String password ) throws IllegalArgumentException, NoSuchElementException {
		
		checkUser(name);
		
		String newUid;
		
		// Get a new Uid, double check not already tried (random but not necessarily unique)
		do {
			newUid = idService.createUid();
		} while ( userRepository.findByName(newUid).isPresent() );
		
		User user = new User( newUid );
		
		// setup form security (username/password)
		UserSecurity us = new UserSecurity().login(name).pw(passwordEncoder.encode(password));
		us.setSecurityProvider(formSp());
		
		user.getUserSecurity().add(us);
		
		return userRepository.save(user);
		
	}
	
	public User newFormUserFromDto ( UserDto userDto ) throws IllegalArgumentException, NoSuchElementException {
		
		return newFormUser( userDto.getEmail(), userDto.getPassword() );		
		
	}
	
	public void checkUser( String name ) throws IllegalArgumentException, NoSuchElementException {
		SecurityProvider sp = formSp();
		if ( userRepository.findFormUser(name, sp).isPresent() )
			throw new IllegalArgumentException ("Username: " + name + " exists.");
	}
	
	private SecurityProvider formSp () throws IllegalArgumentException, NoSuchElementException {
		return securityProviderRepository.findByName(SecurityProvider.Provider.FORM)
				.orElseThrow();
	}
	
	/**
	 * Adds token to dto, returns the dto.
	 * 
	 * @param userDto
	 * @return
	 */
	public UserDto addToken ( UserDto userDto ) {
		
		String token = jwtService.buildToken(userDto.getName());
		userDto.setToken(token);
		return userDto;
		
	}
	
	/**
	 * Logs the user in via form.
	 * 
	 * This checks the user credentials in the supplied UserDto and then passed the Dto back
	 * with a reset token.
	 * 
	 * @param userDto
	 * @return
	 * @throws NoSuchElementException
	 */
	public UserDto loginForm ( UserDto userDto ) throws NoSuchElementException {
		
		String username = userDto.getEmail();
		String password = userDto.getPassword();
		
		SecurityProvider sp = securityProviderRepository.findByName(SecurityProvider.Provider.FORM)
				.orElseThrow();
		
		// will throw nosuchelement if user not found
		User user = userRepository.findFormUser(username, sp)
				.orElseThrow();
		
		// Check password
		if ( ! passwordEncoder.matches( password , user.getFormPassword()) ) 
				throw new NoSuchElementException();
		
		// Return new DTO with jwt token
		return new UserDto(user)
				.setToken(jwtService.buildToken(user.getName()));
		
	}

}
