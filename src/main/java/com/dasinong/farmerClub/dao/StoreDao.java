package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.Store;

public class StoreDao extends EntityHibernateDao<Store>implements IStoreDao {
	
	@Override
	public Store getByOwnerId(Long ownerId){
		List<Store> list = getHibernateTemplate().find("from Store where ownerId=?", ownerId);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}
