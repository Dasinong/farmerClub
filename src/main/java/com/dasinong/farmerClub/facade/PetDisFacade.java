package com.dasinong.farmerClub.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.dao.ISubStageDao;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.SubStage;
import com.dasinong.farmerClub.outputWrapper.PetDisSpecWrapper;

@Transactional
public class PetDisFacade implements IPetDisFacade {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.IPetDisFacade#getPetDisByLocation(java.
	 * lang.Long)
	 */
	@Override
	public Object getPetDisByLocation(Long locationId) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		// TODO: depends on region field content.
		return result;
	}

}
