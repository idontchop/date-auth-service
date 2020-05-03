package com.idontchop.dateauthservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idontchop.dateauthservice.dtos.UserDto;
import com.idontchop.dateauthservice.entities.User;
import com.idontchop.dateauthservice.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	public User newUser(User user) throws IllegalArgumentException {
		
		if ( userRepository.findByName(user.getName()).isPresent() )
			throw new IllegalArgumentException ("Username: " + user.getName() + " exists.");
		
		return userRepository.save(user);
	}
	
	public User newUserFromDto ( UserDto userDto ) throws IllegalArgumentException {
		
		if ( userRepository.findByName(userDto.getName()).isPresent())
			throw new IllegalArgumentException ("Username: " + userDto.getName() + " exists.");
		
		return new User().userFromDto(userDto);
		
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

}
