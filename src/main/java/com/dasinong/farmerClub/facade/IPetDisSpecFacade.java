package com.dasinong.farmerClub.facade;

public interface IPetDisSpecFacade {

	Object getPetDisBySubStage(Long subStageId, Long varietyId);

	Object getPetDisDetail(Long petDisSpecId);

}