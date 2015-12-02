package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.ShortMessageRecord;

public class ShortMessageRecordDao extends EntityHibernateDao<ShortMessageRecord> implements IShortMessageRecordDao {

	@Override
	public ShortMessageRecord findByExternalId(String externalId) {
		List list = getHibernateTemplate().find("from ShortMessageRecord where externalId=?", externalId);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return (ShortMessageRecord) list.get(0);
	}

}
