package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.StepRegion;

public interface IStepRegionDao extends IEntityDao<StepRegion> {

	List<StepRegion> findByStepRegion(String region);

}
