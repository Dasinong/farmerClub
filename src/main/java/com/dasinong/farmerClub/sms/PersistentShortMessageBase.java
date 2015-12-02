package com.dasinong.farmerClub.sms;

import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;

abstract public class PersistentShortMessageBase extends ShortMessageBase implements IPersistentShortMessage {

	public PersistentShortMessageBase() {}
	
	public PersistentShortMessageBase(Long senderId, String receiver) {
		super(senderId, receiver);
	}
	
	public PersistentShortMessageBase(Long senderId, String[] receivers) {
		super(senderId, receivers);
	}

	public String getSerializedPersistentData() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> persistentData = this.getPersistentData();
		return mapper.writeValueAsString(persistentData);
	}
}
