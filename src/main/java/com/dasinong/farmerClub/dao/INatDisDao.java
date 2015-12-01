package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.NatDis;

public interface INatDisDao extends IEntityDao<NatDis> {

	public abstract NatDis findByNatDisName(String natDisName);

}