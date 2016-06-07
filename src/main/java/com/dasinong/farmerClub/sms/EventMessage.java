package com.dasinong.farmerClub.sms;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.dasinong.farmerClub.model.User;

public class EventMessage extends PersistentShortMessageBase{

    private String eventMessage = null;
	
	public EventMessage(Long senderId, String receiver, String eventMessage) {
		super(senderId, receiver);
		this.eventMessage = eventMessage;
	}
	
	
	@Override
	public ShortMessageType getType() {
		return ShortMessageType.SECURITY_CODE;
	}

	@Override
	public String getContent() {
		return eventMessage;
	}

	@Override
	public HashMap<String, Object> getPersistentData() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("eventMessage", this.eventMessage);
		return data;
	}

	@Override
	public String getSmsProductId() {
		return SmsProductId.NOTIFICATION;// SmsProductId.SECURITY_CODE;
	}

	
	public static void main(String[] args){
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		EventMessage message = new EventMessage(1000L, "13162881998", 
				"恭喜你已经成功加入巴斯夫香蕉关爱基金活动！");
		try {
			SMS.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
