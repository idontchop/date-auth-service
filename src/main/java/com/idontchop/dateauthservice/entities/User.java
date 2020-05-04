package com.idontchop.dateauthservice.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.idontchop.dateauthservice.dtos.UserDto;

@Entity
public class User {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO)
	private long id;
	
	// This is the private immutable name for the JWT token
	private String name;
		
	@OneToMany (fetch = FetchType.EAGER, orphanRemoval=true, cascade=CascadeType.ALL)
	@JoinColumn ( name = "user_id" )
	private List<UserSecurity> userSecurity = new ArrayList<>();
	
	public User (String name) {
		this.name = name;
	}
	
	public User () {}
	
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

	public List<UserSecurity> getUserSecurity() {
		return userSecurity;
	}

	public void setUserSecurity(List<UserSecurity> userSecurity) {
		this.userSecurity = userSecurity;
	}
	
	/**
	 * Returns a list of available authentication methods
	 * @return
	 */
	public List<SecurityProvider.Provider> getAuthTypes () {
		return userSecurity.stream()
				.map( us -> us.getSecurityProvider().getName() )
				.collect(Collectors.toList());
	}
	
	public void clearUserSecurity() {
		while ( userSecurity.size() > 0 ) {
			userSecurity.remove(0);
		}
	}

	@JsonIgnore
	public String getFormPassword () {
		return userSecurity.stream()
				.filter( us -> us.isAuthType(SecurityProvider.Provider.FORM) )
				.findFirst()
				.orElseThrow()
				.getPassword();
	}
}
