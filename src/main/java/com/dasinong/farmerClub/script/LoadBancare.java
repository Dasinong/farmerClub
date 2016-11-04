package com.dasinong.farmerClub.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dasinong.farmerClub.dao.ICouponDao;
import com.dasinong.farmerClub.dao.ICouponRequestDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Coupon;
import com.dasinong.farmerClub.model.CouponRequest;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.store.StoreSource;
import com.dasinong.farmerClub.store.StoreStatus;

public class LoadBancare {
	public static User generateUser(String name, String cellphone, String address){
		User user = new User();
		user.setCellPhone(cellphone);
		user.setUserName(name);
		user.setAddress(address);
		user.setUserType("farmer");
		user.setInstitutionId(3L);
		user.setChannel("bancare");
		user.setCreateAt(new Date());
		user.setUpdateAt(new Date());
		user.setRegion("海南");
		return user;
	}
	
	public static CouponRequest generateReport(String crop, double area, long userId, String name, String contactNumber,String address){
		CouponRequest cr = new CouponRequest();
		cr.setCrop(crop);
		cr.setArea(area);
		cr.setUserid(userId);
		cr.setName(name);
		cr.setExperience("");
		cr.setProductUseHistory("");
		cr.setContactNumber(contactNumber);
		cr.setAddress(address);
		cr.setPostcode("");
		return cr;
	}
	
	public static void main(String[] args) throws IOException, ParseException{
		boolean dryRun=true;
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
		DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
		IUserDao userDao = (IUserDao) applicationContext.getBean("userDao");
		ICouponRequestDao crDao = (ICouponRequestDao) applicationContext.getBean("couponRequestDao");
		ICouponDao cDao = (ICouponDao) applicationContext.getBean("couponDao");
		String csvFile = "/Users/jiangsean/DasinongData/BANCare.csv";
		
		File file = new File(csvFile);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		line = reader.readLine();
		while ((line = reader.readLine()) != null) {
			String[] units = line.split(",");
			User retailer = userDao.findByCellphone(units[9]);
			if (false){
				if (retailer == null)
				{
					System.out.println("Missing retailer!"+units);
					break;
				}
				//Check whether new user
				User user = userDao.findByCellphone(units[1]);
				if (user==null){
					System.out.println("New user: "+units[0]+ ","+units[1]);
				}
				
				if (user!=null){
					List<Coupon> cps = cDao.findByOwnerId(user.getUserId());
					if (cps.size()>0){
						System.out.println("User: "+units[0]+","+units[1] + " has coupon "+cps.get(0).getCampaign().getId());
					}
				}
				if (!units[5].equals("健达") && !units[5].equals("凯润")){
					System.out.println("Issue with product: " + units[5]);
				}
				try{ 
					df.parse(units[10]);
				}catch(Exception e){
					System.out.println("Issue parsing date: "+ units[10]);
				}
			}else{
				User user = userDao.findByCellphone(units[1]);
				if (user==null){
					System.out.println("New user: "+units[0]+ ","+units[1]);
					user = generateUser(units[0],units[1],units[2]);
					userDao.save(user);
				}
				CouponRequest cr = generateReport(units[3],Double.parseDouble(units[4]),user.getUserId(),user.getUserName(),user.getCellPhone(),units[2]);
				crDao.save(cr);
				
				Coupon cp = cDao.findRandomClaimableCoupon(15);
				if (units[5].equals("凯润")){
					cp.setP1amount(Double.parseDouble(units[7]));
					
				}else if(units[5].equals("健达")){
					cp.setP2amount(Double.parseDouble(units[7]));
				}
				cp.setOwner(user);
				cp.setScanner(retailer);
				cp.setClaimedAt(new Timestamp(df.parse(units[10]).getTime()));
				cp.setRedeemedAt(new Timestamp(df.parse(units[10]).getTime()));
				cDao.update(cp);
			}
			
			
		}
	}
}
