package fr.robot.twitterClient.tasks;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.robot.twitterClient.service.QuestionService;
import fr.robot.twitterClient.service.TweetService;
import twitter4j.DirectMessage;
import twitter4j.ResponseList;

@Component
public class TwitterChatTask {

	@Autowired
	private TweetService tweetService;
	@Autowired
	private QuestionService questionService;

	@Scheduled(fixedRate = 10000)
	public void twitterChat() {
		try {
			Date findAfter = new Date(System.currentTimeMillis() - 10000);
			ResponseList<DirectMessage> list = tweetService.getUnreadMessage();
			for (DirectMessage directMessage : list) {
				if (directMessage.getCreatedAt().after(findAfter)) {
					String answer = questionService.getAsnwerByQuestionSearch(directMessage.getText());
					if (answer != null) {
						tweetService.sendMessage(directMessage.getSenderScreenName(), answer);
					}
				}
			}
		} catch (Exception e) {
			
		}
	}
}
