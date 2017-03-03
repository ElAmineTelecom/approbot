package fr.robot.twitterClient.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.robot.twitterClient.dataaccess.entity.RetweetAccount;

@Repository
public interface RetweetAccountRepository extends JpaRepository<RetweetAccount, Long> {
	RetweetAccount findByTwitterId(Long tId);
}
