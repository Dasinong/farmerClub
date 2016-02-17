package com.dasinong.farmerClub.crop;

public class CropsWithSubstage {

	private static Long[] cropIds = null;
	
	public static Long[] getIds() {
		if (cropIds == null) {
			cropIds = new Long[11];
			cropIds[0] = 19L;
			cropIds[1] = 34L;
			cropIds[2] = 48L;
			cropIds[3] = 78L;
			cropIds[4] = 127L;
			cropIds[5] = 148L;
			cropIds[6] = 173L;
			cropIds[7] = 202L;
			cropIds[8] = 223L;
			cropIds[9] = 230L;
			cropIds[10] = 231L;
		}
		
		return cropIds;
	}
}
