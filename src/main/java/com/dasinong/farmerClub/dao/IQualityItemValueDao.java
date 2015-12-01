package com.dasinong.farmerClub.dao;

import java.util.Map;

import com.dasinong.farmerClub.model.QualityItemValue;

public interface IQualityItemValueDao extends IEntityDao<QualityItemValue> {

	public abstract Map<Long, String> findByVarietyId(Long varietyId);

}