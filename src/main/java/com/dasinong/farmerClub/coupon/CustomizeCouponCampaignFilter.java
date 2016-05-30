package com.dasinong.farmerClub.coupon;

public class CustomizeCouponCampaignFilter {
	
	public static long[] darenEvent = new long[]{5L,7L,15L};
	public static long[] farmerEvent = new long[]{6L,9L,15L};
	

	public static boolean  isDarenEvent(long input){
		for( long i : darenEvent){
			if (input == i) return true;
		}
		return false;
	}
	
	public static boolean isFarmerEvent(long input){
		for( long i : farmerEvent){
			if (input == i) return true;
		}
		return false;
	}
}
