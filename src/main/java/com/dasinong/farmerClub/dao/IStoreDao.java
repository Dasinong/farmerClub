package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.Store;

public interface IStoreDao extends IEntityDao<Store> {

	Store getByOwnerId(Long ownerId);

}
