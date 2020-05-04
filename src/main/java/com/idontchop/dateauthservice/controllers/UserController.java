package com.idontchop.dateauthservice.controllers;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.idontchop.dateauthservice.config.UserPrincipal;
import com.idontchop.dateauthservice.dtos.RestMessage;
import com.idontchop.dateauthservice.dtos.UserDto;
import com.idontchop.dateauthservice.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping ("/login")
	public UserDto loginUser(@RequestBody @Valid UserDto userDto) {
		return userService.loginForm(userDto);
	}
	
	
	@PostMapping ("/new")
	public UserDto newUser(@RequestBody @Valid UserDto userDto) {
		
		UserDto newUserDto = new UserDto(userService.newFormUserFromDto(userDto));
		
		return userService.addToken(newUserDto);
		
	}
	
	/**
	 * Exposes limited user information from the Principal.
	 * 
	 * This endpoint is used by frontend to determine UI to display. Whether
	 * user has filled out details, what network is logged in through.
	 * 
	 * See UserDto.java
	 * 
	 * @return
	 */
	@GetMapping("/user")
	@PreAuthorize ("hasRole('USER')")
	public RestMessage userEndpoint ( @AuthenticationPrincipal UserPrincipal up ) {
		
		return RestMessage.build("hello")
				.add("user", up.getUsername());
	}
	
	@RequestMapping("/plainUp")
	public Authentication plainUp ( ) {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@ExceptionHandler({NoSuchElementException.class})
	@ResponseStatus( code = HttpStatus.NOT_FOUND)
	public RestMessage userNotFound() {
		return RestMessage.build("user/password not found");
	}
	
	@ExceptionHandler({IllegalArgumentException.class})
	@ResponseStatus( code = HttpStatus.CONFLICT )
	public RestMessage illegal(IllegalArgumentException ex) {
		return RestMessage.build(ex.getMessage());
	}
	/*
	@GetMapping( "/me/profile" )
	public UserProfile myUserProfile ( @AuthenticationPrincipal UserPrincipal up ) {
		return up.getUser().getProfile();
	}
	*/
	
	/**
	 * Creates a new User Profile with each request.
	 * 
	 * ( The main difference between this and updateMyUserProfile: created and id will be reset
	 * hibernate is set to delete orphaned userprofiles )
	 * 
	 * @param newUserProfile
	 * @param up
	 * @return
	 */ /*
	@PostMapping ( "/me/profile" )
	public ResponseEntity<UserProfile> newMyUserProfile ( 
			@Valid @RequestBody UserProfile newUserProfile,
			@AuthenticationPrincipal UserPrincipal up ) {
		
		if ( !userService.saveUserProfile(newUserProfile, up ) ) {
			// Unable to save profile
			throw new ResponseStatusException (HttpStatus.INTERNAL_SERVER_ERROR, "Database Profile Mismatch");
		}
		
		// default return, success
		return ResponseEntity.ok().body(newUserProfile);
	}
	
	@PutMapping ( "/me/profile" )
	public ResponseEntity<UserProfile> updateMyUserProfile (
			@Valid @RequestBody UserProfile newUserProfile,
			@AuthenticationPrincipal UserPrincipal up ) {
		
		if ( !userService.updateUserProfile(newUserProfile, up) ) {
			throw new ResponseStatusException ( HttpStatus.INTERNAL_SERVER_ERROR, "Database Profile Mismatch");
		}
		
		return ResponseEntity.ok().body( up.getUser().getProfile() );
	} */
}
