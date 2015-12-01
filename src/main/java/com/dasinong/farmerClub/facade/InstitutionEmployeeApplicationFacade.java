package com.dasinong.farmerClub.facade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.IInstitutionDao;
import com.dasinong.farmerClub.dao.IInstitutionEmployeeApplicationDao;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.exceptions.InvalidParameterException;
import com.dasinong.farmerClub.exceptions.UserTypeAlreadyDefinedException;
import com.dasinong.farmerClub.model.Institution;
import com.dasinong.farmerClub.model.InstitutionEmployeeApplication;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.UserType;

@Transactional
public class InstitutionEmployeeApplicationFacade implements IInstitutionEmployeeApplicationFacade {

	@Override
	public Object create(User user, String contactName, String cellphone, String code, String title, String region) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		IInstitutionEmployeeApplicationDao appDao = (IInstitutionEmployeeApplicationDao) ContextLoader
				.getCurrentWebApplicationContext().getBean("institutionEmployeeApplicationDao");
		IInstitutionDao instDao = (IInstitutionDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("institutionDao");
		IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");

		if (user.getUserType() != null) {
			throw new UserTypeAlreadyDefinedException(user.getUserId(), user.getUserType());
		}

		Institution inst = instDao.findByCode(code);
		if (inst == null) {
			throw new InvalidParameterException("code", "String");
		}

		InstitutionEmployeeApplication app = new InstitutionEmployeeApplication();
		app.setContactName(contactName);
		app.setCellphone(cellphone);
		app.setInstitutionId(inst.getId());
		app.setTitle(title);
		app.setRegion(region);
		appDao.save(app);

		user.setInstitutionId(inst.getId());
		user.setUserType(UserType.SALES);
		userDao.update(user);

		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("application", app);
		result.put("respCode", 200);
		result.put("message", "保存成功");
		result.put("data", data);
		return result;
	}

}
