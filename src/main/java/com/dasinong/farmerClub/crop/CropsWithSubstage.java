package com.dasinong.farmerClub.crop;

public class CropsWithSubstage {

	private static Long[] cropIds = null;
	
	public static Long[] getIds() {
		if (cropIds == null) {
			cropIds = new Long[5];
			cropIds[0] = 223L;
			cropIds[1] = 173L;
			cropIds[2] = 231L;
			cropIds[3] = 78L;
			cropIds[4] = 34L;
		}
		
		return cropIds;
	}
}
