package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.CropSubscription;

public class CropSubscriptionDao extends EntityHibernateDao<CropSubscription>implements ICropSubscriptionDao {

	@Override
	public List<CropSubscription> findByUserId(Long userId) {
		return getHibernateTemplate().find("from CropSubscription where userId = ?", userId);
	}

}
