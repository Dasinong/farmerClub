package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.CPProduct;

public interface ICPProductDao extends IEntityDao<CPProduct> {

	public abstract CPProduct findByRegisterationId(String registerationId);

	List<CPProduct> findByIngredient(String ingredient);

}