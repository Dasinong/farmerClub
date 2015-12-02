package com.dasinong.farmerClub.sms;

abstract public class ShortMessageBase implements IShortMessage {

	protected Long senderId;
	protected String[] receivers;
	
	public ShortMessageBase() {}
	
	public ShortMessageBase(Long senderId, String receiver) {
		this.senderId = senderId;
		this.receivers = new String[1];
		this.receivers[0] = receiver;
	}
	
	public ShortMessageBase(Long senderId, String[] receivers) {
		this.senderId = senderId;
		this.receivers = receivers;
	}
	
	public Long getSenderId() {
		return this.senderId;
	}
	
	public String[] getReceivers() {
		return this.receivers;
	}
}
