package fr.robot.twitterClient.web.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class QuestionDto {

	private Long id;
	
	@NotNull
	@Size(min = 5, max = 200)
	private String question;
	
	@NotNull
	@Size(min = 2, max = 300)
	private String answer;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
