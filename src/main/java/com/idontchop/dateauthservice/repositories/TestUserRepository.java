package com.idontchop.dateauthservice.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.idontchop.dateauthservice.entities.TestUser;

public interface TestUserRepository extends CrudRepository<TestUser, Long> {

	public Optional<TestUser> findByAccessCode(String accessCode);
}
