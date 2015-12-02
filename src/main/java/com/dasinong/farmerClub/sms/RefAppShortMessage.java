package com.dasinong.farmerClub.sms;

import java.util.HashMap;

import com.dasinong.farmerClub.model.Institution;

public class RefAppShortMessage extends PersistentShortMessageBase {

	public RefAppShortMessage(Long senderId, String receiver) {
		super(senderId, receiver);
	}

	public RefAppShortMessage(Long senderId, String[] receivers) {
		super(senderId, receivers);
	}
	
	@Override
	public HashMap<String, Object> getPersistentData() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		return data;
	}

	@Override
	public ShortMessageType getType() {
		return ShortMessageType.APP_REF;
	}

	@Override
	public String getContent() {
		String shortLink = "http://t.im/dahu";
		String content = "哇朋友向你推荐“大户俱乐部”软件！手机种田好帮手，查天气病虫草害一键搞定，马上免费下载" + shortLink + " 回T退订";
		return content;
	}

	@Override
	public String getSmsProductId() {
		return SmsProductId.USER_MARKETING;
	}
	
}
