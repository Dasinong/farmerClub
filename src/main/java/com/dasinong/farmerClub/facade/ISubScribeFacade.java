package com.dasinong.farmerClub.facade;

import com.dasinong.farmerClub.model.User;

public interface ISubScribeFacade {

	public abstract Object insertSubScribeList(User user, String targetName, String cellphone, String province,
			String city, String country, String district, double area, Long cropId, boolean isAgriWeather,
			boolean isNatAlter, boolean isRiceHelper);

	public abstract Object insertSubScribeList(User user, String targetName, String cellphone, String province,
			String city, String country, String district, double area, String cropName, boolean isAgriWeather,
			boolean isNatAlter, boolean isRiceHelper);

	public abstract Object getSubScribeLists(User user);

	public abstract Object loadSubScribeList(Long subScribeListId);

	public abstract Object updateSubScribeList(Long subScribeListId, User user, String targetName, String cellphone,
			String province, String city, String country, String district, double area, Long cropId,
			boolean isAgriWeather, boolean isNatAlter, boolean isRiceHelper);

	public abstract Object deleteSubScribeList(Long id);

	public abstract Object updateSubScribeList(Long id, User user, String targetName, String cellphone, String province,
			String city, String country, String district, double area, String cropName, boolean isAgriWeather,
			boolean isNatAlter, boolean isRiceHelper);

}