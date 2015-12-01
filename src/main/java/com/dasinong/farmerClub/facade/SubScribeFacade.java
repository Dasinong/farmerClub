package com.dasinong.farmerClub.facade;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.dao.ISubScribeListDao;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.SubScribeList;
import com.dasinong.farmerClub.model.User;

@Transactional
public class SubScribeFacade implements ISubScribeFacade {

	// @Autowired
	private ISubScribeListDao subScribeListDao;
	private ICropDao cropDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ISubScribeFacade#insertSubScribeList(com
	 * .dasinong.farmerClub.model.User, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * double, java.lang.Long, boolean, boolean, boolean)
	 */
	@Override
	public Object insertSubScribeList(User user, String targetName, String cellphone, String province, String city,
			String country, String district, double area, Long cropId, boolean isAgriWeather, boolean isNatAlter,
			boolean isRiceHelper) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		SubScribeList ssb = new SubScribeList(user.getUserId(), targetName, cellphone, province, city, country,
				district, area, cropId, isAgriWeather, isNatAlter, isRiceHelper);
		subScribeListDao = (ISubScribeListDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeListDao");
		subScribeListDao.save(ssb);

		result.put("respCode", 200);
		result.put("message", "插入成功");
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ISubScribeFacade#getSubScribeLists(com.
	 * dasinong.farmerClub.model.User)
	 */
	@Override
	public Object getSubScribeLists(User user) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		subScribeListDao = (ISubScribeListDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeListDao");

		List<SubScribeList> ssls = (List<SubScribeList>) subScribeListDao.findByOwnerId(user.getUserId());

		Map<String, Long> data = new HashMap<String, Long>();
		for (SubScribeList ssl : ssls) {
			data.put(ssl.getTargetName(), ssl.getId());
		}

		result.put("respCode", 200);
		result.put("message", "获得列表成功");
		result.put("data", data);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ISubScribeFacade#loadSubScribeList(java.
	 * lang.Long)
	 */
	@Override
	public Object loadSubScribeList(Long id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		subScribeListDao = (ISubScribeListDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeListDao");

		SubScribeList ssl = subScribeListDao.findById(id);
		cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		Crop crop = cropDao.findById(ssl.getCropId());

		ssl.setCropName(crop.getCropName());

		result.put("respCode", 200);
		result.put("message", "获得列表成功");
		result.put("data", ssl);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.ISubScribeFacade#updateSubScribeList(
	 * java.lang.Long, com.dasinong.farmerClub.model.User, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, double, java.lang.Long, boolean, boolean, boolean)
	 */
	@Override
	public Object deleteSubScribeList(Long id) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		subScribeListDao = (ISubScribeListDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeListDao");
		SubScribeList ssl = subScribeListDao.findById(id);
		subScribeListDao.delete(ssl);

		result.put("respCode", 200);
		result.put("message", "删除成功");
		return result;
	}

	@Override
	public Object updateSubScribeList(Long id, User user, String targetName, String cellphone, String province,
			String city, String country, String district, double area, Long cropId, boolean isAgriWeather,
			boolean isNatAlter, boolean isRiceHelper) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		subScribeListDao = (ISubScribeListDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeListDao");
		SubScribeList ssl = subScribeListDao.findById(id);

		ssl.setOwnerId(user.getUserId());
		ssl.setTargetName(targetName);
		ssl.setCellphone(cellphone);
		ssl.setProvince(province);
		ssl.setCity(city);
		ssl.setCountry(country);
		ssl.setDistrict(district);
		ssl.setArea(area);
		ssl.setCropId(cropId);
		ssl.setIsAgriWeather(isAgriWeather);
		ssl.setIsNatAler(isNatAlter);
		ssl.setIsRiceHelper(isRiceHelper);
		subScribeListDao.update(ssl);

		result.put("respCode", 200);
		result.put("message", "更新成功");
		result.put("data", ssl);
		return result;
	}

	@Override
	public Object insertSubScribeList(User user, String targetName, String cellphone, String province, String city,
			String country, String district, double area, String cropName, boolean isAgriWeather, boolean isNatAlter,
			boolean isRiceHelper) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		Crop crop = cropDao.findByCropName(cropName);
		SubScribeList ssb = new SubScribeList(user.getUserId(), targetName, cellphone, province, city, country,
				district, area, crop.getCropId(), isAgriWeather, isNatAlter, isRiceHelper);
		ssb.setCropName(cropName);
		subScribeListDao = (ISubScribeListDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeListDao");
		subScribeListDao.save(ssb);

		result.put("respCode", 200);
		result.put("message", "插入成功");
		return result;
	}

	@Override
	public Object updateSubScribeList(Long id, User user, String targetName, String cellphone, String province,
			String city, String country, String district, double area, String cropName, boolean isAgriWeather,
			boolean isNatAlter, boolean isRiceHelper) {

		cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		Crop crop = cropDao.findByCropName(cropName);

		HashMap<String, Object> result = new HashMap<String, Object>();
		subScribeListDao = (ISubScribeListDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("subScribeListDao");
		SubScribeList ssl = subScribeListDao.findById(id);

		ssl.setOwnerId(user.getUserId());
		ssl.setTargetName(targetName);
		ssl.setCellphone(cellphone);
		ssl.setProvince(province);
		ssl.setCity(city);
		ssl.setCountry(country);
		ssl.setDistrict(district);
		ssl.setArea(area);
		ssl.setCropId(crop.getCropId());
		ssl.setCropName(cropName);
		ssl.setIsAgriWeather(isAgriWeather);
		ssl.setIsNatAler(isNatAlter);
		ssl.setIsRiceHelper(isRiceHelper);
		subScribeListDao.update(ssl);

		result.put("respCode", 200);
		result.put("message", "更新成功");
		result.put("data", ssl);
		return result;
	}
}
