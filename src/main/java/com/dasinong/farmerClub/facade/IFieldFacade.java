package com.dasinong.farmerClub.facade;

import java.util.Date;
import java.util.List;

import com.dasinong.farmerClub.model.NatDis;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.outputWrapper.FieldWrapper;
import com.dasinong.farmerClub.outputWrapper.SubStageWrapper;

public interface IFieldFacade {

	public abstract FieldWrapper createField(User user, String fieldName, Date startDate, boolean isActive,
			boolean seedingortransplant, double area, long locationId, long varietyId, Long currentStageId, Long yield)
					throws Exception;

	Object addWeatherAlert(NatDis natdis);

	public abstract FieldWrapper changeField(Long fieldId, Long currentStageId);

	List<SubStageWrapper> getStages(long varietyId);

}