package com.dasinong.farmerClub.script;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.ICouponCampaignDao;
import com.dasinong.farmerClub.dao.ICouponDao;
import com.dasinong.farmerClub.dao.IInstitutionDao;
import com.dasinong.farmerClub.dao.IStoreDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.util.Env;
import com.dasinong.farmerClub.util.QRGenUtil;
import com.dasinong.farmerClub.util.Refcode;

public class BatchGenerateRef {
	
	public static void addMissingRef(){
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		List<User> users = userDao.getAllUsersWithEmptyRefCode();
		String refcode;
		for(User user:users){
			System.out.println(user.getUserId());
			do {
				refcode = Refcode.GenerateRefcode();
			} while (userDao.getUIDbyRef(refcode) > 0);
			user.setRefcode(refcode);
			userDao.update(user);
		}
	}
	
	public static void main(String[] args) throws Exception {

		//addMissingRef();
		
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");

		for(long i = 4437; i<=4437;i++){
			User user = userDao.findById(i);
			if (user!=null){
			System.out.println(user.getUserId());
			QRGenUtil.gen("function=refcode&code="+user.getRefcode(), Env.getEnv().RefcodeQRDir,""+user.getUserId());
			}
		}
	}

	
}
