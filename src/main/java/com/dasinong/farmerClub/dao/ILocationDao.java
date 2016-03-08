package com.dasinong.farmerClub.dao;

import java.util.List;

import com.dasinong.farmerClub.model.Location;

public interface ILocationDao extends IEntityDao<Location> {

	public abstract Location findByLocationName(String locationName);

	public abstract List getIdList(String province, String city, String country, String district);

	public abstract List<Location> findLocationsInRange(double lat, double lon, double range);

	public abstract List<Location> findEmptyLocations();

	List<Location> findLocationNear(String province, String city, String country);
}