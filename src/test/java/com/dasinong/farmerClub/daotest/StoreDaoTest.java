package com.dasinong.farmerClub.daotest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dasinong.farmerClub.dao.IInstitutionDao;
import com.dasinong.farmerClub.dao.IStoreDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.store.StoreSource;
import com.dasinong.farmerClub.store.StoreStatus;
import com.dasinong.farmerClub.daotest.utils.TestDataUtils;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:./src/main/webapp/WEB-INF/spring/beans/ModelBeans.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/TestDataSource.xml",
		"file:./src/main/webapp/WEB-INF/spring/database/Hibernate.xml" })
public class StoreDaoTest {

	@Autowired
	private IStoreDao storeDao;

	@Autowired
	private IUserDao userDao;

	@Test
	public void testCRUD() {
		// Create a store
		User owner = TestDataUtils.genRandomUser();
		userDao.save(owner);
		Store store = TestDataUtils.genRandomStore(owner.getUserId(), 1L);
		storeDao.save(store);
		Long storeId = store.getId();
		Store savedStore = storeDao.findById(storeId);

		// Assert the first store is created correctly
		Assert.assertEquals(store.getName(), savedStore.getName());
		Assert.assertEquals(store.getDesc(), savedStore.getDesc());
		Assert.assertEquals(store.getOwnerId(), savedStore.getOwnerId());
		Assert.assertEquals(store.getLocationId(), savedStore.getLocationId());
		Assert.assertEquals(store.getStreetAndNumber(), savedStore.getStreetAndNumber());
		Assert.assertEquals(store.getCellphone(), savedStore.getCellphone());
		Assert.assertEquals(store.getLatitude(), savedStore.getLatitude());
		Assert.assertEquals(store.getLongtitude(), savedStore.getLongtitude());
		Assert.assertEquals(store.getPhone(), savedStore.getPhone());
		Assert.assertEquals(store.getCellphone(), savedStore.getCellphone());
		Assert.assertEquals(store.getStatus(), savedStore.getStatus());
		Assert.assertEquals(store.getSource(), savedStore.getSource());
		Assert.assertEquals(store.getType(), savedStore.getType());
		Assert.assertEquals(store.getCreatedAt(), savedStore.getCreatedAt());
		Assert.assertEquals(store.getUpdatedAt(), savedStore.getUpdatedAt());

		// Create another score
		Store anotherStore = TestDataUtils.genRandomStore(owner.getUserId(), 2L);
		storeDao.save(anotherStore);
		Long anotherStoreId = anotherStore.getId();
		List<Store> stores = storeDao.findAll();

		// Assert both can be found
		Assert.assertEquals(stores.size(), 2);
		Assert.assertEquals(stores.get(0).getId(), store.getId());
		Assert.assertEquals(stores.get(1).getId(), anotherStore.getId());

		// Delete store
		storeDao.delete(store);
		storeDao.delete(anotherStore);
		store = storeDao.findById(storeId);
		Assert.assertNull(store);
		store = storeDao.findById(anotherStoreId);
		Assert.assertNull(store);

		// Clean test user
		userDao.delete(owner);
	}
}
