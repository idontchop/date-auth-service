package com.idontchop.dateauthservice.dtos;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;


/**
 * Simple RestMessage with builder pattern.
 * 
 * .build(String) - creates a "message" field
 * .add(key,value) - adds additional fields
 * 
 * @author micro
 *
 */
public class RestMessage {
	
	Map<String,String> messages = new HashMap<>();

	public static RestMessage build (String message) {
		
		return new RestMessage(message);
	}
	public RestMessage () {}
	public RestMessage (String message) {
		messages.put("message", message);
	}
	
	public RestMessage add (String key, String value ) {
		messages.put(key,value);
		return this;
	}
	
	@JsonAnyGetter
	public Map<String, String> getMessages() {
		return messages;
	}
	
	
	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}
	
	public void setMessage (String message) {
		messages.put("message",message);
	}
	
	public void setHost (String host) {
		messages.put("host", host);
	}
	
	public void setAddress (String address) {
		messages.put("address", address);
	}
	
	public void setPort (String port) {
		messages.put("port", port);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messages == null) ? 0 : messages.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestMessage other = (RestMessage) obj;
		if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
			return false;
		return true;
	}

	
}

