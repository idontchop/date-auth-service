package com.idontchop.dateauthservice.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.idontchop.dateauthservice.dtos.UserDto;

@Entity
public class User {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	
	private String email;
	
	@OneToOne
	private UserSecurity userSecurity;
	
	public User (String name) {
		this.name = name;
	}
	
	public User () {}
	
	public User userFromDto ( UserDto userDto ) {
		
		this.name = userDto.getName();
		this.email = userDto.getEmail();
		return this;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserSecurity getUserSecurity() {
		return userSecurity;
	}

	public void setUserSecurity(UserSecurity userSecurity) {
		this.userSecurity = userSecurity;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
