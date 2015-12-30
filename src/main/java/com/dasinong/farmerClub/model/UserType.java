package com.dasinong.farmerClub.model;

public class UserType {

	public static final String SALES = "sales"; // 合作公司员工，只有ios版本有使用这个
	public static final String RETAILER = "retailer"; // 农资店店主
	public static final String FARMER = "farmer"; // 种植户
	public static final String OTHERS = "others"; // 其他

	public static boolean isValid(String type) {
		return type.equals(UserType.SALES) || type.equals(UserType.RETAILER) || type.equals(UserType.FARMER) || type.equals(UserType.OTHERS);
	}
	
	public static boolean isSales(User user) {
		return UserType.SALES.equals(user.getUserType());
	}
	
	public static boolean isRetailer(User user) {
		return UserType.RETAILER.equals(user.getUserType());
	}
	
	public static boolean isFarmer(User user) {
		return UserType.FARMER.equals(user.getUserType());
	}
	
	public static boolean isOthers(User user) {
		return UserType.OTHERS.equals(user.getUserType());
	}
}
