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

import fr.robot.twitterClient.service.RetweetAccountService;
import fr.robot.twitterClient.service.TweetService;
import fr.robot.twitterClient.dataaccess.entity.RetweetAccount;


@Controller
@RequestMapping(value = "/admin")
public class RetweetAccountController {

	@Autowired private TweetService tweetService;
	@Autowired private RetweetAccountService retweetAccountService; 
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/retweetAccounts", method = RequestMethod.GET)
	public ModelAndView retweetAccountsHome(HttpServletRequest request, Principal currentUser, Model model) {
		ModelAndView mav = new ModelAndView("retweetAccounts");
		mav.addObject("retweetList", retweetAccountService.getAll());
		return mav;
	}
	
	@RequestMapping(value = "/searchUsers", method = RequestMethod.POST)
	public ModelAndView searchOnTwitter(@RequestParam final String searchText, HttpServletRequest request, Principal currentUser) {
		ModelAndView mav = new ModelAndView("retweetAccounts");
		mav.addObject("searchList",tweetService.searchUsers(searchText));
		return mav;
	}
	
	
	@RequestMapping(value = "/addToRetweet/{twitterId}/{name}")
	public @ResponseBody String addToRetweet(@PathVariable final long twitterId, @PathVariable final String name,HttpServletRequest request) {
		try {
			RetweetAccount rtInfo = new RetweetAccount();
			rtInfo.setTwitterId(twitterId);
			rtInfo.setName(name);
			rtInfo.setRetweet(true);
			retweetAccountService.saveOrUpdate(rtInfo);
			return name+" added to Retweet Accounts";
		} catch (Exception e) {
			return "Error ! Unable to add Retweet Accounts.";
		}
	}
	
	@RequestMapping(value = "/changeRetweetStatus")
	public  ModelAndView changeRetweetStatus(@RequestParam final long id, RedirectAttributes redirectAttributes) {
		try {
			RetweetAccount rtInfo = retweetAccountService.get(id);
			if(rtInfo.isRetweet() == false){
				rtInfo.setRetweet(true);
			}else{
				rtInfo.setRetweet(false);
			}
			retweetAccountService.saveOrUpdate(rtInfo);
			redirectAttributes.addFlashAttribute("SUCCESS",  rtInfo.getName()+": Retweet Accounts status changed ");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("ERROR", "Error ! Unable to  change Retweet status.");
		}
		return new ModelAndView("redirect:/admin/retweetAccounts");
	}
	
	@RequestMapping(value = "/deleteRetweetAccount")
	public  ModelAndView deleteRetweetAccount(@RequestParam final long id, HttpServletRequest request, RedirectAttributes redirectAttributes ) {
		
		try {
			retweetAccountService.delete(id);
			redirectAttributes.addFlashAttribute("SUCCESS", "Record deleted successfully.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("ERROR", "Error "+e.getMessage());
		}
		return new ModelAndView("redirect:/admin/retweetAccounts");
	}

}
