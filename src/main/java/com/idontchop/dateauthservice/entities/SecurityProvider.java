package com.idontchop.dateauthservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Sets up an identity for SSO providers.
 * 
 * @author micro
 *
 */
@Entity
public class SecurityProvider {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	private String name;   // facebook, form...
	
	
	

}
