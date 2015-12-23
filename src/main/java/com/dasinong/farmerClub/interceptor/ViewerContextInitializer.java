package com.dasinong.farmerClub.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dasinong.farmerClub.dao.IUserAccessTokenDao;
import com.dasinong.farmerClub.exceptions.InvalidAppAccessTokenException;
import com.dasinong.farmerClub.exceptions.InvalidUserAccessTokenException;
import com.dasinong.farmerClub.exceptions.UserAccessTokenExpiredException;
import com.dasinong.farmerClub.model.AppAccessToken;
import com.dasinong.farmerClub.model.AppAccessTokenManager;
import com.dasinong.farmerClub.model.User;
import com.dasinong.farmerClub.model.UserAccessToken;
import com.dasinong.farmerClub.model.UserAccessTokenManager;
import com.dasinong.farmerClub.util.HttpServletRequestX;
import com.dasinong.farmerClub.viewerContext.ViewerContext;

/**
 * 
 * @author xiahonggao
 *
 */
public class ViewerContextInitializer extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ViewerContext viewerContext = new ViewerContext();
		HttpServletRequestX requestX = new HttpServletRequestX(request);

		// Set deviceId if any
		String deviceId = requestX.getStringOptional("deviceId", null);
		if (deviceId != null) {
			viewerContext.setDeviceId(deviceId);
		}

		// Set appId if any
		// appId is passed if there is no login user
		// appId should not be passed if app/user access token is passed
		Long appId = requestX.getLongOptional("appId", null);
		if (appId != null) {
			viewerContext.setAppId(appId);
		}
	
		// Set version if any
		Integer version = requestX.getIntOptional("version", null);
		if (version != null) {
			viewerContext.setVersion(version);
		}

		String token = requestX.getStringOptional("accessToken", null);

		// initialize viewer context from session
		if (token == null || "".equals(token)) {
			// TODO (xiahonggao): deprecate session
			// response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			// return false;

			User user = (User) request.getSession().getAttribute("User");
			if (user != null) {
				viewerContext.setUserId(user.getUserId());
			}

			request.setAttribute(ViewerContext.REQUEST_KEY, viewerContext);
			return true;
		}

		// initialize viewer context from app access token
		if (this.isAppAccessTokenFormat(token)) {
			AppAccessTokenManager manager = new AppAccessTokenManager();
			try {
				AppAccessToken accessToken = manager.parse(token);
				viewerContext.setAppId(accessToken.getAppId());
			} catch (InvalidAppAccessTokenException ex) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return false;
			}

			request.setAttribute(ViewerContext.REQUEST_KEY, viewerContext);
			return true;
		}

		// initialize viewer context from user access token
		try {
			UserAccessTokenManager manager = new UserAccessTokenManager();
			UserAccessToken accessToken = manager.parse(token);
			viewerContext.setAppId(accessToken.getAppId());
			viewerContext.setUserId(accessToken.getUserId());

			manager.renew(accessToken);
		} catch (InvalidUserAccessTokenException ex) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return false;
		} catch (UserAccessTokenExpiredException ex) {
			// client side is supposed to show login page once 401 is seen
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		request.setAttribute(ViewerContext.REQUEST_KEY, viewerContext);
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (request.getAttribute(ViewerContext.REQUEST_KEY) != null) {
			request.removeAttribute(ViewerContext.REQUEST_KEY);
		}
	}

	private boolean isAppAccessTokenFormat(String token) {
		String[] parts = token.split("\\|");
		return parts != null && parts.length > 1;
	}
}
