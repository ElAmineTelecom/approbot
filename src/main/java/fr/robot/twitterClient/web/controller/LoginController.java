package fr.robot.twitterClient.web.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fr.robot.twitterClient.web.dto.UserDto;

@RestController
public class LoginController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = {"/login", "/logout"})
	public ModelAndView login(@RequestParam(value = "exceptions", required = false) String error, HttpServletRequest req,
			Principal currentUser) {
		ModelAndView mav = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			/* The user is logged in :) */
			mav.setViewName("redirect:/home");
		} else {
			mav.setViewName("login");
		}

		if (error != null) {
			String targetUrl = getRememberMeTargetUrlFromSession(req);
			if (StringUtils.hasText(targetUrl)) {
				mav.addObject("targetUrl", targetUrl);
				mav.addObject("loginUpdate", true);
			}
			AuthenticationException exception = (AuthenticationException) req.getSession()
					.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
			mav.addObject("error", true);
			mav.addObject("exception", exception.getMessage());
		}
		return mav;
	}

	/**
	 * get targetURL from session
	 */
	private String getRememberMeTargetUrlFromSession(HttpServletRequest request) {
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if (session != null) {
			targetUrl = session.getAttribute("targetUrl") == null ? "" : session.getAttribute("targetUrl").toString();
		}
		return targetUrl;
	}

}
