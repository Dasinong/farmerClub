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
		Store store = storeDao.findById(16L);
		stores.add(store);
		
		CouponCampaign campaign = (new CouponCampaignMutator(couponDao, campaignDao))
				.setName("新活动")
				.setDescription("测试巴斯夫")
				.setInstitution(inst)
				.setType(CouponCampaignType.CASH)
				.setVolume(50)
				.setAmount(1)
				.setRetailerStores(stores)
				.setRedeemTimeStart(Timestamp.valueOf("2016-01-01 00:00:00"))
				.setRedeemTimeEnd(Timestamp.valueOf("2016-04-30 00:00:00"))
				.setClaimTimeStart(Timestamp.valueOf("2016-01-01 00:00:00"))
				.setClaimTimeEnd(Timestamp.valueOf("2016-02-28 00:00:00"))
				.save();
	}
}
