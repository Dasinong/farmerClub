package com.dasinong.farmerClub.sms;

public class WeatherDataShortMessage extends ShortMessageBase {

	private String content;
	
	public WeatherDataShortMessage(Long senderId, String receiver, String content) {
		super(senderId, receiver);
		
		// Temporary hack.
		if (content != null && content.trim().endsWith("Issue loading:"))
			content = content.trim().substring(0, content.length() - 14);
		this.content = content;
	}
	
	public WeatherDataShortMessage(Long senderId, String[] receivers, String content) {
		super(senderId, receivers);
		
		// Temporary hack.
		if (content != null && content.trim().endsWith("Issue loading:"))
			content = content.trim().substring(0, content.length() - 14);
		this.content = content;
	}
	
	@Override
	public ShortMessageType getType() {
		return ShortMessageType.WEATHER_DATA;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public String getSmsProductId() {
		return SmsProductId.NOTIFICATION;
	}

}
