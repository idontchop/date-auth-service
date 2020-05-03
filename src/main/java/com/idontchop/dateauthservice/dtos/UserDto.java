package com.idontchop.dateauthservice.dtos;

import javax.validation.constraints.NotBlank;

import com.idontchop.dateauthservice.entities.User;

public class UserDto {
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String email;
	
	private String token;
	
	public UserDto() {}
	
	public UserDto( User user ) {
		this.name = user.getName();
		this.email = user.getEmail();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public UserDto setToken(String token) {
		this.token = token;
		return this;
	}
	
	

}
