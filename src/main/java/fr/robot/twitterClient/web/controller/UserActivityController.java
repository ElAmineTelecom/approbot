package fr.robot.twitterClient.web.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.robot.twitterClient.service.HashTagService;
import fr.robot.twitterClient.dataaccess.entity.Hashtag;


@Controller
@RequestMapping(value = "/user")
public class UserActivityController {
	
	public static final  String	HASHTAG_VIEW_NAME = "hashtag";
	
	@Autowired private HashTagService hashTagService;

	@RequestMapping(value = "/hashtags", method = RequestMethod.GET)
	public ModelAndView listHashtags(HttpServletRequest request, Principal currentUser, Model model) {
		ModelAndView mav = new ModelAndView(HASHTAG_VIEW_NAME);
		List<Hashtag> allList = (List<Hashtag>) hashTagService.getAll();
		List<Hashtag> followedList = allList.stream().filter(tagInfo ->tagInfo.isFollow() == true).collect(Collectors.toList());
		mav.addObject("followedList", followedList);
		return mav;
	}

}
