package com.dasinong.farmerClub;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dasinong.farmerClub.dao.IInstitutionDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.dao.UserDao;
import com.dasinong.farmerClub.exceptions.InvalidParameterException;
import com.dasinong.farmerClub.exceptions.MissingParameterException;
import com.dasinong.farmerClub.exceptions.ResourceNotFoundException;
import com.dasinong.farmerClub.exceptions.SalesPeopleCannotBeReferredException;
import com.dasinong.farmerClub.exceptions.UserIsNotLoggedInException;
import com.dasinong.farmerClub.exceptions.UserTypeAlreadyDefinedException;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.UserType;
import com.dasinong.farmerClub.outputWrapper.UserWrapper;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class UserController extends RequireUserLoginController {

	@Autowired
	ServletContext servletContext;

	@RequestMapping(value = "/loadUserProfile", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object loadUserProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		User user = this.getLoginUser(request);
		if (user == null) {
			throw new UserIsNotLoggedInException();
		}
		result.put("respCode", 200);
		result.put("message", "获取成功");
		UserWrapper userwrapper = new UserWrapper(user);
		result.put("data", userwrapper);
		return result;
	}

	@RequestMapping(value = "/updateProfile", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object updateProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		User user = this.getLoginUser(request);
		if (user == null) {
			throw new UserIsNotLoggedInException();
		}
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");

		String userName = request.getParameter("username");
		String cellphone = request.getParameter("cellphone");
		String address = request.getParameter("address");
		String pictureId = request.getParameter("pictureId");
		String telephone = request.getParameter("telephone");
		String isAuth = request.getParameter("isAuth");

		// String oldCellphone = user.getCellPhone();

		if (userName != null && !userName.isEmpty()) {
			user.setUserName(userName);
		}
		if (address != null && !address.isEmpty()) {
			user.setAddress(address);
		}
		if (cellphone != null && !cellphone.isEmpty()) {
			user.setCellPhone(cellphone);
		}
		if (pictureId != null && !pictureId.isEmpty()) {
			user.setPictureId(pictureId);
		}
		if (telephone != null && !telephone.isEmpty()) {
			user.setTelephone(telephone);
		}
		/*
		 * if (!oldCellphone.equals(cellphone)){ user.setAuthenticated(false); }
		 */
		if (isAuth != null && !isAuth.isEmpty()) {
			user.setAuthenticated(true);
		}
		user.setUpdateAt(new Date());
		userDao.update(user);

		result.put("respCode", 200);
		result.put("message", "更新成功");
		UserWrapper userwrapper = new UserWrapper(user);
		result.put("data", userwrapper);
		return result;
	}

	@RequestMapping(value = "/isAuth", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object isAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		User user = this.getLoginUser(request);
		if (user == null) {
			throw new UserIsNotLoggedInException();
		}

		if (user.getAuthenticated()) {
			result.put("respCode", 200);
			result.put("message", "已验证");
			return result;
		} else {
			// isAuth还修改用户状态？？
			// TODO: figure out what shit this API is doing。
			// @Xiyao delete this API as long as verify it no longer used.
			user.setAuthenticated(true);
			UserDao userDao = (UserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
			userDao.update(user);
			result.put("respCode", 120);
			result.put("message", "尚未验证,并设置为已验证");
			return result;
		}
	}

	@RequestMapping(value = "/setAuth", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object setAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		User user = this.getLoginUser(request);
		if (user == null) {
			throw new UserIsNotLoggedInException();
		}

		if (user.getAuthenticated()) {
			result.put("respCode", 200);
			result.put("message", "已验证");
			return result;
		} else {
			user.setAuthenticated(true);
			UserDao userDao = (UserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
			userDao.update(user);
			result.put("respCode", 200);
			result.put("message", "提交验证");
			return result;
		}
	}

	@RequestMapping(value = "/uploadAvater", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object uploadAvater(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		User user = this.getLoginUser(request);
		if (user == null) {
			throw new UserIsNotLoggedInException();
		}
		MultipartFile imgFile = request.getFile("file");

		if (!imgFile.isEmpty()) {
			String origFileName = imgFile.getOriginalFilename();
			String[] imgt = origFileName.split("\\.");
			String ext = "";
			if (imgt.length >= 2) {
				ext = imgt[imgt.length - 1];
			}

			String filePath = this.servletContext.getRealPath("/");
			Random rnd = new Random();
			String fileName = user.getCellPhone() + rnd.nextInt(9999) + "." + ext;
			File dest = new File(filePath + "../avater/" + fileName);
			imgFile.transferTo(dest);
			user.setPictureId(fileName);
			IUserDao userdao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
			userdao.update(user);
		}
		result.put("respCode", 200);
		result.put("message", "上传成功");
		return result;
	}

	@RequestMapping(value = "/updatePassword", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object updatePassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		User user = this.getLoginUser(request);
		if (user == null) {
			throw new UserIsNotLoggedInException();
		}

		String oldPassword = request.getParameter("oPassword");
		String newPassword = request.getParameter("nPassword");

		// If password is set, we expect both oPassword and nPassword
		// to be present.
		if ((user.getIsPassSet() && oldPassword == null) || newPassword == null) {
			throw new MissingParameterException();
		}

		// If password is set, we expect oPassword to match password on file.
		if (user.getIsPassSet() && !user.validatePassword(oldPassword)) {
			result.put("respCode", 320);
			result.put("message", "旧密码错误");
			return result;
		}

		user.setAndEncryptPassword(newPassword);
		user.setIsPassSet(true);
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
		userDao.update(user);

		result.put("respCode", 200);
		result.put("message", "密码设置成功");
		return result;
	}

	@RequestMapping(value = "/resetPassword", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();

		User user = this.getLoginUser(request);
		if (user == null) {
			throw new UserIsNotLoggedInException();
		}

		String newPassword = request.getParameter("nPassword");

		if (newPassword == null) {
			throw new MissingParameterException("nPassword");
		}

		user.setAndEncryptPassword(newPassword);
		user.setIsPassSet(true);
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
		userDao.update(user);
		result.put("respCode", 200);
		result.put("message", "修改成功");
		return result;
	}

	@RequestMapping(value = "/setRef", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object setRef(HttpServletRequest request, HttpServletResponse response) throws Exception {
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
		IInstitutionDao institutionDao = (IInstitutionDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("institutionDao");
		HashMap<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		User user = this.getLoginUser(request);
		if (user.getRefuid() != null) {
			result.put("respCode", 201);
			result.put("message", "已设置过推荐人");
			// result.put("data", user.getUserId());
			return result;
		}

		String refcode = requestX.getString("refcode");
		Long refuid = userDao.getUIDbyRef(refcode);
		if (refuid == -1) {
			Institution ins = institutionDao.findByCode(refcode);
			if (ins == null) {
				result.put("respCode", 300);
				result.put("message", " 目标推荐码不存在");
				return result;
			}

			if (user.getUserType() != null) {
				throw new UserTypeAlreadyDefinedException(user.getUserId(), user.getUserType());
			}

			user.setInstitutionId(ins.getId());
			user.setUserType(UserType.SALES);
			userDao.update(user);
			result.put("respCode", 200);
			result.put("message", "绑定机构码成功");
			result.put("data", new UserWrapper(user));
			return result;
		}

		// Referred by user
		if (user.getUserType() == UserType.SALES) {
			throw new SalesPeopleCannotBeReferredException(user.getUserId());
		}
		user.setRefuid(refuid);
		User refuser = userDao.findById(refuid);
		user.setInstitutionId(refuser.getInstitutionId());
		userDao.update(user);
		result.put("respCode", 200);
		result.put("message", "推荐用户设置成功");
		result.put("data", new UserWrapper(user));
		return result;
	}

	@RequestMapping(value = "/setUserType", produces = "application/json;charset=utf-8")
	@ResponseBody
	public Object setType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		User user = this.getLoginUser(request);

		String userType = requestX.getString("type");
		if (!UserType.isValid(userType)) {
			throw new InvalidParameterException("type", "UserType");
		}

		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
		user.setUserType(userType);
		userDao.update(user);

		result.put("respCode", 200);
		result.put("message", "更新成功");
		return result;
	}
}
