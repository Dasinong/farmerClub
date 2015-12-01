package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.VarietyBrowse;

public interface IVarietyBrowseDao extends IEntityDao<VarietyBrowse> {

	VarietyBrowse findByVarietyBrowseName(String varietyBrowseName);

	List<VarietyBrowse> findByCropId(Long id);

}
