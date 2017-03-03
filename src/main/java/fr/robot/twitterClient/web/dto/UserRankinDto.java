package fr.robot.twitterClient.web.dto;

import java.util.Comparator;

public class UserRankinDto {

	private String userName;
	
	private String hashtag;
	
	private Long count;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
	
	
	
	public static class OrderByTweetCount implements Comparator<UserRankinDto> {

	    @Override
	    public int compare(UserRankinDto o1, UserRankinDto o2) {
	        return o1.getCount() > o2.getCount() ? 1 : (o1.getCount() < o2.getCount() ? -1 : 0);
	    }
	}

	
}
