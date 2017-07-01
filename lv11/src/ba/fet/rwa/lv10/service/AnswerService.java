package ba.fet.rwa.lv10.service;

import java.util.List;

import ba.fet.rwa.lv10.dao.AnswerDao;
import ba.fet.rwa.lv10.domain.Answer;


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
		
		public Answer findByText(String text) {
			return answerDao.findByText(text);
		}
		
		public void updateAnswer(Answer answer){
			answerDao.update(answer);
		}
		public void deleteAnswer(Answer answer){
			answerDao.deleteAnswer(answer);
		}
		
		
	}
