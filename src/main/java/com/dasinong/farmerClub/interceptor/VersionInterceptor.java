package com.dasinong.farmerClub.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class VersionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO use a more proper parameter
		/*
		 * if ("IOS".equals(request.getParameter("clientVersion"))){ switch
		 * (request.getServletPath()){ case "/authRegLog":
		 * request.getRequestDispatcher("/nauthRegLog"); case "/login":
		 * request.getRequestDispatcher("/nlogin"); } } switch
		 * (request.getServletPath()){ case "/authRegLog":
		 * request.getRequestDispatcher("/nauthRegLog").forward(request,
		 * response); case "/login":
		 * request.getRequestDispatcher("/nlogin").forward(request, response); }
		 */
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
