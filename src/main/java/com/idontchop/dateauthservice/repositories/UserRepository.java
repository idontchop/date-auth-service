package com.idontchop.dateauthservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.idontchop.dateauthservice.entities.SecurityProvider;
import com.idontchop.dateauthservice.entities.User;

public interface UserRepository extends CrudRepository <User, Long> {

	@Query(value = "SELECT u FROM User u INNER JOIN u.userSecurity AS us ON us.securityProvider = :sp WHERE us.email = :login")
	Optional<User> findFormUser(String login, SecurityProvider sp);
	
	Optional<User> findByName(String username);

	Optional<User> findById(long id);
	
}
