package com.dasinong.farmerClub.dao;

import java.util.ArrayList;
import java.util.List;

import com.dasinong.farmerClub.model.RetailerStore;

public class RetailerStoreDao extends EntityHibernateDao<RetailerStore> implements IRetailerStoreDao {

	@Override
	public List<RetailerStore> findByOwnerId(long ownerId) {
		List<RetailerStore> stores = this.getHibernateTemplate().find("from RetailerStore where ownerId = ?", ownerId);
		if (stores == null || stores.size() == 0) {
			return new ArrayList<RetailerStore>();
		}
		return stores;
	}

}
