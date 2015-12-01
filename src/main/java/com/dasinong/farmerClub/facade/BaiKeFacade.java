package com.dasinong.farmerClub.facade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.dao.ICPProductBrowseDao;
import com.dasinong.farmerClub.dao.ICPProductDao;
import com.dasinong.farmerClub.dao.ICropDao;
import com.dasinong.farmerClub.dao.IPetDisSpecBrowseDao;
import com.dasinong.farmerClub.dao.IPetDisSpecDao;
import com.dasinong.farmerClub.dao.IVarietyBrowseDao;
import com.dasinong.farmerClub.dao.IVarietyDao;
import com.dasinong.farmerClub.model.CPProduct;
import com.dasinong.farmerClub.model.CPProductBrowse;
import com.dasinong.farmerClub.model.Crop;
import com.dasinong.farmerClub.model.PetDisSpec;
import com.dasinong.farmerClub.model.PetDisSpecBrowse;
import com.dasinong.farmerClub.model.Variety;
import com.dasinong.farmerClub.model.VarietyBrowse;
import com.dasinong.farmerClub.outputWrapper.CPProductWrapper;
import com.dasinong.farmerClub.outputWrapper.CropWrapper;
import com.dasinong.farmerClub.outputWrapper.PetDisSpecWrapper;
import com.dasinong.farmerClub.outputWrapper.VarietyWrapper;
import com.dasinong.farmerClub.util.Env;
import com.dasinong.farmerClub.util.FullTextSearch;

@Transactional
public class BaiKeFacade implements IBaiKeFacade {
	class FilterInfo {
		boolean isGuo;
	}

	ICropDao cropDao;
	IVarietyDao varietyDao;
	IVarietyBrowseDao varietyBrowseDao;
	IPetDisSpecDao petDisSpecDao;
	IPetDisSpecBrowseDao petDisSpecBrowseDao;
	ICPProductDao cPProductDao;
	ICPProductBrowseDao cPProductBrowseDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.IBaiKeFacade#getCPProductById(java.lang.
	 * Long)
	 */
	@Override
	public CPProductWrapper getCPProductById(Long id) {
		cPProductDao = (ICPProductDao) ContextLoader.getCurrentWebApplicationContext().getBean("cPProductDao");
		CPProduct pro = cPProductDao.findById(id);
		if (pro != null) {
			CPProductWrapper cpw = new CPProductWrapper(pro);
			return cpw;
		} else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.IBaiKeFacade#getVarietyById(java.lang.
	 * Long)
	 */
	@Override
	public VarietyWrapper getVarietyById(Long id) {
		varietyDao = (IVarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");
		Variety v = varietyDao.findById(id);
		if (v != null) {
			VarietyWrapper vw = new VarietyWrapper(v);
			return vw;
		} else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dasinong.farmerClub.facade.IBaiKeFacade#getPetDisSpecById(java.lang
	 * .Long)
	 */
	@Override
	public PetDisSpecWrapper getPetDisSpecById(Long id) {
		petDisSpecDao = (IPetDisSpecDao) ContextLoader.getCurrentWebApplicationContext().getBean("petDisSpecDao");
		PetDisSpec pds = petDisSpecDao.findById(id);
		if (pds != null) {
			PetDisSpecWrapper pdsw = new PetDisSpecWrapper(pds);
			return pdsw;
		} else
			return null;
	}

	@Override
	public Object browseVarietyByCropId(Long cropId) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		varietyBrowseDao = (IVarietyBrowseDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("varietyBrowseDao");
		List<VarietyBrowse> vbs = varietyBrowseDao.findByCropId(cropId);
		if (vbs == null || vbs.size() == 0) {
			result.put("respCode", 400);
			result.put("message", "该作物不存在");
			return result;
		}

		result.put("respCode", 200);
		result.put("message", "获得成功");
		result.put("data", vbs);
		return result;
	}

	@Override
	public Object browseCPProductByModel(String model) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		cPProductBrowseDao = (ICPProductBrowseDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("cPProductBrowseDao");
		List<CPProductBrowse> cpbs = cPProductBrowseDao.findByModel(model);
		if (cpbs == null || cpbs.size() == 0) {
			result.put("respCode", 400);
			result.put("message", "该类型农药不存在");
			return result;
		}
		result.put("respCode", 200);
		result.put("message", "获得成功");
		result.put("data", cpbs);
		return result;
	}

	@Override
	public Object browsePetDisByType(String type) {

		HashMap<String, Object> result = new HashMap<String, Object>();
		petDisSpecBrowseDao = (IPetDisSpecBrowseDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecBrowseDao");
		List<PetDisSpecBrowse> pdsbs = petDisSpecBrowseDao.findByType(type);
		if (pdsbs == null || pdsbs.size() == 0) {
			result.put("respCode", 400);
			result.put("message", "该类型病虫草害不存在");
			return result;
		}
		result.put("respCode", 200);
		result.put("message", "获得成功");
		result.put("data", pdsbs);
		return result;

	}

	@Override
	public Object getCropByType(String type) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		cropDao = (ICropDao) ContextLoader.getCurrentWebApplicationContext().getBean("cropDao");
		List<Crop> crops = cropDao.findByType(type);
		if (crops == null || crops.size() == 0) {
			result.put("respCode", 400);
			result.put("message", "该类型作物不存在");
			return result;
		}
		List<CropWrapper> cws = new ArrayList<CropWrapper>();
		for (Crop c : crops) {
			cws.add(new CropWrapper(c));
		}
		result.put("respCode", 200);
		result.put("message", "获得成功");
		result.put("data", cws);
		return result;
	}

	@Override
	public Object getVarietysByName(String name) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		varietyDao = (IVarietyDao) ContextLoader.getCurrentWebApplicationContext().getBean("varietyDao");
		List<Variety> varietys = varietyDao.findVarietysByName(name);
		if (varietys == null || varietys.size() == 0) {
			result.put("respCode", 400);
			result.put("message", "该名称品种不存在");
			return result;
		}

		List<VarietyWrapper> vws = new ArrayList<VarietyWrapper>();
		for (Variety v : varietys) {
			vws.add(new VarietyWrapper(v));
		}
		result.put("respCode", 200);
		result.put("message", "获得成功");
		result.put("data", vws);
		return result;
	}

	@Override
	public Object getCPProdcutsByIngredient(String ingredient) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		cPProductDao = (ICPProductDao) ContextLoader.getCurrentWebApplicationContext().getBean("cPProductDao");
		List<CPProduct> cpproducts = cPProductDao.findByIngredient(ingredient);
		if (cpproducts == null || cpproducts.size() == 0) {
			result.put("respCode", 400);
			result.put("message", "该有效成分不存在");
			return result;
		}
		List<CPProductWrapper> cppws = new ArrayList<CPProductWrapper>();
		for (CPProduct cpp : cpproducts) {
			cppws.add(new CPProductWrapper(cpp));
		}
		result.put("respCode", 200);
		result.put("message", "获得成功");
		result.put("data", cppws);
		return result;
	}

	@Override
	public List<HashMap<String, String>> searchVariety(String key) {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		FullTextSearch bs = null;
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {

			bs = new FullTextSearch("variety", Env.getEnv().DataDir + "/varietyIndex");
		} else {
			bs = new FullTextSearch("variety", Env.getEnv().DataDir + "/lucene/variety");
		}
		bs.setHighlighterFormatter("<font color='red'>", "</font>");

		try {
			String[] source = { "varietyName", "varietySource", "registerationId", "characteristics" };
			String[] target = { "varietyName", "varietyId", "subId", "varietySource", "registerationId" };
			HashMap<String, String>[] h = bs.search(key, source, target);
			Set<Integer> idcheck = new HashSet<Integer>();
			if (h != null) {
				for (int i = 0; i < h.length; i++) {
					if (h[i] != null) {
						if (!idcheck.contains(Integer.parseInt(h[i].get("varietyId")))) {
							idcheck.add(Integer.parseInt(h[i].get("varietyId")));
							HashMap<String, String> record = new HashMap<String, String>();
							record.put("id", h[i].get("varietyId"));
							record.put("name", h[i].get("varietyName") + h[i].get("subId"));
							record.put("source", h[i].get("registerationId"));
							record.put("type", "variety");
							result.add(record);
						}
					}
				}
			}
		} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<HashMap<String, String>> searchCPProduct(String key) {
		List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		FullTextSearch bs = null;

		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			bs = new FullTextSearch("petDisSpec", Env.getEnv().DataDir + "/cPProductIndex");
		} else {
			bs = new FullTextSearch("petDisSpec", Env.getEnv().DataDir + "/lucene/cPProduct");
		}
		bs.setHighlighterFormatter("<font color='red'>", "</font>");

		try {
			String[] source = { "cPProductName", "manufacturer", "crop" };
			String[] target = { "cPProductName", "manufacturer", "crop", "cPProductId" };
			HashMap<String, String>[] h = bs.search(key, source, target);
			Set<Integer> idcheck = new HashSet<Integer>();
			if (h != null) {
				for (int i = 0; i < h.length; i++) {
					if (h[i] != null) {
						if (!idcheck.contains(Integer.parseInt(h[i].get("cPProductId")))) {
							idcheck.add(Integer.parseInt(h[i].get("cPProductId")));
							HashMap<String, String> record = new HashMap<String, String>();
							record.put("id", h[i].get("cPProductId"));
							record.put("name", h[i].get("cPProductName"));
							record.put("source", h[i].get("crop") + " " + h[i].get("manufacturer"));
							record.put("type", "pesticide");
							result.add(record);
						}
					}
				}
			}
		} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Object browsePetDisSpecsByCropIdAndType(Long cropId, String type) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		petDisSpecBrowseDao = (IPetDisSpecBrowseDao) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecBrowseDao");

		List<PetDisSpecBrowse> pdsbList = petDisSpecBrowseDao.findByCropIdAndType(cropId, type);
		if (pdsbList == null || pdsbList.size() == 0) {
			result.put("respCode", 400);
			result.put("message", "这些病虫草害不存在");
			return result;
		}

		result.put("respCode", 200);
		result.put("message", "获得成功");
		result.put("data", pdsbList);
		return result;
	}

	@Override
	public Map<String, List<HashMap<String, String>>> searchPetDisSpec(String key) {
		Map<String, List<HashMap<String, String>>> result = new HashMap<String, List<HashMap<String, String>>>();
		FullTextSearch bs = null;

		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			bs = new FullTextSearch("petDisSpec", Env.getEnv().DataDir + "/petDisSpecIndex");
		} else {
			bs = new FullTextSearch("petDisSpec", Env.getEnv().DataDir + "lucene/petDisSpec");
		}
		bs.setHighlighterFormatter("<font color='red'>", "</font>");
		try {
			String[] source = { "petDisSpecName", "cropName", "sympthon" };
			String[] target = { "petDisSpecName", "petDisSpecId", "cropName", "sympthon", "type" };

			HashMap<String, String>[] h = bs.search(key, source, target);
			List<HashMap<String, String>> ill = new ArrayList<HashMap<String, String>>();
			List<HashMap<String, String>> pest = new ArrayList<HashMap<String, String>>();
			List<HashMap<String, String>> grass = new ArrayList<HashMap<String, String>>();
			Set<Integer> idcheck = new HashSet<Integer>();
			if (h != null) {
				for (int i = 0; i < h.length; i++) {
					if (h[i] != null) {
						if (!idcheck.contains(Integer.parseInt(h[i].get("petDisSpecId")))) {
							idcheck.add(Integer.parseInt(h[i].get("petDisSpecId")));
							HashMap<String, String> record = new HashMap<String, String>();
							record.put("id", h[i].get("petDisSpecId"));
							record.put("name", h[i].get("petDisSpecName"));
							record.put("source", h[i].get("cropName") + " " + h[i].get("sympthon"));
							if (h[i].get("type").contains("病")) {
								record.put("type", "pest");
								ill.add(record);
							}
							if (h[i].get("type").contains("虫")) {
								record.put("type", "pest");
								pest.add(record);
							}
							if (h[i].get("type").contains("草")) {
								record.put("type", "pest");
								grass.add(record);
							}
						}
					}
				}
			}
			result.put("disease", ill);
			result.put("pest", pest);
			result.put("weeds", grass);
		} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<Variety> filterVar(List<Variety> vars) {
		List<Variety> result = new ArrayList<Variety>();

		return result;
	}
}
