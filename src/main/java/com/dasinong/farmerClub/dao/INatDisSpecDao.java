package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.NatDisSpec;

public interface INatDisSpecDao extends IEntityDao<NatDisSpec> {

	public abstract NatDisSpec findByNatDisSpecName(String natDisSpecName);

}