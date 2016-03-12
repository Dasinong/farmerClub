package com.dasinong.farmerClub.mvc;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.dasinong.farmerClub.BaseController;
import com.dasinong.farmerClub.dao.IUserDao;
import com.dasinong.farmerClub.model.User;

public class DarenJifenController extends BaseController implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		try {
			Long userId = Long.parseLong(request.getParameter("userId"));
			IUserDao userDao = (IUserDao) ContextLoader.getCurrentWebApplicationContext().getBean("userDao");
			User user = userDao.findById(userId);
            if (user!=null){
            	mv.addObject("MemberPoints",user.getMemberPoints());
            	mv.addObject("Avatar",user.getPictureId());
            	mv.setViewName("DarenJifen");
            }
		} catch (Exception e){
			
		}
		return mv;
	}

}
