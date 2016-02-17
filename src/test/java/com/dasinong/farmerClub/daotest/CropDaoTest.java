package com.dasinong.farmerClub.daotest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.model.Crop;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/TestDataSource.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml" })
public class CropDaoTest {

	@Autowired
	private ICropDao cropDao;
	

	@Test
	public void findByIds() {
		Long[] ids = new Long[]{19L,48L};
		List<Crop> crops = cropDao.findByIds(ids);
		Assert.assertEquals(crops.size(),2);
	}


}
