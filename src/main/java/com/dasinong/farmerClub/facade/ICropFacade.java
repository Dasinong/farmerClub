package com.dasinong.farmerClub.facade;

import java.util.List;

import com.dasinong.farmerClub.outputWrapper.CropDetailsWrapper;
import com.dasinong.farmerClub.outputWrapper.CropSubscriptionWrapper;

public interface ICropFacade {

	public CropDetailsWrapper getCropDetailsById(long cropId);
	
	public CropDetailsWrapper getCropDetailsByIdAndSubStageId(long cropId, long subStageId);
	
	public List<CropSubscriptionWrapper> getCropSubscriptions(long userId);
}
