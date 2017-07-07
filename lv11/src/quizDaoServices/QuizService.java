package quizDaoServices;

import java.util.*;
import quizClasses.*;
import quizDao.*;

public class QuizService {
	
		private QuizDao quizDao;
		
		public QuizService(QuizDao _quizDao) {
			this.quizDao = _quizDao;
		}
		//Napravi novi kviz koji ima jedno pitanje
		public void create(Quiz quiz, Question question) {
			quizDao.addQuestionToQuiz(quiz, question);	
		}
		//Izbriši kviz (pitanja i odgovori se ne brišu ako su u 
		//drugom kvizu) nije u potpunosti implementirano
		public void deleteQuiz(Quiz quiz){
			quizDao.deleteQuiz(quiz);
			
		}
		//Nađi sve kvizove
		public Collection<Quiz> findAll() {
			return quizDao.findAll();
		}
		//Nađi kviz po imenu
		public Collection<Quiz> findByName(String name){
			return quizDao.findByName(name);
		}
		public Quiz findOneByName(String name){
			Collection<Quiz> quizzes=quizDao.findByName(name);
			Quiz quiz = new Quiz();
			try{
				quiz=quizzes.iterator().next();
			}
			catch(Exception e){
				quiz=null;
				System.out.println(e.getMessage());
			}
			return quiz;
		}
		//Nađi kviz po id-u
		public Quiz findById(int quizId){
			return quizDao.findById(quizId);
		}
		//Nađi sva pitanja koja pripadaju zadanom kvizu
		public Collection<Question> findQuizQuestions(Quiz quiz){
			return quizDao.getQuizQuestions(quiz);
		}
		//Update-uje kviz sa datim imenom, na osnovu prosljeđenog
		//drugog kviza quiz
		public void update(String quizName, Quiz quiz) {
			quizDao.update(quizName, quiz, false);	
		}
		//Update-uje kviz sa datim imenom, na osnovu prosljeđenog
		//drugog kviza quiz i briše taj kviz (može se možda koristiti
		//ako recimo želimo spojiti dva kviza iz baze)
		public void updateAndDelete(String quizName, Quiz quiz) {
			quizDao.update(quizName, quiz, true);	
		}
		//Dodaje pitanje u kviz
		public void add(Quiz quiz, Question question) {
			quizDao.add(quiz, question);	
		}
		
		
	}

