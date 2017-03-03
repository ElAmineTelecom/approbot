package fr.robot.twitterClient.service;

import fr.robot.twitterClient.dataaccess.entity.Question;

public interface QuestionService extends GenericService<Question>{

	public Question getByQuestion(String question);
	
	public String getAsnwerByQuestionSearch(String search);
}
