package quizDaoServices;

import java.util.*;
import quizClasses.*;
import quizDao.*;

public class ResultService {
	
		private ResultDao resultDao;
		
		public ResultService(ResultDao _quizDao) {
			this.resultDao = _quizDao;
		}
		//Napravi(sačuvaj) rezultat
		public void create(Quiz quiz, Result result) {
			resultDao.save(quiz,result);	
		}
		//Izbriši rezultat
		public void delete(Result result){
			resultDao.delete(result);
		}
		//Izbriši sve rezultate koji su u kolekciji
		public void delete(Collection<Result> result){
			resultDao.delete(result);
		}
		//Nađi sve rezultate
		public Collection<Result> findAll() {
			return resultDao.findAll();
		}
		//Nađi rezultat po id-u
		public Result findById(int id) {
			return resultDao.findById(id);
		}
		//Nađi rezultat po imenu korisnika
		public Collection<Result> findByFirstname(String firstname){
			return resultDao.findByFirstname(firstname);
		}
		
		//Update-uje rezultat, ako postoji na osnovu username-a iz
		//newResult i oldResult
		public void update(Result oldResult,Result newResult) {
			resultDao.update(oldResult, newResult);	
		}
		//Vraća usera asociranog sa proslijeđenim rezultatom
		public User getUser(Result result){
			return resultDao.getUser(result);
		}
		//Vraća kviz asociran sa proslijeđenim rezultatom
		public Quiz getQuiz(Result result){
			return resultDao.getQuiz(result);
		}
		//Dodaje na rezultat points bodova
		public void addPoints(Result result,int points){
			resultDao.addScore(result, points);
		}
		//Postavlja rezultat na points
		public void setPoints(Result result, int points){
			resultDao.setScore(result, points);
		}
		//Čisti poene rezultata (stavlja na 0)
		public void clearPoints(Result result){
			resultDao.clearScore(result);
		}
		//Pronalazi sve rezultate datog korisnika
		public Collection<Result>getUserResults(User user){
			return resultDao.getUserResults(user);
		}
		//Pronalazi sve rezultate kviza
		public Collection<Result>getQuizResults(Quiz quiz){
			return resultDao.getQuizResults(quiz);
		}
		
	}

