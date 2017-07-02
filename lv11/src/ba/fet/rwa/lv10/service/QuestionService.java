package ba.fet.rwa.lv10.service;

import java.util.Collection;
import java.util.List;

import ba.fet.rwa.lv10.dao.QuestionDao;
import ba.fet.rwa.lv10.domain.Question;
import ba.fet.rwa.lv10.domain.Answer;


public class QuestionService {
	

		
		private QuestionDao questionDao;
		
		public QuestionService(QuestionDao _qustionDao) {
			this.questionDao = _qustionDao;
		}
		
		public int getQuestionPoints(Question question){
			return questionDao.findQuestionPoints(question);
		}
		
		public void update(String questionName, Question question) {
			questionDao.update(questionName,question);	
		}
		
		public void create(Question question, Answer answer1, Answer answer2) {
			questionDao.save(question,answer1,answer2);	
		}
		
		public void deleteQuestion(Question question){
			questionDao.deleteQuestion(question);
		}
		
		public Collection<Question> findAll() {
			return questionDao.findAllQuestions();
		}
		public void clearQuestion(Question question){
			questionDao.deleteAnswersFromQuestion(question);
		}
		public Collection<Question> findAnswered(){
			return questionDao.findAnsweredQuestions();
		}
		
		public Collection<Answer> findCorrectAnswers(Question question){
			return questionDao.findCorrectAnswers(question);
		}
		
		public Question findByText(String text) {
			return questionDao.findByText(text);
		}

		public Question findById(int questionId) {
			return questionDao.findById(questionId);
		}
		
	}

