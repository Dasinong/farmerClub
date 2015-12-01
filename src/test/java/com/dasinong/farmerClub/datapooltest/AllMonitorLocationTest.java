package com.dasinong.farmerClub.datapooltest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dasinong.farmerClub.dao.IMonitorLocationDao;
import com.dasinong.farmerClub.datapool.AllMonitorLocation;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/TestDataSource.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml" })

public class AllMonitorLocationTest {
	@Autowired
	private IMonitorLocationDao monitorLocationDao;

	@Test
	public void getNearest() {
		AllMonitorLocation.getInstance(monitorLocationDao);
		long code = AllMonitorLocation.getInstance().getNearest(39, 116);
		Assert.assertEquals(101090211, code);
		code = AllMonitorLocation.getInstance().getNearest(31.2, 121.5);
		Assert.assertEquals(101020100, code);
		code = AllMonitorLocation.getInstance().getNearest(39.9, 116.3);
		Assert.assertEquals(101010100, code);
	}

}
