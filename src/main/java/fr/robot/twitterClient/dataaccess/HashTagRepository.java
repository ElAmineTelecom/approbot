package fr.robot.twitterClient.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.robot.twitterClient.dataaccess.entity.Hashtag;

public interface HashTagRepository extends JpaRepository<Hashtag, Long> {

}
