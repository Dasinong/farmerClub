package com.dasinong.farmerClub.facade;

import com.dasinong.farmerClub.model.User;

public interface ILaoNongFacade {
	
	public abstract Object getLaoNongs_DEPRECATED(double lat, double lon, User user);

	public abstract Object getLaoNongs_DEPRECATED(Long areaId, User user);

	public abstract Object getLaoNongs(double lat, double lon, User user);

	public abstract Object getLaoNongs(Long areaId, User user);

}
