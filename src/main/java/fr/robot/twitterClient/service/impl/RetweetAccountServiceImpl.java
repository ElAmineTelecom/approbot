package fr.robot.twitterClient.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.robot.twitterClient.service.RetweetAccountService;
import fr.robot.twitterClient.service.TweetService;
import fr.robot.twitterClient.service.exceptions.BusinessException;
import fr.robot.twitterClient.dataaccess.RetweetAccountRepository;
import fr.robot.twitterClient.dataaccess.entity.RetweetAccount;
import twitter4j.Status;
import twitter4j.User;

@Service
public class RetweetAccountServiceImpl implements RetweetAccountService{


	private static final long serialVersionUID = 1L;
	@Autowired private RetweetAccountRepository repo;
	@Autowired private TweetService tweetService;
	
	public Collection<RetweetAccount> getAll() {
		return repo.findAll();
	}

	public RetweetAccount saveOrUpdate(RetweetAccount object) throws BusinessException {
		return repo.save(object);
	}

	public RetweetAccount get(Serializable id) throws BusinessException {
		return repo.getOne((Long) id);
	}

	public void delete(Serializable id) throws BusinessException {
		repo.delete((Long) id);
		
	}

	public Page<RetweetAccount> getAll(Pageable pageable) throws BusinessException {
		return repo.findAll(pageable);
	}

	public List<User> findRetweetUserList() {
		List<User> retweetList = new ArrayList<User>();
		List<RetweetAccount> list = repo.findAll();
		for (RetweetAccount retweetAccount : list) {
				User userInfo = tweetService.getUserByTwitterId(retweetAccount.getTwitterId());
				if(userInfo != null){
					retweetList.add(userInfo);
				}
			
		}
		return retweetList;
	}

	public RetweetAccount findByTwitterId(Long tId) {
		return repo.findByTwitterId(tId);
	}
 
	public int SendRetweet(Date date) {
		int count = 0;
		List<RetweetAccount> list = repo.findAll();
		for (RetweetAccount retweetAccount : list) {
		 if(retweetAccount.isRetweet()){ 
				List<Status> tweets= tweetService.getTweetsOfUser(retweetAccount.getName());
				for (Status status : tweets) {
					if(status.getCreatedAt().after(date)){
						tweetService.retweet(status.getId());
					}
				}
			
		 }
		}
		return count;
	}
}
