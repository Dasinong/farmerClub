package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.SystemMessage;

public interface ISystemMessageDao extends IEntityDao<SystemMessage> {

	public List<SystemMessage> findAllValid();
}
