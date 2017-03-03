package fr.robot.twitterClient.service.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.robot.twitterClient.service.HashTagService;
import fr.robot.twitterClient.service.TweetService;
import fr.robot.twitterClient.service.common.BusinessUtil;
import fr.robot.twitterClient.service.exceptions.BusinessException;
import fr.robot.twitterClient.dataaccess.HashTagRepository;
import fr.robot.twitterClient.dataaccess.entity.Hashtag;
import fr.robot.twitterClient.web.dto.UserRankinDto;
import twitter4j.QueryResult;
import twitter4j.Status;

@Service
public class HashTagServiceImpl implements HashTagService {


	private static final long serialVersionUID = 1L;
	@Autowired
	private HashTagRepository repo;
	@Autowired
	private TweetService tweetService;

	@Override
	public Collection<Hashtag> getAll() {
		return repo.findAll();
	}

	@Override
	public Hashtag saveOrUpdate(Hashtag object) throws BusinessException {
		return repo.save(object);
	}

	@Override
	public Hashtag get(Serializable id) throws BusinessException {
		return repo.getOne((Long) id);
	}

	@Override
	public void delete(Serializable id) throws BusinessException {
		repo.delete((Long) id);
	}

	@Override
	public Page<Hashtag> getAll(Pageable pageable) throws BusinessException {
		return null;
	}

	@Override
	public long tweetCountOfHashTag(String hashtag) {
		QueryResult result = tweetService.searchTweets(hashtag);
		return result.getTweets().size();
	}

	@Override
	public List<String> follwedHashTagList() {
		List<String> list = new ArrayList<String>();
		List<Hashtag> tagInfoList = repo.findAll();
		for (Hashtag hashtag : tagInfoList) {
			if (hashtag.isFollow()) {
				list.add(BusinessUtil.HASH + hashtag.getTag());
			}
		}
		return null;
	}

	@Override
	public Set<UserRankinDto> getUserRanking(String hashTag) {
		Set<UserRankinDto> uniqueSet  =  new HashSet<UserRankinDto>();
		QueryResult result = tweetService.searchTweets(hashTag);
		for (Status tweet : result.getTweets()) {
			UserRankinDto dto = new UserRankinDto();
			dto.setHashtag(hashTag);
			dto.setUserName(tweet.getUser().getScreenName());

			boolean found = false;
			for (UserRankinDto userRankinDto : uniqueSet) {
				if(userRankinDto.getUserName().equals(dto.getUserName())){
					userRankinDto.setCount(userRankinDto.getCount()+1);
					found = true;
					break;
				}
			}
			if(!found){
				dto.setCount(1l);
				uniqueSet.add(dto);
			}

		}
		return uniqueSet;
	}
}
