package fr.robot.twitterClient.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.robot.twitterClient.dataaccess.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	public Question findByQuestion(String question);
	
	public Question findByQuestionLikeIgnoreCase(String question);
}
