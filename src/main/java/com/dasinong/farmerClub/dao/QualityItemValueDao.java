package com.dasinong.farmerClub.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dasinong.farmerClub.model.QualityItemValue;

public class QualityItemValueDao extends EntityHibernateDao<QualityItemValue>implements IQualityItemValueDao {

	@Override
	public Map<Long, String> findByVarietyId(Long varietyId) {
		Map<Long, String> result = new HashMap<Long, String>();
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from QualityItemValue where varietyId=?", varietyId);
		if (list == null || list.isEmpty()) {
			return null;
		}
		for (Object i : list) {
			QualityItemValue qiv = (QualityItemValue) i;
			result.put(qiv.getQualityItemId(), qiv.getItemValue());
		}
		return result;
	}

}
