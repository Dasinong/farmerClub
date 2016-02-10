package com.dasinong.farmerClub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.exceptions.InvalidParameterException;
import com.dasinong.farmerClub.exceptions.ResourceNotFoundException;
import com.dasinong.farmerClub.facade.IBaiKeFacade;
import com.dasinong.farmerClub.outputWrapper.CPProductWrapper;
import com.dasinong.farmerClub.outputWrapper.FormattedCPProductWrapper;
import com.dasinong.farmerClub.outputWrapper.PetDisSpecWrapper;
import com.dasinong.farmerClub.outputWrapper.VarietyWrapper;
import com.dasinong.farmerClub.util.Env;
import com.dasinong.farmerClub.util.FullTextSearch;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class BaiKeController extends BaseController {

	IBaiKeFacade baiKeFacade;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/searchWord", produces = "application/json")
	@ResponseBody
	public Object searchWord(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HashMap<String, Object> content = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		
		String key = requestX.getNonEmptyString("key");
		String type = requestX.getStringOptional("type", null);
		
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		if (type == null) {
			result.put("respCode", 200);
			result.put("message", "检索成功");
			content.put("variety", baiKeFacade.searchVariety(key));
			content.put("cpproduct", baiKeFacade.searchCPProduct(key));
			content.putAll(baiKeFacade.searchPetDisSpec(key));
			result.put("data", content);
			return result;
		}
		if (type.equalsIgnoreCase("variety")) {
			result.put("respCode", 200);
			result.put("message", "获取成功");
			result.put("data", baiKeFacade.searchVariety(key));
			return result;
		}
		if (type.equalsIgnoreCase("cpproduct")) {
			result.put("respCode", 200);
			result.put("message", "获取成功");
			result.put("data", baiKeFacade.searchCPProduct(key));
			return result;
		}

		if (type.equalsIgnoreCase("petdisspec")) {
			result.put("respCode", 200);
			result.put("message", "获取成功");
			Map<String, List<HashMap<String, String>>> orig = baiKeFacade.searchPetDisSpec(key);
			List<HashMap<String, String>> target = new ArrayList<HashMap<String, String>>();
			for (Entry<String, List<HashMap<String, String>>> es : orig.entrySet()) {
				target.addAll(es.getValue());
			}
			result.put("data", target);
			return result;
		}
		throw new InvalidParameterException("type", "Enum");
	}

	@RequestMapping(value = "/createVarietyIndex", produces = "application/json")
	@ResponseBody
	public Object createVarietyIndex(HttpServletRequest request, HttpServletResponse response) {
		FullTextSearch bs = null;
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			bs = new FullTextSearch("variety", Env.getEnv().DataDir + "/varietyIndex");
		} else {
			bs = new FullTextSearch("variety", Env.getEnv().DataDir + "/lucene/variety");
		}
		bs.setHighlighterFormatter("<font color='red'>", "</font>");
		bs.createVarietyIndex();
		String[] a = { "varietyName", "varietySource" };
		String[] b = { "varietyName", "varietyId", "varietySource" };
		try {
			HashMap[] h = bs.search("玉米", a, b);
			for (int k = 0; k < h.length; k++) {
				if (h[k] == null) {
					break;
				}
			}
		} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}

		return "OK";
	}

	@RequestMapping(value = "/createPetDisSpecIndex", produces = "application/json")
	@ResponseBody
	public Object createPetDisSpecIndex(HttpServletRequest request, HttpServletResponse response) {
		FullTextSearch bs = null;
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			bs = new FullTextSearch("petDisSpec", Env.getEnv().DataDir + "/petDisSpecIndex");
		} else {
			bs = new FullTextSearch("petDisSpec", Env.getEnv().DataDir + "/lucene/petDisSpec");
		}
		bs.setHighlighterFormatter("<font color='red'>", "</font>");
		bs.createPetIndex(); // only need create index once
		String[] resource = { "petDisSpecName", "cropName", "sympthon" };
		String[] result = { "petDisSpecName", "petDisSpecId", "cropName", "sympthon", "type" };
		try {
			HashMap[] h = bs.search("玉米", resource, result);
			for (int k = 0; k < h.length; k++) {
				if (h[k] == null) {
					break;
				}
			}
		} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "OK";
	}

	@RequestMapping(value = "/createCPProductIndex", produces = "application/json")
	@ResponseBody
	public Object createCPProductIndex(HttpServletRequest request, HttpServletResponse response) {
		FullTextSearch bs = null;
		if (System.getProperty("os.name").equalsIgnoreCase("windows 7")) {
			bs = new FullTextSearch("petDisSpec", Env.getEnv().DataDir + "/petCPProductIndex");
		} else {
			bs = new FullTextSearch("petDisSpec", Env.getEnv().DataDir + "/lucene/cPProduct");
		}
		bs.setHighlighterFormatter("<font color='red'>", "</font>");
		bs.createCPProductIndex(); // only need create index once
		String[] resource = { "cPProductName", "manufacturer", "crop" };
		String[] result = { "cPProductName", "manufacturer", "crop", "cPProductId" };
		try {
			HashMap[] h = bs.search("玉米", resource, result);
			for (int k = 0; k < h.length; k++) {
				if (h[k] == null) {
					break;
				}
			}
		} catch (ParseException | IOException | InvalidTokenOffsetsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "OK";
	}

	@RequestMapping(value = "/getVarietyBaiKeById", produces = "application/json")
	@ResponseBody
	public Object getVarietyBaiKeById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Long id;
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (Exception e) {
			throw new InvalidParameterException("id", "Long");
		}
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		VarietyWrapper vw = baiKeFacade.getVarietyById(id);
		if (vw == null) {
			throw new ResourceNotFoundException(id, "variety");
		} else {
			result.put("respCode", 200);
			result.put("message", "检索成功");
			result.put("data", vw);
			return result;
		}
	}

	@RequestMapping(value = "/getPetDisSpecBaiKeById", produces = "application/json")
	@ResponseBody
	public Object getPetDisSpecById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		Long id = requestX.getLong("id");
		
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		PetDisSpecWrapper pdsw = baiKeFacade.getPetDisSpecById(id);
		if (pdsw == null) {
			throw new ResourceNotFoundException(id, "petdisspec");
		} else {
			result.put("respCode", 200);
			result.put("message", "检索成功");
			result.put("data", pdsw);
			return result;
		}
	}

	@RequestMapping(value = "/getCPProductById", produces = "application/json")
	@ResponseBody
	public Object getCPProductById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		
		Long id = requestX.getLong("id");
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");

		CPProductWrapper cpw = baiKeFacade.getCPProductById(id);
		if (cpw == null) {
			throw new ResourceNotFoundException(id, "cpproduct");
		} else {
			result.put("respCode", 200);
			result.put("message", "获得成功");
			result.put("data", cpw);
			return result;
		}
	}
	
	@RequestMapping(value = "/getFormattedCPProductById", produces = "application/json")
	@ResponseBody
	public Object getFormattedCPProductById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		
		Long id = requestX.getLong("id");
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		
		FormattedCPProductWrapper cpw = baiKeFacade.getFormattedCPProductById(id);
		if (cpw == null) {
			throw new ResourceNotFoundException(id, "cpproduct");
		} else {
			result.put("respCode", 200);
			result.put("message", "获得成功");
			result.put("data", cpw);
			return result;
		}
	}

	@RequestMapping(value = "/browseCropByType", produces = "application/json")
	@ResponseBody
	public Object browseCropByType(HttpServletRequest request, HttpServletResponse response) {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		String type = requestX.getNonEmptyString("type");
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		return baiKeFacade.getCropByType(type);
	}

	@RequestMapping(value = "/browseVarietyByCropId", produces = "application/json")
	@ResponseBody
	public Object browseVarietyByCropId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		Long cropId = requestX.getLong("cropId");
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		return baiKeFacade.browseVarietyByCropId(cropId);
	}

	@RequestMapping(value = "/browsePetDisByType", produces = "application/json")
	@ResponseBody
	public Object browsePetDisByType(HttpServletRequest request, HttpServletResponse response) {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		String type = requestX.getNonEmptyString("type");
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		return baiKeFacade.browsePetDisByType(type);
	}

	@RequestMapping(value = "/browseCPProductByModel", produces = "application/json")
	@ResponseBody
	public Object browseCPProductByModel(HttpServletRequest request, HttpServletResponse response) {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		String model = requestX.getNonEmptyString("model");
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		return baiKeFacade.browseCPProductByModel(model);
	}
	
	
	@RequestMapping(value = "/browseCPProductByModelAndManufacturer", produces = "application/json")
	@ResponseBody
	public Object browseCPProductByModelAndManufacturer(HttpServletRequest request, HttpServletResponse response) {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		String model = requestX.getNonEmptyString("model");
		String manufacturer = requestX.getNonEmptyString("manufacturer");
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		return baiKeFacade.browseCPProductByModelAndManufacturer(model,manufacturer);
	}

	@RequestMapping(value = "/getCPProdcutsByIngredient", produces = "application/json")
	@ResponseBody
	public Object getCPProdcutsByIngredient(HttpServletRequest request, HttpServletResponse response) {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		String ingredient = requestX.getNonEmptyString("ingredient");
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		return baiKeFacade.getCPProdcutsByIngredient(ingredient);
	}

	@RequestMapping(value = "/getVarietysByName", produces = "application/json")
	@ResponseBody
	public Object getVarietysByName(HttpServletRequest request, HttpServletResponse response) {
		HttpServletRequestX requestX = new HttpServletRequestX(request);
		String name = requestX.getNonEmptyString("name");
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		return baiKeFacade.getVarietysByName(name);
	}

	@RequestMapping(value = "/browsePetDisSpecsByCropIdAndType", produces = "application/json")
	@ResponseBody
	public Object browsePetDisSpecsByCropIdAndType(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		Long cropId = requestX.getLong("cropId");
		String type = requestX.getNonEmptyString("type");

		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		return baiKeFacade.browsePetDisSpecsByCropIdAndType(cropId, type);
	}
}
