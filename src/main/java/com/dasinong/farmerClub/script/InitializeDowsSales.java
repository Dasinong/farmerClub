package com.dasinong.farmerClub.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.util.Refcode;

public class InitializeDowsSales {
	
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");

		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		
		String csvFile = "/Users/xiahonggao/Desktop/dows.csv";
		
		File file = new File(csvFile);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] units = line.split(",");
			String name= units[1];
			String cellphone = units[2];
			User user = new User();
			user.setUserName(name);
			user.setAndEncryptPassword("888888");
			user.setCellPhone(cellphone);
			user.setChannel("dowsPromotion");
			user.setInstitutionId(Institution.DOWS);
			
			String refcode;
			do {
				refcode = Refcode.GenerateRefcode();
			} while (userDao.getUIDbyRef(refcode) > 0);
			user.setRefcode(refcode);
			
			userDao.save(user);
		}
	}
}
