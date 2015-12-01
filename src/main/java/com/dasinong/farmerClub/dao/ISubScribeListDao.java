package com.dasinong.farmerClub.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.dasinong.farmerClub.model.SubScribeList;

public interface ISubScribeListDao extends IEntityDao<SubScribeList> {

	public abstract List<SubScribeList> findByOwnerId(Long oid);

}