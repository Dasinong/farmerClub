package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.Institution;

public interface IInstitutionDao extends IEntityDao<Institution> {

	Institution findByName(String name);

	Institution findByCode(String code);

}