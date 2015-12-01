package com.dasinong.farmerClub.sms;

import java.util.HashMap;

import com.dasinong.farmerClub.model.Institution;

public class RefAppShortMessage extends PersistentShortMessageBase {
	
	private Long institutionId = null;
	
	public RefAppShortMessage(Long institutionId) {
		this.institutionId = institutionId;
	}

	@Override
	public HashMap<String, Object> getPersistentData() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("institutionId", this.institutionId);
		return data;
	}

	@Override
	public ShortMessageType getType() {
		return ShortMessageType.APP_REF;
	}

	@Override
	public String getContent() {
		String normalUrl = "http://t.im/rctw";
		String dowsUrl = "http://t.im/sh3v";
		String bsfUrl = "http://jinrinongshi.com/bsf.html";
		String content;
		
		if (this.institutionId == Institution.DOWS) {
			content = "哇朋友向你推荐“今日农事”软件！手机种田好帮手，查天气病虫草害一键搞定，马上免费下载" + dowsUrl + " 回T退订";
		} else if (this.institutionId == Institution.BASF) {
			content = "哇朋友向你推荐“今日农事”软件！手机种田好帮手，查天气病虫草害一键搞定，马上免费下载" + bsfUrl + " 回T退订";
		} else {
			content = "哇朋友向你推荐“今日农事”软件！手机种田好帮手，查天气病虫草害一键搞定，马上免费下载" + normalUrl + " 回T退订";
		}
		
		return content;
	}

	@Override
	public String getSmsProductId() {
		return SmsProductId.USER_MARKETING;
	}
	
}
