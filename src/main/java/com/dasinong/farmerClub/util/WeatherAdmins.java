package com.dasinong.farmerClub.util;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdmins {

	private static List<String> subscribers = null;
	
	public static List<String> getSubscribers() {
		if (subscribers == null) {
			subscribers = new ArrayList<String>();
			subscribers.add("15311733826");
			subscribers.add("13162881998");
			subscribers.add("13137736397");
		}
		
		return subscribers;
	}
}
