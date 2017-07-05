package ba.fet.rwa.lv10.service;

import java.util.Collection;
import java.util.List;

import ba.fet.rwa.lv10.dao.AnswerDao;
import ba.fet.rwa.lv10.domain.Answer;
import ba.fet.rwa.lv10.domain.Question;


public class AnswerService {
	

		
		private AnswerDao answerDao;
		
		public AnswerService(AnswerDao _answerDao) {
			this.answerDao = _answerDao;
		}
		
		public void create(Answer answer) {
			answerDao.save(answer);	
		}
		
		public List<Answer> findAll() {
			return answerDao.findAllAnswers();
		}
		
		public Collection<Answer> findByText(String text) {
			return answerDao.findByText(text);
		}
		
		public void updateAnswer(Question question,Answer answer3,Answer answer){
			answerDao.update(question,answer3,answer);
		}
		public void deleteAnswer(Answer answer){
			answerDao.deleteAnswer(answer);
		}
		
		
	}
