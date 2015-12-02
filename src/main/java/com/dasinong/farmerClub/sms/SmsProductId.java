package com.dasinong.farmerClub.sms;

/**
 * 
 * @author xiahonggao
 *
 * 在51welink上，我们有3个productId，808, 812和818
 * - 808用于验证码，无时间限制，支持群发
 * - 812用于营销，发送时间段09:00-18:00，支持群发
 * - 818用于通知，无时间限制，不支持群发
 */
public class SmsProductId {

	public static final String SECURITY_CODE = "1012808";
	
	public static final String USER_MARKETING = "1012812";
	
	public static final String NOTIFICATION = "1012818";
	
	public static boolean canSendMultiple(String productId) {
		if (productId.equals(SECURITY_CODE)) {
			return true;
		} else if (productId.equals(USER_MARKETING)) {
			return true;
		} else {
			return false;
		}
	}
}
