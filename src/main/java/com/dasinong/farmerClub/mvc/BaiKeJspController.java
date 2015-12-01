package com.dasinong.farmerClub.mvc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.dasinong.farmerClub.BaseController;
import com.dasinong.farmerClub.facade.IBaiKeFacade;
import com.dasinong.farmerClub.outputWrapper.CPProductWrapper;
import com.dasinong.farmerClub.outputWrapper.PetDisSpecWrapper;
import com.dasinong.farmerClub.outputWrapper.VarietyWrapper;

public class BaiKeJspController extends BaseController implements Controller {

	IBaiKeFacade baiKeFacade;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse reponse) throws Exception {
		System.out.println(this.getViewerContext(request).getUserId());

		System.out.println(this.getViewerContext(request).getAppId());
		ModelAndView mv = new ModelAndView();
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		baiKeFacade = (IBaiKeFacade) ContextLoader.getCurrentWebApplicationContext().getBean("baiKeFacade");
		HashMap<String, Object> result = new HashMap<String, Object>();
		try {
			switch (type) {
			case "pest":
				PetDisSpecWrapper pdsw = baiKeFacade.getPetDisSpecById(Long.parseLong(id));
				mv = handlePest(mv, pdsw);
				break;
			case "pesticide":
				CPProductWrapper cpw = baiKeFacade.getCPProductById(Long.parseLong(id));
				mv = handlePesticide(mv, cpw);
				break;
			case "variety":
				VarietyWrapper vw = baiKeFacade.getVarietyById(Long.parseLong(id));
				mv = handleVariety(mv, vw);
				break;
			}
		} catch (Exception e) {
			this.logger.error("Error happened when handle BaiKeJsp Data!", e);
		}

		return mv;
	}

	public ModelAndView handlePest(ModelAndView mv, PetDisSpecWrapper pdsw) {
		String[] images = pdsw.getImagesPath();
		mv.addObject("DisasterName", pdsw.getPetDisSpecName());
		mv.addObject("Alias", pdsw.getAlias());
		mv.addObject("ImagesCount", images.length);
		mv.addObject("Images", images);
		mv.addObject("Symptom", pdsw.getSympton());
		mv.addObject("Morphology", pdsw.getForm());
		mv.addObject("Habit", pdsw.getHabbit());
		mv.addObject("Pattern", pdsw.getRule());
		mv.setViewName("BaikeDisaster");
		return mv;
	}

	public ModelAndView handlePesticide(ModelAndView mv, CPProductWrapper cpw) {
		mv.addObject("name", cpw.getName());
		mv.addObject("activeIngredient", cpw.getActiveIngredient());
		mv.addObject("type", cpw.getType());
		mv.addObject("crop", cpw.getCrop().replaceAll("\n", " "));
		mv.addObject("disease", cpw.getDisease().replaceAll("\n", " "));
		mv.addObject("volumn", cpw.getVolumn().replaceAll("\n", " "));
		mv.addObject("method", cpw.getMethod().replaceAll("\n", " "));
		mv.addObject("guideline", cpw.getGuideline());
		mv.addObject("registrationId", cpw.getRegistrationId());
		mv.addObject("manufacturer", cpw.getManufacturer());
		mv.addObject("tips", cpw.getTip());
		mv.setViewName("BaikePesticide");
		return mv;
	}

	public ModelAndView handleVariety(ModelAndView mv, VarietyWrapper vw) {
		mv.addObject("Name", vw.getVarietyName());
		mv.addObject("SubId", vw.getSubId());
		mv.addObject("ExaminationNumber", vw.getRegistrationId());
		mv.addObject("BreedingUnit", vw.getOwner());
		mv.addObject("SuitableRegion", vw.getSuitableArea());
		mv.addObject("Yield", vw.getYieldPerformance());
		mv.addObject("VarietyChacts", vw.getCharacteristics());
		mv.setViewName("BaikeVariety");
		return mv;
	}

}
