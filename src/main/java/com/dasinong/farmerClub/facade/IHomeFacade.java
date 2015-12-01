package com.dasinong.farmerClub.facade;

import org.springframework.transaction.annotation.Transactional;

import com.dasinong.farmerClub.model.User;

public interface IHomeFacade {

	@Transactional

	public abstract Object LoadHome(double lat, double lon);

	Object LoadHome(User user, Long fieldId, int taskType);

}