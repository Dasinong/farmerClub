package com.dasinong.farmerClub.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.UserType;
import com.dasinong.farmerClub.util.Refcode;

public class InitializeTestUserForCoupon {
	
	
	public static User genUser(String cellphone, String name,String password) throws NoSuchAlgorithmException{
		User user = new User();
		user.setUserName(name);
		user.setCellPhone(cellphone);
		user.setAndEncryptPassword(password);
		user.setInstitutionId(3L);
		return user;
	}
	
	
	public static User genRandBSF() throws NoSuchAlgorithmException{
		User user = new User();
		Random rnd = new Random();
		int i = rnd.nextInt(100);
		user.setInstitutionId(3L);
		String name = "巴斯夫";
		
		user.setCellPhone("140000000"+i);
		user.setAndEncryptPassword("0000"+i);
		name = name+i;
		
        i = rnd.nextInt(10);
        switch (i+1){
	        case 0:
	        case 1:
	        case 2:
	        case 3:
	        	name = name + "农户";
	        	user.setUserType(UserType.FARMER);
	        	break;
	        case 4:
	        	name = name + "其它";
	        	user.setUserType(UserType.OTHERS);
	        	break;
	        case 5:
	        case 6:
	        	name = name + "农资";
	        	user.setUserType(UserType.RETAILER);
	        	break;
	        case 7:
	        	name = name + "销售";
	        	user.setUserType(UserType.SALES);
	        	break;
	        case 8:
	        case 9:
	        	name = name + "达人";
	        	user.setUserType(UserType.JIANDADAREN);
	        	break;
        }
        user.setUserName(name);
		user.setInstitutionId(3L);
		return user;
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");

		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		/*
		User user = genUser("13900000005","test10","000005");
		userDao.save(user);
		user = genUser("13900000006","test11","000006");
		userDao.save(user);
		user = genUser("13900000007","test12","000007");
		userDao.save(user);
		user = genUser("13900000010","test13","000010");
		userDao.save(user);
		*/
		/*
		User user = genUser("13900000021","巴斯夫其它","000021");
		userDao.save(user);
		user = genUser("13900000022","巴斯夫农户","000022");
		userDao.save(user);
		user = genUser("13900000023","巴斯夫农资","000023");
		userDao.save(user);
		user = genUser("13900000024","巴斯夫达人","000024");
		userDao.save(user);
		
		*/
		//User user = genUser("13900000025","巴斯夫上海农资","000025");
		for(int i=0;i<10;i++){
				User user = genRandBSF();
				if (userDao.findByCellphone(user.getCellPhone())==null){
					System.out.println(user.getUserName());
					userDao.save(user);
				}
		}
	}
}
