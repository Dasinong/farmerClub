package com.dasinong.farmerClub.util;

import java.util.List;

import com.dasinong.farmerClub.model.Location;

public class GeoUtil {

	List<Location> ls;

	public GeoUtil(List<Location> ls) {
		this.ls = ls;
	}

	public Location getNearLoc(double lat, double lon) {
		Location nearest = null;
		double distance = 1000;
		for (Location l : ls) {
			double ndistance = Math.pow(lat - l.getLatitude(), 2) + Math.pow(lat - l.getLatitude(), 2);
			if (ndistance < distance) {
				nearest = l;
				distance = ndistance;
			}
		}
		return nearest;
	}

}
