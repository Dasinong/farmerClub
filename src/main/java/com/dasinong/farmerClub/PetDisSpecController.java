package com.dasinong.farmerClub;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;

import com.dasinong.farmerClub.facade.IPetDisSpecFacade;
import com.dasinong.farmerClub.facade.IPetSoluFacade;
import com.dasinong.farmerClub.util.HttpServletRequestX;

@Controller
public class PetDisSpecController extends RequireUserLoginController {

	IPetDisSpecFacade petDisSpecFacade;
	IPetSoluFacade petSoluFacade;

	@RequestMapping(value = "/getPetDisBySubStage", produces = "application/json")
	@ResponseBody
	public Object getPetDisBySubStage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		Long subStageId = requestX.getLong("subStageId");
		Long varietyId = requestX.getLongOptional("varietyId", -1L);

		petDisSpecFacade = (IPetDisSpecFacade) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecFacade");

		return petDisSpecFacade.getPetDisBySubStage(subStageId, varietyId);
	}

	@RequestMapping(value = "/getPetDisSpecDetial", produces = "application/json")
	@ResponseBody
	public Object getPetDisSpecDetial(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		Long petDisSpecId = requestX.getLong("petDisSpecId");

		petDisSpecFacade = (IPetDisSpecFacade) ContextLoader.getCurrentWebApplicationContext()
				.getBean("petDisSpecFacade");

		return petDisSpecFacade.getPetDisDetail(petDisSpecId);
	}

	@RequestMapping(value = "/getPetSolu", produces = "application/json")
	@ResponseBody
	public Object getPetSolu(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		Long petSoluId = requestX.getLong("petSoluId");

		petSoluFacade = (IPetSoluFacade) ContextLoader.getCurrentWebApplicationContext().getBean("petSoluFacade");
		return petSoluFacade.getPetSoluDetail(petSoluId);
	}
}
