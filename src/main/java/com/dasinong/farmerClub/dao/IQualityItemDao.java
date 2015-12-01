package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.QualityItem;

public interface IQualityItemDao extends IEntityDao<QualityItem> {

	public abstract QualityItem findByQualityItemName(String qualityItemName);

}