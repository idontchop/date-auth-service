package com.idontchop.dateauthservice.repositories;

import org.springframework.data.repository.CrudRepository;

import com.idontchop.dateauthservice.entities.UserSecurity;

public interface UserSecurityRepository extends CrudRepository<UserSecurity, Long> {
	
}
