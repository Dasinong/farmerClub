package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.PetDisSpecBrowse;

public interface IPetDisSpecBrowseDao extends IEntityDao<PetDisSpecBrowse> {

	public abstract List<PetDisSpecBrowse> findByType(String type);

	public abstract List<PetDisSpecBrowse> findByCropIdAndType(Long cropId, String type);

}