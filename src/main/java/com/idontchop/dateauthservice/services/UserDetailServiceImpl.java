package com.idontchop.dateauthservice.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.idontchop.dateauthservice.config.UserPrincipal;
import com.idontchop.dateauthservice.entities.User;
import com.idontchop.dateauthservice.repositories.UserRepository;


/**
 * Service implementation for username / password login direct.
 * 
 * @author Nathaniel J Dunn
 *
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserPrincipal loadUserByUsername ( String username ) throws UsernameNotFoundException {
		
		
		Optional<User> userOptional = userRepository.findByName ( username );
					
		if ( !userOptional.isPresent() ) {
			throw new UsernameNotFoundException(username + " not found");
		}
		
		User user = userOptional.get();
		UserPrincipal userPrincipal = new UserPrincipal ( user.getName(),
						user.getPassword(),
				AuthorityUtils.createAuthorityList("USER"));
		
		userPrincipal.setUser(user);
		
		return userPrincipal;
		
	}
	
	/**
	 * JWT tokens only store the id of the user to standardize between
	 * oauth logins and form logins
	 * 
	 * @param id
	 * @return
	 * @throws UsernameNotFoundException
	 */
	public UserPrincipal loadUserById ( long id ) throws UsernameNotFoundException {
		
		Optional<User> userOptional = userRepository.findById(id);
		
		if ( !userOptional.isPresent() ) {
			throw new UsernameNotFoundException( id + " not found");
		}
		
		User user = userOptional.get();
		UserPrincipal up = new UserPrincipal ( Long.toString(id), "",
				AuthorityUtils.createAuthorityList("USER") );
		
		up.setUser(user);
		
		return up;
	}
	
	public UserPrincipal loadUserById ( String idString ) throws UsernameNotFoundException {
		
		try {
			long id = Long.parseLong(idString);
			return loadUserById(id);
		} catch (NumberFormatException e) {
			return loadUserByUsername(idString);
		}
	}

}
