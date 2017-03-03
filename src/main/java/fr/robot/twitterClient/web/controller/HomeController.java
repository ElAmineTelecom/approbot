package fr.robot.twitterClient.web.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.robot.twitterClient.service.HashTagService;
import fr.robot.twitterClient.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;


import fr.robot.twitterClient.dataaccess.entity.Hashtag;
import fr.robot.twitterClient.web.dto.TweetDto;
import fr.robot.twitterClient.web.dto.UserRankinDto;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterStream;

@RestController
public class HomeController {

	@Autowired
	private TweetService tweetService;
	@Autowired
	private TwitterStream twitterStream;
	@Autowired
	private HashTagService hashTagService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, Principal currentUser, Model model) {
		ModelAndView mav = new ModelAndView("homepage");

		List<Hashtag> allList = (List<Hashtag>) hashTagService.getAll();
		List<Hashtag> followedList = allList.stream().filter(tagInfo -> tagInfo.isFollow() == true)
				.collect(Collectors.toList());
		mav.addObject("followedList", followedList);
		return mav;
	}

	@RequestMapping(value = "/saveTagtoSession/{tag}")
	public @ResponseBody List<Status> saveTagtoSession(@PathVariable final String tag, HttpServletRequest request) {
		request.getSession().setAttribute("currentHashTag", tag);
		QueryResult set = tweetService.searchTweets(tag);
		if (set.getTweets().size() > 0) {
			return set.getTweets();
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/tweets/downloadCSV")
	public void fooAsCSV(HttpServletResponse response, HttpServletRequest request) {
		String selectedTag = (String) request.getSession().getAttribute("currentHashTag");
		String csvFileName = "tweets.csv";
		response.setContentType("text/plain; charset=utf-8");
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
		response.setHeader(headerKey, headerValue);
		try {

			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			String[] header = { "sender", "message" };
			csvWriter.writeHeader(header);

			QueryResult set = tweetService.searchTweets("#" + selectedTag);
			for (Status status : set.getTweets()) {
				TweetDto dto = new TweetDto();
				dto.setSender(status.getUser().getScreenName());
				dto.setMessage(status.getText());

				csvWriter.write(dto, header);
			}

			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getUserRankingByHashtag/{tag}")
	public @ResponseBody Set<UserRankinDto> getUserRankingByHashtag(@PathVariable final String tag,
																	HttpServletRequest request) {
		Set<UserRankinDto> list = hashTagService.getUserRanking(tag);
		System.out.println(list);
		 /*Collections.sort(list, new Comparator<UserRankinDto>() {
	        public int compare(UserRankinDto o1, UserRankinDto o2) {
	            return o2.getCount().compareTo(o1.getCount());
	        }
	    });

		humans.sort(
			      (Human h1, Human h2) -> h1.getName().compareTo(h2.getName()));

		list.sort(
				(UserRankinDto a, UserRankinDto b) -> a.getCount().compareTo(b.getCount()));

		Collections.sort(list, new UserRankinDto.OrderByTweetCount());
		 */
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

}
