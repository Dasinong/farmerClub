package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.ShortMessageRecord;

public interface IShortMessageRecordDao extends IEntityDao<ShortMessageRecord> {

	public ShortMessageRecord findByExternalId(String externalId);
	
}
