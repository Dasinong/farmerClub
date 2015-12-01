package com.dasinong.farmerClub.model;

public class UserType {

	public static final String SALES = "sales"; // 合作公司员工
	public static final String RETAILER = "retailer"; // 农资店店主
	public static final String FARMER = "farmer"; // 种植户

	public static boolean isValid(String type) {
		return type.equals(UserType.SALES) || type.equals(UserType.RETAILER) || type.equals(UserType.FARMER);
	}
}
