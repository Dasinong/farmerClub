package com.dasinong.farmerClub.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.Crop;

public class CropDao extends EntityHibernateDao<Crop>implements ICropDao {

	@Override
	public Crop findByCropName(String cropName) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from Crop where cropName=?", cropName);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (Crop) list.get(0);
	}

	@Override
	public List<Crop> findByType(String type) {
		List list = getHibernateTemplate().find(
				// "from Crop where type=?",type);
				"from Crop where type like '%" + type + "%'");
		if (list == null || list.isEmpty()) {
			return new ArrayList<Crop>();
		}
		return list;
	}

}
