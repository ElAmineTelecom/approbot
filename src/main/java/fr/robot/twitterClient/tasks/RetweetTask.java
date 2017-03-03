package fr.robot.twitterClient.tasks;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.robot.twitterClient.service.RetweetAccountService;

@Component
public class RetweetTask {

	@Autowired
	private RetweetAccountService retweetAccountService;

	@Scheduled(fixedRate = 60000 * 10)
	public void retweetAccounts() {
		try {
			retweetAccountService.SendRetweet(new Date(System.currentTimeMillis() - (60000 * 10)));
		} catch (Exception e) {

		}

	}
}
