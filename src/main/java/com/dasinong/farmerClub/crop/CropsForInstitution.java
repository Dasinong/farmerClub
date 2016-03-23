package com.dasinong.farmerClub.crop;

import com.dasinong.farmerClub.model.Institution;

public class CropsForInstitution {
	
	private static Long[] basfCropIds;
	private static Long[] dowsCropIds;
	private static Long[] yanhuaCropIds;
	private static Long[] zhongnongCropIds;

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
		
		if (instId == Institution.ZHNO) {
			return getIdsForZhongNong();
		}
		
		return null;
	}
	
	public static Long[] getIdsForBasf() {
		// 香蕉和葡萄
		if (basfCropIds == null) {
			basfCropIds = new Long[7];
			basfCropIds[0] = 34L;
			basfCropIds[1] = 231L;
			basfCropIds[2] = 78L;
			basfCropIds[3] = 19L;
			basfCropIds[4] = 48L;
			basfCropIds[5] = 148L;
			basfCropIds[6] = 202L;
		}
		
		return basfCropIds;
	}
	
	public static Long[] getIdsForZhongNong() {
		// 玉米
		if (zhongnongCropIds == null) {
			zhongnongCropIds = new Long[3];
			zhongnongCropIds[0] = 270L;
			zhongnongCropIds[1] = 223L;
			zhongnongCropIds[2] = 331L;
		}
		
		return zhongnongCropIds;
	}
	
	public static Long[] getIdsForDows() {
		// 小麦和水稻
		if (dowsCropIds == null) {
			dowsCropIds = new Long[3];
			dowsCropIds[0] = 173L;
			dowsCropIds[1] = 223L;
			dowsCropIds[2] = 331L;
		}
		
		return dowsCropIds;
	}
	
	public static Long[] getIdsForYanhua() {
		// 小麦和水稻
		if (yanhuaCropIds == null) {
			yanhuaCropIds = new Long[3];
			yanhuaCropIds[0] = 173L;
			yanhuaCropIds[1] = 223L;
			yanhuaCropIds[2] = 331L;
		}
		
		return yanhuaCropIds;
	}
}
