package com.idontchop.dateauthservice.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.idontchop.dateauthservice.entities.User;

public interface UserRepository extends CrudRepository <User, Long> {

	Optional<User> findByName(String username);

	Optional<User> findById(long id);

}
