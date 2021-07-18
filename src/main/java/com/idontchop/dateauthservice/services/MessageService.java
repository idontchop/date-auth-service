package com.idontchop.dateauthservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idontchop.dateauthservice.entities.TestUser;
import com.idontchop.dateauthservice.entities.User;
import com.lovemire.messageLibrary.config.enums.LoveMireEvents;
import com.lovemire.messageLibrary.services.LoveMireMessageService;

/**
 * Responsible for handling Messages sent about accounts. 
 * 
 * @author nathan
 *
 */
@Service
public class MessageService {
	
	@Autowired
	LoveMireMessageService ms;
	
	public void sendAccountEvent(LoveMireEvents event, User user) {
		
		ms.sendEvent(event, user.getName(), user);
	}
	
	public void sendAccountEvent(LoveMireEvents event, TestUser testUser) {
		ms.sendEvent(event, testUser.getName(), testUser);
	}

}
