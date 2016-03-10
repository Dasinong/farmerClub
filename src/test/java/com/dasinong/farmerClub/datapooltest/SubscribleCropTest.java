package com.dasinong.farmerClub.datapooltest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dasinong.farmerClub.crop.CropsForInstitution;
import com.dasinong.farmerClub.crop.CropsWithSubstage;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/TestDataSource.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml" })

public class SubscribleCropTest {
	
	@Test
	public void  cropsForInstitution() {
		Long[] ids = CropsForInstitution.getIds(1L);
		Assert.assertEquals(ids.length,2);
		ids = CropsForInstitution.getIds(2L);
		Assert.assertEquals(ids.length,2);
		ids = CropsForInstitution.getIds(3L);
		Assert.assertEquals(ids.length,7);
		ids = CropsForInstitution.getIds(4L);
		Assert.assertEquals(ids.length,2);
	}
	
	@Test
	public void  cropsWithStage() {
		Long[] ids = CropsWithSubstage.getIds();
		Assert.assertEquals(ids.length,9);
	}
}
