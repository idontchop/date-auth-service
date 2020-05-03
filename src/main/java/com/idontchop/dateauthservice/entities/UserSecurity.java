package com.idontchop.dateauthservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Specifies which Security Provider and holds data specific to it.
 * 
 * Most fields can be null.
 * 
 * @author micro
 *
 */
@Entity
public class UserSecurity {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY)
	private long id;
	
	// fields which might be null depending on provider
	private String password;	
	private String email;
	
	@ManyToOne
	private SecurityProvider securityProvider;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SecurityProvider getSecurityProvider() {
		return securityProvider;
	}

	public void setSecurityProvider(SecurityProvider securityProvider) {
		this.securityProvider = securityProvider;
	}
	
	

}
