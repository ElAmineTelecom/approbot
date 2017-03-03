package fr.robot.twitterClient.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.robot.twitterClient.dataaccess.entity.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{
	public UserProfile findByUserId(String userId);
}
