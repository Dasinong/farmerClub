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
		Institution inst = instDao.findById(1L);
		List<Store> stores = new ArrayList<Store>();
		Store store = storeDao.findById(23L);
		stores.add(store);
		
		CouponCampaign campaign = (new CouponCampaignMutator(couponDao, campaignDao))
				.setName("俱乐部用户回馈")
				.setDescription("俱乐部用户回馈抽奖活动。凡使用过陶氏代金券的用户都可以参加。")
				.setInstitution(inst)
				.setType(CouponCampaignType.CASH)
				.setVolume(1500)
				.setAmount(1)
				.setRetailerStores(stores)
				.setRedeemTimeStart(Timestamp.valueOf("2016-09-30 00:00:00"))
				.setRedeemTimeEnd(Timestamp.valueOf("2016-11-30 00:00:00"))
				.setClaimTimeStart(Timestamp.valueOf("2016-09-15 00:00:00"))
				.setClaimTimeEnd(Timestamp.valueOf("2016-11-15 00:00:00"))
				.setPictureUrl("sample.png;")
				.save();
	}
}
