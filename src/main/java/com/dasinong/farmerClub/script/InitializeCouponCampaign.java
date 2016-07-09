package com.dasinong.farmerClub.script;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.coupon.CouponCampaignMutator;
import com.dasinong.farmerClub.coupon.CouponCampaignType;
import com.dasinong.farmerClub.dao.ICouponCampaignDao;
import com.dasinong.farmerClub.dao.ICouponDao;
import com.dasinong.farmerClub.dao.IInstitutionDao;
import com.dasinong.farmerClub.dao.IStoreDao;
import com.dasinong.farmerClub.model.CouponCampaign;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.Store;

/**
 * 
 * @author xiahonggao
 *
 * 如果有新的coupon campaign，可以修改这个script来后台导入数据
 */
public class InitializeCouponCampaign {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");
	
		IInstitutionDao instDao = (IInstitutionDao) applicationContext.getBean("institutionDao");
		ICouponDao couponDao = (ICouponDao) applicationContext.getBean("couponDao");
		ICouponCampaignDao campaignDao = (ICouponCampaignDao) applicationContext.getBean("couponCampaignDao");
		IStoreDao storeDao = (IStoreDao) applicationContext.getBean("storeDao");
		Institution inst = instDao.findById(3L);
		List<Store> stores = new ArrayList<Store>();
		Store store = storeDao.findById(23L);
		stores.add(store);
		
		CouponCampaign campaign = (new CouponCampaignMutator(couponDao, campaignDao))
				.setName("陶氏产品代金卷")
				.setDescription("当地发生14级以上的台风，会根据香港天文台气象（http://www.hko.gov.hk/contentc.htm）记录，由系统发送领取券给台风影响区域内的参与关爱基金的农户。")
				.setInstitution(inst)
				.setType(CouponCampaignType.CASH)
				.setVolume(5000)
				.setAmount(5)
				.setRetailerStores(stores)
				.setRedeemTimeStart(Timestamp.valueOf("2016-05-20 00:00:00"))
				.setRedeemTimeEnd(Timestamp.valueOf("2016-08-20 00:00:00"))
				.setClaimTimeStart(Timestamp.valueOf("2016-05-30 00:00:00"))
				.setClaimTimeEnd(Timestamp.valueOf("2016-08-30 00:00:00"))
				.setPictureUrl("event.png;")
				.save();
	}
}
