package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.TaskRegion;

public interface ITaskRegionDao extends IEntityDao<TaskRegion> {

	List<TaskRegion> findByTaskRegion(String region);

}
