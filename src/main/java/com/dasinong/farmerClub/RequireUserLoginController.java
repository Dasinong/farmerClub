package com.dasinong.farmerClub;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.dasinong.farmerClub.exceptions.UserIsNotLoggedInException;
import com.dasinong.farmerClub.viewerContext.ViewerContext;

public class RequireUserLoginController extends BaseController {

	@ModelAttribute
	public void assertUserIsLoggedIn(HttpServletRequest request) throws Exception {
		ViewerContext vc = this.getViewerContext(request);
		if (!vc.isUserLogin()) {
			throw new UserIsNotLoggedInException();
		}
	}

}
