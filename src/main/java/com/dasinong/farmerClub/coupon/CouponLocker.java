package com.dasinong.farmerClub.coupon;

import java.util.HashMap;

public class CouponLocker {

	private static Object lock = new Object();
	private static HashMap<Long, Boolean> records = new HashMap<Long, Boolean>();
	
	public static boolean tryLock(Long couponId) {
		synchronized (lock) {
			if (records.containsKey(couponId)) {
				return false;
			}
			
			records.put(couponId, true);
			return true;
		}
	}
	
	public static void unlock(Long couponId) {
		synchronized (lock) {
			records.remove(couponId);
		}
	}
}
