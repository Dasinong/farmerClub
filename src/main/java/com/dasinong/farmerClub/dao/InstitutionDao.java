package com.dasinong.farmerClub.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.Institution;

public class InstitutionDao extends EntityHibernateDao<Institution>implements IInstitutionDao {

	@Override
	public Institution findByName(String name) {
		List list = getHibernateTemplate().find("from Institution where name=?", name);
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Institution) list.get(0);
	}

	@Override
	public Institution findByCode(String code) {
		List list = getHibernateTemplate().find("from Institution where code=?", code);
		if (list == null || list.size() == 0) {
			return null;
		}
		return (Institution) list.get(0);
	}
}
