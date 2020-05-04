package com.idontchop.dateauthservice.services;

import java.util.Random;

import org.springframework.stereotype.Service;

/**
 * Responsible for generated random numbers.
 * 
 * @author micro
 *
 */
@Service
public class IdService {
	
	private final int UIDLENGTH 	= 12;
	
	/**
	 * Creates a UIDLENGTH alphanumeric string.
	 * 
	 * @return
	 */
	public String createUid() {
		
		int leftLimit = 48;
		int rightLimit = 123;

		Random rand = new Random();
		
		return
				rand.ints ( leftLimit, rightLimit )
				.filter(i -> !isSymbol(i))
				.limit(UIDLENGTH)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}
	
	private boolean isSymbol(int i) {
		if ( i >= 58 && i <= 64) return true;
		if ( i >= 91 && i <= 96) return true;
		return false;
	}

}
