package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.PetDis;

public interface IPetDisDao extends IEntityDao<PetDis> {

	public abstract PetDis findByPetDisName(String petDisName);

}