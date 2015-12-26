package com.dasinong.farmerClub.crop;

import com.dasinong.farmerClub.model.Institution;

public class CropsForInstitution {
	
	private static Long[] basfCropIds;
	private static Long[] dowsCropIds;
	private static Long[] yanhuaCropIds;

	public static Long[] getIds(Long instId) {
		if (instId == Institution.BASF) {
			return getIdsForBasf();
		}
		
		if (instId == Institution.DOWS) {
			return getIdsForDows();
		}
		
		if (instId == Institution.YANHUA) {
			return getIdsForYanhua();
		}
		
		return null;
	}
	
	public static Long[] getIdsForBasf() {
		// 香蕉和葡萄
		if (basfCropIds == null) {
			basfCropIds = new Long[2];
			basfCropIds[0] = 34L;
			basfCropIds[1] = 231L;
		}
		
		return basfCropIds;
	}
	
	public static Long[] getIdsForDows() {
		// 小麦和水稻
		if (dowsCropIds == null) {
			dowsCropIds = new Long[2];
			dowsCropIds[0] = 173L;
			dowsCropIds[1] = 223L;
		}
		
		return dowsCropIds;
	}
	
	public static Long[] getIdsForYanhua() {
		if (yanhuaCropIds == null) {
			yanhuaCropIds = new Long[2];
			yanhuaCropIds[0] = 173L;
			yanhuaCropIds[1] = 223L;
		}
		
		return yanhuaCropIds;
	}
}
