package fr.robot.twitterClient.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fr.robot.twitterClient.service.UserService;
import fr.robot.twitterClient.dataaccess.entity.User;
import fr.robot.twitterClient.web.dto.UserDto;

/**
 * @author Sparkle
 */
@RestController
public class RegistrationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private UserService userService;

	
	@RequestMapping(value = "/registration")
	public ModelAndView registerationLanding(final HttpServletRequest request){
		ModelAndView mav = new ModelAndView("register");
		mav.addObject("accountDto", new UserDto());
		return mav;
	}
	
	@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(@ModelAttribute("accountDto") @Valid final UserDto accountDto, final BindingResult result) {
		ModelAndView mav = new ModelAndView("register");
		if(result.hasErrors()){
			return mav;
		}
		try {
			final User registered = userService.registerNewUserAccount(accountDto);
			final String username = registered.getEmail();
			mav.addObject("SUCCESS", username + " registered successfully. ");
			mav.addObject("username", username);
		} catch (Exception e) {
			mav.addObject("ERROR", e.getMessage());
		}
		return mav;
	}
}
