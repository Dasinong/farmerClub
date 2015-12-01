package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.PetSolu;

public interface IPetSoluDao extends IEntityDao<PetSolu> {

	public abstract PetSolu findByPetSoluName(String petSoluName);

}