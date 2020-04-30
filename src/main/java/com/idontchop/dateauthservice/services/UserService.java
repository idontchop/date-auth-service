package com.idontchop.dateauthservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idontchop.dateauthservice.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

}
