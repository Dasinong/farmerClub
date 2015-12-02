package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.CropSubscription;

public interface ICropSubscriptionDao extends IEntityDao<CropSubscription> {

	public abstract List<CropSubscription> findByUserId(Long userId);
	
}
