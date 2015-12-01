package com.dasinong.farmerClub.laonong;

import java.util.List;

import com.dasinong.farmerClub.model.User;

public interface ILaoNongDataSource {

	public List<LaoNong> genLaoNongs(User user, Long areaId);
	
}
