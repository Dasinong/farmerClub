package com.dasinong.farmerClub.store;

public class StoreType {
	public static final int SEED = 0x00000001;
	public static final int FERTILIZER = 0x00000002;
	public static final int CPPRODUCT = 0x00000004;
	public static final int WHOLESALE = 0x00000008;

	public static boolean hasType(int type, int targetType) {
		return (type & targetType) > 0;
	}
}