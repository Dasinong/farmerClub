package com.dasinong.farmerClub.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.IStoreDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.store.StoreSource;
import com.dasinong.farmerClub.store.StoreStatus;
import com.dasinong.farmerClub.util.Refcode;

public class InitializeBSFDaren{
	
	public static User getRetailer(String name, String cellphone){
		User user = new User();
		user.setUserName(name);
		user.setCellPhone(cellphone);
		user.setChannel("internal");
		user.setInstitutionId(Institution.BASF);
		
		user.setUserType("retailer");
		user.setCreateAt(new Date());
		user.setUpdateAt(new Date());
		user.setLastLogin(new Date());
		user.setAuthenticated(true);
		user.setPictureId("default.jpg");
		return user;
	}
	
	public static User getManager(String name, String cellphone){
		User user = new User();
		user.setUserName(name);
		user.setCellPhone(cellphone);
		user.setChannel("internal");
		user.setInstitutionId(Institution.BASF);
		
		user.setUserType("sales");
		user.setCreateAt(new Date());
		user.setUpdateAt(new Date());
		user.setLastLogin(new Date());
		user.setAuthenticated(true);
		user.setPictureId("default.jpg");
		return user;
	}
	
	public static Store getStore(String name,Long ownerId, String streetAndNumber,String phone,String contactName, String desc){
		Store store = new Store();
		store.setName(name);
		store.setOwnerId(ownerId);
		store.setStreetAndNumber(streetAndNumber);
		store.setPhone(phone);
		store.setContactName(contactName);
		store.setDesc(desc);
		store.setType(15);
		store.setCreatedAt(new Timestamp((new Date()).getTime()));
		store.setUpdatedAt(new Timestamp((new Date()).getTime()));
		store.setStatus(StoreStatus.ACCEPTED);
		store.setSource(StoreSource.IMPORT_BY_BACKEND);
		Location  dummyLocation= new Location();
		dummyLocation.setLocationId(1L);
		store.setLocation(dummyLocation);
		return store;
		
	}
	
	
	public static void loadRetailerAndManager() throws IOException{
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		
		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		IStoreDao storeDao = (IStoreDao) applicationContext.getBean("storeDao");
		
		
		String csvFile = "/Users/jiangsean/fujiandaren.csv";
	    File file = new File(csvFile);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
		    
			String[] units = line.split(",");
			String daren_Cell = units[9];
			User daren =  userDao.findByCellphone(daren_Cell);
			if (daren!=null){
				System.out.println(daren.getUserId());
			
				String retailer_name= units[6];
				String retailer_cellphone = units[7];
				
				User retailer = userDao.findByCellphone(retailer_cellphone);
			
				if (retailer==null){
					retailer = getRetailer(retailer_name,retailer_cellphone);
					String refcode;
					do {
						refcode = Refcode.GenerateRefcode();
					} while (userDao.getUIDbyRef(refcode) > 0);
					retailer.setRefcode(refcode);
					userDao.save(retailer);
					System.out.println("new retailer" + retailer.getUserId());
					
					String name = units[6];
					Long ownerId = retailer.getUserId();
					String streetAndNumber = units[1]+units[2]+units[3];
					String phone = retailer.getCellPhone();
					String contactName = retailer.getUserName();
					String desc = units[0];
					Store store = getStore(name, ownerId, streetAndNumber, phone, contactName, desc);
				    storeDao.save(store);
				    System.out.println("new store" + store.getId());
				}
				
				daren.setRefuid(retailer.getUserId());
				userDao.update(daren);
				
				
				String manager_name= units[4];
				String manager_cellphone = units[5];
				User manager = userDao.findByCellphone(manager_cellphone);
				if (manager==null){
					manager = getManager(manager_name,manager_cellphone);
					String refcode;
					do {
						refcode = Refcode.GenerateRefcode();
					} while (userDao.getUIDbyRef(refcode) > 0);
					manager.setRefcode(refcode);
					userDao.save(manager);
					System.out.println("new manger" + manager.getUserId());
				}
				retailer.setRefuid(manager.getUserId());
				userDao.update(retailer);
			}
		}
		reader.close();
	}
	
	public static void loadUser() throws IOException{
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		String csvFile = "/Users/jiangsean/fujiandaren.csv";
	    File file = new File(csvFile);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		long idcount = 4801;
		while ((line = reader.readLine()) != null) {
			
			String[] units = line.split(",");
			String name= units[8];
			String cellphone = units[9];
			User user = userDao.findByCellphone(cellphone);
			if (user==null){
				user = new User();
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
			}
			else{
				user.setUserName(name);
				user.setChannel("internal");
				user.setInstitutionId(Institution.BASF);
				
				user.setUserType("jiandadaren");
				user.setCreateAt(new Date());
				user.setUpdateAt(new Date());
				user.setLastLogin(new Date());
				user.setAuthenticated(true);
				user.setPictureId("default.jpg");
				userDao.update(user);
			}
			
			System.out.println(user.getUserId());
		}
		reader.close();
	}
	
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		/*
		IStoreDao storeDao = (IStoreDao) applicationContext.getBean("storeDao");
		for(long i=267L;i<=325;i++){
			Store store = storeDao.findById(i);
			String name = store.getStreetAndNumber();
			store.setStreetAndNumber(store.getName());
			store.setName(name);
			storeDao.update(store);
			System.out.println(store.getId());
		}*/
		//loadUser();
		loadRetailerAndManager();
		
		
	}
}
