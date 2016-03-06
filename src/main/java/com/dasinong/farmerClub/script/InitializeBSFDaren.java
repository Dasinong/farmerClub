package com.dasinong.farmerClub.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.util.Refcode;

public class InitializeBSFDaren{
	
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");

		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		
		String csvFile = "/Users/jiangsean/daren/daren.csv";
		long idcount=4001;
	    File file = new File(csvFile);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		line = reader.readLine();
		while ((line = reader.readLine()) != null) {
			String[] units = line.split(",");
			String name= units[10];
			String cellphone = units[11];
			User user = new User();
			user.setUserId(idcount);
			user.setUserName(name);
			user.setCellPhone(cellphone);
			user.setChannel("internal");
			user.setInstitutionId(Institution.BASF);
			
			user.setUserType("jiandadaren");
			user.setCreateAt(new Date());
			user.setUpdateAt(new Date());
			user.setLastLogin(new Date());
			user.setAuthenticated(true);
			user.setPictureId("default.jpg");
			user.setRefcode(""+idcount);
			idcount++;
			userDao.save(user);
			System.out.println(user.getUserId());
		}

	}
}
