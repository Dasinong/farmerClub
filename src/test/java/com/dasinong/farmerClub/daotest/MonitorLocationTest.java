package com.dasinong.farmerClub.daotest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dasinong.farmerClub.dao.IMonitorLocationDao;
import com.dasinong.farmerClub.model.MonitorLocation;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/TestDataSource.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml" })
public class MonitorLocationTest {

	@Autowired
	private IMonitorLocationDao monitorLocationDao;

	@Test
	public void findByCode() {
		MonitorLocation m = monitorLocationDao.findByCode(101040100L);
		Assert.assertEquals(m.getCity(), "重庆");
		Assert.assertEquals(m.getCityDetail(), "重庆,重庆,重庆");
		Assert.assertEquals(m.getCode(), 101040100);
		Assert.assertEquals(m.getId(), 11);
		Assert.assertEquals(m.getLatitude(), 29.56301);
		Assert.assertEquals(m.getLongitude(), 106.551557);
		Assert.assertEquals(m.getPostCode(), 400000);
	}

	@Test
	public void dataCountCheck() {
		@SuppressWarnings("unchecked")
		List<MonitorLocation> monitorLocations = monitorLocationDao.findAll();
		Assert.assertEquals(2027, monitorLocations.size());
	}
}
