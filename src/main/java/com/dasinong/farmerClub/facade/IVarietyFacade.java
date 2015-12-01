package com.dasinong.farmerClub.facade;

public interface IVarietyFacade {

	public abstract Object getVariety(String cropName, long locationId);

	public abstract Object getVariety(String cropName, String province);

	public abstract Object getVariety(long cropId, long locationId);

	public abstract Object getVariety(long cropId, String province);

	Object getNormalVariety(long cropId);

}