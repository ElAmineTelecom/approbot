package fr.robot.twitterClient.service;

import java.util.List;
import java.util.Set;

import fr.robot.twitterClient.dataaccess.entity.Hashtag;
import fr.robot.twitterClient.web.dto.UserRankinDto;

public interface HashTagService extends GenericService<Hashtag>{

	public long tweetCountOfHashTag(String hashtag);

	public List<String> follwedHashTagList();

	public Set<UserRankinDto> getUserRanking(String hashTag);
}
