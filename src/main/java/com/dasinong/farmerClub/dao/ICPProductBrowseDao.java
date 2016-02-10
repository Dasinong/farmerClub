package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.CPProductBrowse;

public interface ICPProductBrowseDao extends IEntityDao<CPProductBrowse> {

	public abstract List<CPProductBrowse> findByModel(String model);

	public abstract List<CPProductBrowse> findByModelAndManufacturer(String model, String manufacturer);

}