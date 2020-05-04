package com.idontchop.dateauthservice.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	
	public enum Provider {
		FORM,
		FACEBOOK
	}
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Provider name;   // facebook, form...

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Provider getName() {
		return name;
	}

	public void setName(Provider name) {
		this.name = name;
	}	

	
}
