package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.ProStock;

public interface IProStockDao extends IEntityDao<ProStock> {

	List<Object[]> countStockByUserid(long userId);

	ProStock getByBoxcode(String boxcode);

	long computeAuth(long prodId, long userId);

}
