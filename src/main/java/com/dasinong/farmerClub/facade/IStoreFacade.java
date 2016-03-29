package com.dasinong.farmerClub.facade;

import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.StoreWrapper;
import com.dasinong.farmerClub.store.StoreSource;

public interface IStoreFacade {
	
	StoreWrapper get(User user) throws Exception;
	
	StoreWrapper createOrUpdate(User user, String name, String desc, Long locationId, String streetAndNumber,
			Double latitude, Double longtitude, String ownerName, String phone, StoreSource source, int type)
					throws Exception;
}
