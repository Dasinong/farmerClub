package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.RetailerStore;

public interface IRetailerStoreDao extends IEntityDao<RetailerStore> {

	List<RetailerStore> findByOwnerId(long ownerId);
}
