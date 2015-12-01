package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.PetDisSpec;

public interface IPetDisSpecDao extends IEntityDao<PetDisSpec> {

	public abstract PetDisSpec findByPetDisSpecName(String petDisSpecName);

	public abstract PetDisSpec findByNameAndCrop(String petDisSpecName, String cropName);

}