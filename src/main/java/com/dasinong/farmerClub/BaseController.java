package com.dasinong.farmerClub;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.coupon.exceptions.CampaignNotInClaimRangeException;
import com.dasinong.farmerClub.coupon.exceptions.CampaignNotInRedeemRangeException;
import com.dasinong.farmerClub.coupon.exceptions.CanNotClaimMultipleCouponException;
import com.dasinong.farmerClub.coupon.exceptions.CouponAlreadyRedeemedException;
import com.dasinong.farmerClub.coupon.exceptions.NoMoreAvailableCouponException;
import com.dasinong.farmerClub.coupon.exceptions.NotAuthorizedToScanCouponException;
import com.dasinong.farmerClub.coupon.exceptions.CanNotRedeemOthersCouponException;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.exceptions.GenerateAppAccessTokenException;
import com.dasinong.farmerClub.exceptions.GenerateUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.InvalidAppAccessTokenException;
import com.dasinong.farmerClub.exceptions.InvalidParameterException;
import com.dasinong.farmerClub.exceptions.InvalidUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.MissingParameterException;
import com.dasinong.farmerClub.exceptions.MultipleUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.RequireUserTypeException;
import com.dasinong.farmerClub.exceptions.ResourceNotFoundException;
import com.dasinong.farmerClub.exceptions.SalesPeopleCannotBeReferredException;
import com.dasinong.farmerClub.exceptions.UserIsNotLoggedInException;
import com.dasinong.farmerClub.exceptions.UserNotFoundInSessionException;
import com.dasinong.farmerClub.exceptions.UserTypeAlreadyDefinedException;
import com.dasinong.farmerClub.exceptions.ViewerContextNotInitializedException;
import com.dasinong.farmerClub.exceptions.WeatherAlreadySubscribedException;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.viewerContext.ViewerContext;

public class BaseController {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	public ViewerContext getViewerContext(HttpServletRequest request) throws Exception {
		ViewerContext vc = (ViewerContext) request.getAttribute(ViewerContext.REQUEST_KEY);
		if (vc == null) {
			throw new ViewerContextNotInitializedException();
		}
		return vc;
	}

	public User getLoginUser(HttpServletRequest request) throws Exception {
		ViewerContext vc = this.getViewerContext(request);
		if (!vc.isUserLogin()) {
			return null;
		}

		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
		return userDao.findById(vc.getUserId());
	}

	/**
	 * Range 100 - 200 is reserved for session/token
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(UserNotFoundInSessionException.class)
	@ResponseBody
	public Object handleUserNotFoundError(HttpServletRequest req, UserNotFoundInSessionException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 100);
		result.put("message", "用户未找到");
		return result;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(UserIsNotLoggedInException.class)
	@ResponseBody
	public Object handleUserIsNotLoggedInException(HttpServletRequest req, UserIsNotLoggedInException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 100);
		result.put("message", "用户没有登录");
		return result;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(InvalidAppAccessTokenException.class)
	@ResponseBody
	public Object handleInvalidAppAccessTokenException(HttpServletRequest req,
			InvalidAppAccessTokenException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 110);
		result.put("message", "不合法的App Access Token");
		return result;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(GenerateAppAccessTokenException.class)
	@ResponseBody
	public Object handleGenerateAppAccessTokenException(HttpServletRequest req,
			GenerateAppAccessTokenException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 111);
		result.put("message", "无法产生App Access Token (appId=" + exception.getAppId() + ")");
		return result;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(InvalidUserAccessTokenException.class)
	@ResponseBody
	public Object handleInvalidUserAccessTokenException(HttpServletRequest req,
			InvalidUserAccessTokenException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> errorData = new HashMap<String, Object>();
		errorData.put("accessToken", exception.getToken());
		result.put("respCode", 120);
		result.put("message", "不合法的User Access Token");
		result.put("data", errorData);
		return result;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(MultipleUserAccessTokenException.class)
	@ResponseBody
	public Object handleMultipleUserAccessTokenException(HttpServletRequest req,
			MultipleUserAccessTokenException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 121);
		result.put("message", "多个User Access Token");
		return result;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(GenerateUserAccessTokenException.class)
	@ResponseBody
	public Object handleGenerateUserAccessTokenException(HttpServletRequest req,
			GenerateUserAccessTokenException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 122);
		result.put("message", "无法生成UserAccessToken");
		return result;
	}

	/**
	 * Range 300 - 400 is reserved for parameter validation
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(MissingParameterException.class)
	@ResponseBody
	public Object hanleMissingParameterError(HttpServletRequest req, MissingParameterException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> errorData = new HashMap<String, Object>();
		errorData.put("name", exception.getParamName());
		result.put("respCode", 300);
		result.put("message", "参数缺失");
		result.put("data", errorData);
		return result;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(InvalidParameterException.class)
	@ResponseBody
	public Object hanleInvalidParameterException(HttpServletRequest req, InvalidParameterException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> errorData = exception.getParams();
		result.put("respCode", 301);
		result.put("message", "参数不正确");
		result.put("data", errorData);
		return result;
	}

	/**
	 * range 400 - 499 is reserved for resource errors 500 is reversed for
	 * unknown server error
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseBody
	public Object handleResourceNotFound(HttpServletRequest req, ResourceNotFoundException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> errorData = new HashMap<String, Object>();
		errorData.put("resourceType", exception.getResourceType());
		errorData.put("resourceId", exception.getResourceId());
		result.put("respCode", 404);
		result.put("message", "请求的资源不存在");
		result.put("data", errorData);
		return result;
	}

	/**
	 * range 600 - 699 is reserved for user profile errors
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(UserTypeAlreadyDefinedException.class)
	@ResponseBody
	public Object handleUserTypeAlreadDefinedException(HttpServletRequest req, UserTypeAlreadyDefinedException ex) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> errorData = new HashMap<String, Object>();
		errorData.put("userType", ex.getUserType());
		errorData.put("userId", ex.getUserId());
		result.put("respCode", 600);
		result.put("message", "用户的类型已经设置了");
		result.put("data", errorData);
		return result;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(RequireUserTypeException.class)
	@ResponseBody
	public Object handleRequireUserTypeException(HttpServletRequest req, RequireUserTypeException ex) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> errorData = new HashMap<String, Object>();
		errorData.put("userType", ex.getUserType());
		result.put("respCode", 601);
		result.put("message", "用户的类型必须是" + ex.getUserType());
		result.put("data", errorData);
		return result;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(SalesPeopleCannotBeReferredException.class)
	@ResponseBody
	public Object handleSalesPeopleCannotBeReferredException(HttpServletRequest req, RequireUserTypeException ex) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> errorData = new HashMap<String, Object>();
		result.put("respCode", 602);
		result.put("message", "合作公司的员工不能被推荐");
		result.put("data", errorData);
		return result;
	}

	/**
	 * Range 1000 - 1100 is reserved for database exception
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(DataAccessException.class)
	@ResponseBody
	public Object handleDatabaseError(HttpServletRequest req, DataAccessException exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> errorData = new HashMap<String, Object>();
		errorData.put("class", exception.getClass());
		errorData.put("stacktrace", exception.getStackTrace());
		result.put("respCode", 1000);
		result.put("message", "数据库访问错误");
		result.put("data", errorData);
		return result;
	}

	/**
	 * Range 1100 - 1200 is reserved for weather subscription
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(WeatherAlreadySubscribedException.class)
	@ResponseBody
	public Object handleWeatherAlreadySubscribed(HttpServletRequest req, Exception exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 1100);
		result.put("message", "已经关注了该地区得天气");
		return result;
	}
	
	/**
	 * Range 2000 - 2500 is reserved for coupon
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(CanNotClaimMultipleCouponException.class)
	@ResponseBody
	public Object handleCanNotClaimMultipleCouponException(HttpServletRequest req, Exception exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 2001);
		result.put("message", "已经认领过Coupon了");
		return result;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(CampaignNotInClaimRangeException.class)
	@ResponseBody
	public Object handleCampaignNotInClaimRangeException(HttpServletRequest req, Exception exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 2002);
		result.put("message", "不在优惠券认领时间");
		return result;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(NoMoreAvailableCouponException.class)
	@ResponseBody
	public Object handleNoMoreAvailableCouponException(HttpServletRequest req, Exception exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 2003);
		result.put("message", "该活动的优惠卷已领完了");
		return result;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(CouponAlreadyRedeemedException.class)
	@ResponseBody
	public Object handleCouponAlreadyRedeemedException(HttpServletRequest req, Exception exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 2101);
		result.put("message", "优惠券已被认领了");
		return result;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(CampaignNotInRedeemRangeException.class)
	@ResponseBody
	public Object handleCampaignNotInRedeemRangeException(HttpServletRequest req, Exception exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 2102);
		result.put("message", "不在优惠券的兑换时间");
		return result;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(NotAuthorizedToScanCouponException.class)
	@ResponseBody
	public Object handleNotAuthorizedToScanCouponException(HttpServletRequest req, Exception exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 2103);
		result.put("message", "未授权扫描该优惠券");
		return result;
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(CanNotRedeemOthersCouponException.class)
	@ResponseBody
	public Object handleCanNotRedeemOthersCouponException(HttpServletRequest req, Exception exception) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("respCode", 2104);
		result.put("message", "不能使用他人的优惠卷");
		return result;
	}

	/**
	 * This catches every exception and returns 500 which means uncaught
	 * internal error.
	 */
	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Object handleError(HttpServletRequest req, Exception exception) {
		logger.error("Unknown Exception: ", exception);
		exception.printStackTrace();
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> errorData = new HashMap<String, Object>();
		errorData.put("class", exception.getClass());
		errorData.put("stacktrace", exception.getStackTrace());
		result.put("respCode", 500);
		result.put("message", "服务器内部错误");
		result.put("data", errorData);
		return result;
	}

}
