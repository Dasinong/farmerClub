package com.dasinong.farmerClub.facade;

import java.util.List;

import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.FieldWrapper;
import com.dasinong.farmerClub.outputWrapper.SubStageWrapper;

public interface IFieldFacade {

	public abstract FieldWrapper createField(User user, String fieldName, double area, long locationId, long cropId, Long currentStageId)
					throws Exception;

	public abstract FieldWrapper changeField(Long fieldId, Long currentStageId);

	List<SubStageWrapper> getStages(long cropId);
	
	public abstract FieldWrapper findById(long fieldId);

}