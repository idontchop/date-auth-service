package com.idontchop.dateauthservice.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.idontchop.dateauthservice.entities.SecurityProvider;
import com.idontchop.dateauthservice.entities.SecurityProvider.Provider;

public interface SecurityProviderRepository extends CrudRepository<SecurityProvider, Long> {

	Optional<SecurityProvider> findByName(Provider form);
	

}
