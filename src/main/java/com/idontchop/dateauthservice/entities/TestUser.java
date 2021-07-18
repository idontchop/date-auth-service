package com.idontchop.dateauthservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * For Development. Provides a (non-secure) one time code that will automatically retrieve
 * a username in a token, without supplying password or sso.
 * 
 * @author nathan
 *
 */
@Entity
public class TestUser {

	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO)
	private long id;
	
	private String username;
	
	private String accessCode;
	
	public TestUser() {}
	public TestUser( String username, String accessCode) {
		this.username = username;
		this.accessCode = accessCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}
	
	
}
