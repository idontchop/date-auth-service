package com.idontchop.dateauthservice.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.idontchop.dateauthservice.entities.User;


public class UserPrincipal extends org.springframework.security.core.userdetails.User /*implements OAuth2User*/ {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// true if the user has been saved to data base
	private boolean isRegistered;
	
	// User entity from database
	private User user;
	
	// attributes from Social
	//private Map<String, Object> attributes;
	
	// user request, should contain token
	//private OAuth2UserRequest oUserRequest;
	
	// unused as of 12/20/19
    public UserPrincipal(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
    
    // Used by UserDetailsImpl
    public UserPrincipal(String username, String password, List<GrantedAuthority> createAuthorityList) {
		super (username, password, createAuthorityList);
	}
    /*
    // Used by OAuthUserSErvice
    public UserPrincipal( Optional<User> user, OAuth2User oAuth2User) {
    	
    	super ( oAuth2User.getName(), "", Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER")) );
    	
    	// If database found the user, save it here and properly set isRegistered
    	if ( user.isPresent() ) {
    		this.user = user.get();
    		isRegistered = true;
    	} else isRegistered = false;
    	
    	// social attributes
    	this.attributes = oAuth2User.getAttributes();

    }
    
    public UserPrincipal ( Optional<User> user, OAuth2User oAuth2User, OAuth2UserRequest oUserRequest) {
    	super ( oAuth2User.getName(), "", Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER")) );
    	
    	this.oUserRequest = oUserRequest;
    	
    	// If database found the user, save it here and properly set isRegistered
    	if ( user.isPresent() ) {
    		this.user = user.get();
    		isRegistered = true;
    	} else isRegistered = false;
    	
    	// social attributes
    	this.attributes = oAuth2User.getAttributes();
    }
        
    
	@Override
	public Map<String, Object> getAttributes() {

		return attributes;
	}

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

	@Override
	public String getName() {
		return super.getUsername();
	}
	
	public OAuth2UserRequest getoUserRequest() {
		return oUserRequest;
	}

	public void setoUserRequest(OAuth2UserRequest oUserRequest) {
		this.oUserRequest = oUserRequest;
	}
	
	*/

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	

}
