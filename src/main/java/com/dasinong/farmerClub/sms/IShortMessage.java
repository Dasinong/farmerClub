package com.dasinong.farmerClub.sms;

public interface IShortMessage {
	
	public ShortMessageType getType();
	
	public String getContent();
	
	public String getSmsProductId();
	
	public Long getSenderId();
	
	public String[] getReceivers();
}
