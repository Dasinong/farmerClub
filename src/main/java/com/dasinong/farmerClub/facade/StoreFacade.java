package com.dasinong.farmerClub.facade;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ILocationDao;
import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.dao.IStoreDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.exceptions.RequireUserTypeException;
import com.dasinong.farmerClub.exceptions.UserTypeAlreadyDefinedException;
import com.dasinong.farmerClub.model.Location;
import com.dasinong.farmerClub.model.Store;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.UserType;
import com.dasinong.farmerClub.outputWrapper.StoreWrapper;
import com.dasinong.farmerClub.store.StoreSource;
import com.dasinong.farmerClub.store.StoreStatus;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Transactional
public class StoreFacade implements IStoreFacade {

	@Override
	public StoreWrapper createOrUpdate(User user, String name, String desc, Long locationId, String streetAndNumber, Double latitude,
			Double longtitude, String ownerName, String phone, StoreSource source, int type) throws Exception {
		IStoreDao storeDao = (IStoreDao) ContextLoader.getCurrentWebApplicationContext().getBean("storeDao");
		ILocationDao locDao = (ILocationDao) ContextLoader.getCurrentWebApplicationContext().getBean("locationDao");
		Location loc = locDao.findById(locationId);
				
		// If store is added from registration flow, user type must be retailer
		if (source.equals(StoreSource.REGISTRATION) && !UserType.isRetailer(user)) {
			throw new RequireUserTypeException(user.getUserType());
		}

		//Store and user has one-one relationship. Judge new store by ownerId instead of storeId.
		Store store = storeDao.getByOwnerId(user.getUserId());
		boolean isNew = false;
		if (store==null){
			store = new Store();
			isNew=true;
		}
		
		store.setName(name);
		store.setDesc(desc);
		store.setLocation(loc);
		store.setStreetAndNumber(streetAndNumber);
		store.setContactName(ownerName);
		store.setOwnerId(user.getUserId());
		store.setPhone(phone);
		store.setLatitude(latitude);
		store.setLongtitude(longtitude);
		store.setType(type);
		store.setSource(StoreSource.REGISTRATION);
		store.setStatus(StoreStatus.PENDING);
		if (isNew){
			store.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		}
		store.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		
		if (isNew){
			storeDao.save(store);
		}
		else{
			storeDao.update(store);
		}
	
		StoreWrapper sw = new StoreWrapper(store);
		return sw;
	}
	
	
	@Override
	public StoreWrapper get(User user) throws Exception {
		IStoreDao storeDao = (IStoreDao) ContextLoader.getCurrentWebApplicationContext().getBean("storeDao");
		Store store = storeDao.getByOwnerId(user.getUserId());
		if (store==null) return null;
		else{
			StoreWrapper sw = new StoreWrapper(store);
			return sw;
		}
	}
}
