//This class will insert

package com.dasinong.farmerClub.script;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.dao.IStoreDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.store.StoreSource;
import com.dasinong.farmerClub.store.StoreStatus;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.util.WinsafeUtil;




public class ImportBVAAccount {
	
	public static Store generateStore(String cellphone, Long ownerId, String storeName, String contactName,Location loc,String address){
		Store store = new Store();
		store.setCellphone(cellphone);
		store.setName(storeName);
		store.setLocation(loc);
		store.setStreetAndNumber(address);
		store.setOwnerId(ownerId);
		store.setType(15);
		store.setCreatedAt(new Timestamp((new Date()).getTime()));
		store.setUpdatedAt(new Timestamp((new Date()).getTime()));
		store.setStatus(StoreStatus.ACCEPTED);
		store.setSource(StoreSource.IMPORT_BY_BACKEND);
		return store;
	}
	public static User generateBVAUser(String cellPhone,String userName, Long winsafeid){
		User user = new User();
		user.setCellPhone(cellPhone);
		user.setUserName(userName);
		user.setInstitutionId(3L);
		user.setUserType("retailer");
		user.setWinsafeid(winsafeid);
		user.setCreateAt(new Date());
		user.setUpdateAt(new Date());
		user.setLastLogin(new Date());
		return user;
	}
	public static void main(String args[]) throws JsonProcessingException, IOException{
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		
		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		IStoreDao storeDao = (IStoreDao) applicationContext.getBean("storeDao");
		ILocationDao locationDao = (ILocationDao) applicationContext.getBean("locationDao");
		
		Location loc = locationDao.findById(1000000L);
		WinsafeUtil winsafe = new WinsafeUtil();
		String winSafeResult = winsafe.getCustInfo("");
		int totalcount=0;
		int wrongNumbercount=0;
		int successInsert=0;
		int alreadyInsert=0;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(winSafeResult);
		JsonNode returnCode = root.get("returnCode");
		if (returnCode.getIntValue() == 0){
			
			JsonNode returnData = root.get("returnData");
			for( JsonNode jn : returnData){
				
				String cellphone = jn.get("phone").getTextValue();
				totalcount++;
				cellphone = cellphone.replace("-","");
				if (cellphone.length()!=11 || cellphone.charAt(0)!='1'){
					System.out.print(jn.get("contactname").getTextValue()+": ");
					wrongNumbercount++;
					System.out.println(cellphone);
				}
				else{
					try{
			
					User user = generateBVAUser(cellphone,jn.get("contactname").getTextValue(),Long.parseLong(jn.get("custid").getTextValue()));
					if (userDao.findByCellphone(cellphone)!=null){
						user = userDao.findByCellphone(cellphone);
						user.setUserName(jn.get("contactname").getTextValue());
						user.setWinsafeid(Long.parseLong(jn.get("custid").getTextValue()));
						userDao.update(user);
						System.out.println(user.getUserName()+user.getWinsafeid());
						alreadyInsert++;
					}
					else{
						userDao.save(user);
						System.out.println(user.getUserId());
						Store store = generateStore(cellphone,user.getUserId(),jn.get("custname").getTextValue(),
								jn.get("contactname").getTextValue(),loc,jn.get("address").getTextValue());
						storeDao.save(store);
						successInsert++;
					}
					
					}catch(Exception e){
						wrongNumbercount++;
					}
				}
				
				
			};
			System.out.println("total:"+totalcount);
			System.out.println("wrong"+wrongNumbercount);
			System.out.println("successInsert"+successInsert);
			System.out.println("alreadyInsert"+alreadyInsert);
		}
	}
}
