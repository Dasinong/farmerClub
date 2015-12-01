package com.dasinong.farmerClub.dao;

import com.dasinong.farmerClub.model.SubStage;

public interface ISubStageDao extends IEntityDao<SubStage> {

	public abstract SubStage findBySubStageName(String subStageName);

}