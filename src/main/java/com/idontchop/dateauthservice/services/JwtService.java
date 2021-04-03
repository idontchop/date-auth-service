package com.idontchop.dateauthservice.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value ("${date.auth.expiration-time}")
	private String EXPIRATIONTIME;
	
	@Value ("${date.auth.signing-key}")
	private String SIGNINGKEY;
	
	@Value ("${date.auth.prefix}")
	private String PREFIX;
	
	/**
	 * Supplies a JWT Token for the given user.
	 * 
	 * Sets subject to username
	 * 
	 * Sets one claim: 
	 * 		1) Expiration time from properties
	 * 		
	 * 
	 * @param name
	 * @return the token string
	 */
	public String buildToken ( String name ) {
		
		LocalDateTime ldt = LocalDateTime.now();
		ldt = ldt.plusHours( Integer.parseInt(EXPIRATIONTIME) );
		
		return Jwts.builder().setSubject(name)		
			.setExpiration( Date.from( ldt.atZone( ZoneId.systemDefault() ).toInstant()  ))
			.signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
			.compact();
	}
	
	/**
	 * Extracts the user from the supplied JWT token.
	 * 
	 * Throws errors when JWT expired.
	 * 
	 * TODO: handle jwt errors
	 * 
	 * @param req
	 * @return
	 */
	public String getAuthentication ( HttpServletRequest req ) {
		
		String token = req.getHeader("Authorization");
		
		if ( token != null && StringUtils.hasText(token) && token.startsWith(PREFIX)) {
			
			return Jwts.parser()
					.setSigningKey(SIGNINGKEY)
					.parseClaimsJws( token.replace(PREFIX, ""))
					.getBody()
					.getSubject();
		}
		
		return null;
	}
}
