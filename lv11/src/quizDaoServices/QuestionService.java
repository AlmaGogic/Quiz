package quizDaoServices;

import java.util.*;

import quizClasses.*;
import quizDao.*;


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
		
		public void add(Question question,Answer answer){
			questionDao.addAnswer(question, answer);
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

