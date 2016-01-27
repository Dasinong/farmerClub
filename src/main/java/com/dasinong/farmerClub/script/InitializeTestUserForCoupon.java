package com.dasinong.farmerClub.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.util.Refcode;

public class InitializeTestUserForCoupon {
	
	
	public static User genUser(String cellphone, String name,String password) throws NoSuchAlgorithmException{
		User user = new User();
		user.setUserName(name);
		user.setCellPhone(cellphone);
		user.setAndEncryptPassword(password);
		return user;
		
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");

		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		User user = genUser("13900000000","test0","000000");
		userDao.save(user);
		user = genUser("13900000001","test1","000001");
		userDao.save(user);
		user = genUser("13900000002","test2","000002");
		userDao.save(user);
		user = genUser("13900000003","test3","000003");
		userDao.save(user);
		user = genUser("13900000004","test4","000004");
		userDao.save(user);
		
	}
}
