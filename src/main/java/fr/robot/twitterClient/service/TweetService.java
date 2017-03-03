package fr.robot.twitterClient.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twitter4j.DirectMessage;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.Query.ResultType;

@Service
public class TweetService {

	@Autowired
	private Twitter twitter;

	public void replyTweet(Status status, String text){
		StatusUpdate stat= new StatusUpdate(text);
	    stat.setInReplyToStatusId(status.getId());
	    
	    try {
			twitter.updateStatus(stat);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getLatestTweets() {
		List<String> tweets = new ArrayList<String>();
		try {
			ResponseList<Status> homeTimeline = twitter.getHomeTimeline();
			for (Status status : homeTimeline) {
				tweets.add(status.getText());
			}
		} catch (TwitterException e) {
			throw new RuntimeException(e);
		}
		return tweets;
	}

	public QueryResult searchTweets(String hashtag) {
		QueryResult result = null;
		Query query = new Query("#"+hashtag);
		query.setCount(100);
		query.setResultType(ResultType.recent);
		try {
			result = twitter.search(query);
		} catch (TwitterException e) {
		}
		return result;
	}

	public ResponseList<User> searchUsers(String searchText) {
		ResponseList<User> list = null;
		try {
			list = twitter.searchUsers(searchText, 10);
		} catch (TwitterException e) {
		}
		return list;
	}

	public User getUserByTwitterId(long tId) {
		User userInfo = null;
		try {
			userInfo = twitter.showUser(tId);
		} catch (TwitterException e) {
		}
		return userInfo;
	}

	public List<Status> getTweetsOfUser(String user) {
		List<Status> statuses = new ArrayList<Status>();
		try {
			if (user.length() > 0) {
				statuses = twitter.getUserTimeline(user);
			} else {
				user = twitter.verifyCredentials().getScreenName();
				statuses = twitter.getUserTimeline();
			}
		} catch (TwitterException te) {
		}
		return statuses;
	}

	public Status retweet(long id) {
		Status sInfo = null;
		try {
			sInfo = twitter.retweetStatus(id);
		} catch (TwitterException e) {
		}
		return sInfo;
	}
	
	public DirectMessage  sendMessage(String twitterName, String message){
		DirectMessage directMessage = null;
		try {
			directMessage = twitter.sendDirectMessage(twitterName, message);
		} catch (TwitterException e) {
		}
		return directMessage;
	}
	
	public ResponseList<DirectMessage> getUnreadMessage(){
		ResponseList<DirectMessage> list = null;
		try {
			list = twitter.getDirectMessages();
		} catch (TwitterException e) {
		}
		return  list;
	}

}
