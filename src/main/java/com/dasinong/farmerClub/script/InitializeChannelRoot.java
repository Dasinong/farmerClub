package com.dasinong.farmerClub.script;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.util.Refcode;

public class InitializeChannelRoot {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");

		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		List<User> users = userDao.findAll();
		System.out.println("total " + users.size() + " users");
		long startId = 559L;
		long endId = 717L;
		for (User user : users) {
			if (user.getUserId() >= startId && user.getUserId() <= endId) {
				user.setChannel("Yanhua");
				String refcode;
				do {
					refcode = Refcode.GenerateRefcode();
				} while (userDao.getUIDbyRef(refcode) != -1);
				user.setRefcode(refcode);
				user.setInstitutionId(2L);
				user.setUserType("Sales");
				userDao.update(user);

				System.out.println("Initiaze user: " + user.getUserName());
			}
		}
	}
}
