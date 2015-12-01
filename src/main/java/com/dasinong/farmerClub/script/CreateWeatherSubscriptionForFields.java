package com.dasinong.farmerClub.script;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.dao.IFieldDao;
import com.dasinong.farmerClub.dao.IPetDisSpecBrowseDao;
import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.dao.IWeatherSubscriptionDao;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.Field;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.WeatherSubscription;
import com.dasinong.farmerClub.model.WeatherSubscriptionType;

@Transactional
public class CreateWeatherSubscriptionForFields {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
				"file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/ScriptDataSource.xml",
				"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml");

		IPetDisSpecDao dao = (IPetDisSpecDao) applicationContext.getBean("petDisSpecDao");
		IFieldDao fieldDao = (IFieldDao) applicationContext.getBean("fieldDao");
		List<Field> fields = fieldDao.findAll();
		IWeatherSubscriptionDao weatherSubsDao = (IWeatherSubscriptionDao) applicationContext
				.getBean("weatherSubscriptionDao");

		for (Field field : fields) {
			Location loc = field.getLocation();
			WeatherSubscription subs = new WeatherSubscription();
			subs.setLocationId(loc.getLocationId());
			subs.setMonitorLocationId(field.getMonitorLocationId());
			subs.setLocationName(loc.toString());
			subs.setUserId(field.getUser().getUserId());
			subs.setType(WeatherSubscriptionType.FIELD);
			weatherSubsDao.save(subs);
		}
	}
}
