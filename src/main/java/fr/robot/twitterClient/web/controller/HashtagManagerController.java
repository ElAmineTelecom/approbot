package fr.robot.twitterClient.web.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.robot.twitterClient.service.HashTagService;
import fr.robot.twitterClient.dataaccess.entity.Hashtag;


@Controller
@RequestMapping(value = "/admin")
public class HashtagManagerController {

	@Autowired private HashTagService hashTagService;
	
	public static final  String	VIEW_NAME = "hashtag-mgr";
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/manageHashtags", method = RequestMethod.GET)
	public ModelAndView retweetAccountsHome(HttpServletRequest request, Principal currentUser, Model model) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject("hashTagList", hashTagService.getAll());
		return mav;
	}
	
	@RequestMapping(value = "/searchHashtag", method = RequestMethod.POST)
	public ModelAndView searchOnTwitter(@RequestParam final String searchText, HttpServletRequest request, Principal currentUser) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject("tweetCount",hashTagService.tweetCountOfHashTag(searchText));
		mav.addObject("searchText", searchText);
		return mav;
	}
	
	
	@RequestMapping(value = "/addToFollow/{hashTag}")
	public @ResponseBody String addToRetweet(@PathVariable final String hashTag,HttpServletRequest request) {
		try {
			Hashtag hashtInfo = new Hashtag();
			hashtInfo.setFollow(true);
			hashtInfo.setTag(hashTag);
			hashTagService.saveOrUpdate(hashtInfo);
			return "Now Following hashtag "+hashTag;
		} catch (Exception e) {
			return "Error ! Unable to follow Hashtag";
		}
	}
	
	@RequestMapping(value = "/changeHashtagStatus")
	public  ModelAndView changeRetweetStatus(@RequestParam final long id, RedirectAttributes redirectAttributes) {
		try {
			Hashtag hashtInfo = hashTagService.get(id);
			if(hashtInfo.isFollow() == false){
				hashtInfo.setFollow(true);
			}else {
				hashtInfo.setFollow(false);
			}
			hashTagService.saveOrUpdate(hashtInfo);
			redirectAttributes.addFlashAttribute("SUCCESS",  hashtInfo.getTag()+": Hashtag status changed ");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("ERROR", "Error ! Unable to change Hashtag status.");
		}
		return new ModelAndView("redirect:/admin/manageHashtags");
	}
	
	@RequestMapping(value = "/deleteHashtag")
	public  ModelAndView deleteRetweetAccount(@RequestParam final long id, HttpServletRequest request, RedirectAttributes redirectAttributes ) {
		try {
			hashTagService.delete(id);
			redirectAttributes.addFlashAttribute("SUCCESS", "Record deleted successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("ERROR", "Error "+e.getMessage());
		}
		return new ModelAndView("redirect:/admin/manageHashtags");
	}

}
