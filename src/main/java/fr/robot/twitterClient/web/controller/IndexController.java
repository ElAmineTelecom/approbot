package fr.robot.twitterClient.web.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class IndexController {

	/**
	 * Simply selects the index view to render by returning its name.
	 */
	@RequestMapping(value = "/")
	public ModelAndView home(HttpServletRequest request, Principal currentUser, Model model) {
		ModelAndView mav = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			/* The user is logged in :) */
			mav.setViewName("redirect:/home");
		} else {
			mav.setViewName("redirect:/login");
		}
		
		return mav;
	}

}
