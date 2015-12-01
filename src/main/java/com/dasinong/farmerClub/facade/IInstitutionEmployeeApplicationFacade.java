package com.dasinong.farmerClub.facade;

import com.dasinong.farmerClub.model.User;

public interface IInstitutionEmployeeApplicationFacade {

	public Object create(User user, String contactName, String cellphone, String code, String title, String region) throws Exception;
}
