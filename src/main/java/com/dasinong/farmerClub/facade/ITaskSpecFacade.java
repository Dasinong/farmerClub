package com.dasinong.farmerClub.facade;

import java.util.List;

import com.dasinong.farmerClub.outputWrapper.StepWrapper;

public interface ITaskSpecFacade {

	public abstract Object getTaskSpec(Long taskSpecId);

	List<StepWrapper> getSteps(Long taskSpecId, Long fieldId);

}