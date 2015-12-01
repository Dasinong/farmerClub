package com.dasinong.farmerClub.script;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.IDasinongAppDao;
import com.dasinong.farmerClub.model.DasinongAppManager;

public class CreateDasinongApp {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");

		IDasinongAppDao appDao = (IDasinongAppDao) applicationContext.getBean("dasinongAppDao");
		DasinongAppManager manager = new DasinongAppManager(appDao);
		manager.generate("安卓版今日农事");
		manager.generate("iOS版今日农事");
		manager.generate("农事天地.com");
	}
}
