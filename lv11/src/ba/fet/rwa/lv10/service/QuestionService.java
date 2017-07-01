package ba.fet.rwa.lv10.service;

import java.util.List;

import ba.fet.rwa.lv10.dao.QuestionDao;
import ba.fet.rwa.lv10.domain.Question;
import ba.fet.rwa.lv10.domain.Answer;


public class QuestionService {
	
		private QuestionDao questionDao;
		
		public QuestionService(QuestionDao qDao) {
			this.questionDao = qDao;
		}
		
		public void create(Question question, Answer answer1, Answer answer2) {
			question.addAnswer(answer1);
			question.addAnswer(answer2);
			questionDao.save(question, answer1, answer2);	
		}
		
		public List<Question> findAll() {
			return questionDao.findAllQuestions();
		}
		
		public Question findByText(String text) {
			return questionDao.findByText(text);
		}
		
	}


