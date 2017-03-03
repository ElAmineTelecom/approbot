package fr.robot.twitterClient.service.impl;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fr.robot.twitterClient.service.QuestionService;
import fr.robot.twitterClient.service.exceptions.BusinessException;
import fr.robot.twitterClient.dataaccess.QuestionRepository;
import fr.robot.twitterClient.dataaccess.entity.Question;

@Service
public class QuestionServiceImpl implements QuestionService {


	private static final long serialVersionUID = 1L;
	@Autowired
	private QuestionRepository repo;

	public Collection<Question> getAll() {
		return repo.findAll();
	}

	public Question saveOrUpdate(Question object) throws BusinessException {
		return repo.save(object);
	}

	public Question get(Serializable id) throws BusinessException {
		return repo.getOne((Long) id);
	}

	public void delete(Serializable id) throws BusinessException {
		repo.delete((Long) id);

	}

	public Page<Question> getAll(Pageable pageable) throws BusinessException {
		return repo.findAll(pageable);
	}
 
	@Override
	public Question getByQuestion(String question) {
		return repo.findByQuestion(question);
	}

	@Override
	public String getAsnwerByQuestionSearch(String search) {
		Question qsInfo = repo.findByQuestionLikeIgnoreCase(search);
		if(qsInfo != null){
			return qsInfo.getAnswer();
		}else {
			return null;
		}
	}

}
