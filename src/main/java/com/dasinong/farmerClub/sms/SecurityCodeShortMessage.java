package com.dasinong.farmerClub.sms;

import java.util.HashMap;

public class SecurityCodeShortMessage extends PersistentShortMessageBase {

	private String securityCode = null;
	
	public SecurityCodeShortMessage(Long senderId, String receiver, String securityCode) {
		super(senderId, receiver);
		
		this.securityCode = securityCode;
	}
	
	public SecurityCodeShortMessage(Long senderId, String[] receivers, String securityCode) {
		super(senderId, receivers);
		
		this.securityCode = securityCode;
	}
	
	@Override
	public ShortMessageType getType() {
		return ShortMessageType.SECURITY_CODE;
	}

	@Override
	public String getContent() {
		return "临时登录密码" + securityCode + "，请妥善保管，不要泄露给他人。登陆成功后记得及时修改密码。此临时密码将于3小时后失效。";
	}

	@Override
	public HashMap<String, Object> getPersistentData() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("securityCode", this.securityCode);
		return data;
	}

	@Override
	public String getSmsProductId() {
		return SmsProductId.NOTIFICATION;// SmsProductId.SECURITY_CODE;
	}

}
