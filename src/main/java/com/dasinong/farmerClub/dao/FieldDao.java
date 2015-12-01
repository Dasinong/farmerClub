package com.dasinong.farmerClub.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.Field;

public class FieldDao extends EntityHibernateDao<Field>implements IFieldDao {

	@Override
	public Field findByFieldName(String fieldName) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from Field where fieldName=?", fieldName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (Field) list.get(0);
	}

}
