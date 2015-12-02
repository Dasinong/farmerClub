package com.dasinong.farmerClub.util;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdmins {

	private static String[] subscribers = null;
	
	public static String[] getSubscribers() {
		if (subscribers == null) {
			subscribers = new String[3];
			subscribers[0] = "15311733826";
			subscribers[1] = "13162881998";
			subscribers[2] = "13137736397";
		}
		
		return subscribers;
	}
}
