package com.dasinong.farmerClub.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.ICouponCampaignDao;
import com.dasinong.farmerClub.dao.IStoreDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.store.StoreSource;
import com.dasinong.farmerClub.store.StoreStatus;
import com.dasinong.farmerClub.util.Refcode;

public class InitializeBSFInsRetailer{
	
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
	
	
	public static List<Store> loadRetailer(String filename) throws IOException{
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		
		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		IStoreDao storeDao = (IStoreDao) applicationContext.getBean("storeDao");
		
		List<Store> result = new ArrayList<Store>();
		
		String csvFile = filename;
	    File file = new File(csvFile);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null) {
		    
			String[] units = line.split(",");
			
			String retailer_name= units[2];
			String retailer_cellphone = units[3];
			
			User retailer = userDao.findByCellphone(retailer_cellphone);
			if (retailer==null){
				retailer = getRetailer(retailer_name,retailer_cellphone);
				String refcode;
				do {
					refcode = Refcode.GenerateRefcode();
				} while (userDao.getUIDbyRef(refcode) > 0);
				retailer.setRefcode(refcode);
				userDao.save(retailer);
				System.out.println("new retailer " + retailer.getUserId());
			}
			else{
				retailer.setInstitutionId(Institution.BASF);
				retailer.setUserName(retailer_name);
				retailer.setUserType("retailer");
				userDao.update(retailer);
				System.out.println("old retailer " + retailer.getUserId());
			}
					
			String name = units[4];
			Long ownerId = retailer.getUserId();
			String streetAndNumber = units[5];
			String phone = retailer.getCellPhone();
			String contactName = retailer.getUserName();
			String desc = units[0];
			
			Store store = storeDao.getByOwnerId(retailer.getUserId());
			
			if (store==null){
				store = getStore(name, ownerId, streetAndNumber, phone, contactName, desc);
				storeDao.save(store);
				System.out.println("new store " + store.getId());
			}
			else{
				store.setName(name);
				store.setStreetAndNumber(streetAndNumber);
				storeDao.update(store);
				System.out.println("old store " + store.getId());
			}
			result.add(store);
		}
		reader.close();
		return result;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		
		ICouponCampaignDao couponCampaignDao = (ICouponCampaignDao) applicationContext.getBean("couponCampaignDao");
		
		List<Store> newStores = loadRetailer("/Users/jiangsean/bsfins/hainan.csv");
		CouponCampaign couponCampaign = couponCampaignDao.findById(15L);
		List<Store> oldStores = couponCampaign.getRetailerStores();
		newStores.addAll(oldStores);
		couponCampaign.setRetailerStores(newStores);
		System.out.println("CampaignStore: "+newStores.size());
		couponCampaignDao.update(couponCampaign);
		
	}
}