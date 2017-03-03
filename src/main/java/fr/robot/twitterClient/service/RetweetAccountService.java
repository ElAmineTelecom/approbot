package fr.robot.twitterClient.service;

import java.util.Date;
import java.util.List;

import fr.robot.twitterClient.dataaccess.entity.RetweetAccount;
import twitter4j.User;

public interface RetweetAccountService extends GenericService<RetweetAccount>{

	public List<User> findRetweetUserList();
	
	public RetweetAccount findByTwitterId(Long tId);
	
	public int SendRetweet(Date date);
}
