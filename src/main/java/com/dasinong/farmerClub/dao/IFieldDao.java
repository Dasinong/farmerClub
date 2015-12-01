package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.Field;

public interface IFieldDao extends IEntityDao<Field> {

	public abstract Field findByFieldName(String fieldName);

}