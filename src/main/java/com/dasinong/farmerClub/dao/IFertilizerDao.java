package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.Fertilizer;

public interface IFertilizerDao extends IEntityDao<Fertilizer> {

	public abstract List<Fertilizer> findByGeneralName(String generalName);

}