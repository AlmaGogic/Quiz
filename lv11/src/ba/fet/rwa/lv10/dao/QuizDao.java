package ba.fet.rwa.lv10.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ba.fet.rwa.lv10.domain.Answer;
import ba.fet.rwa.lv10.domain.Question;
import ba.fet.rwa.lv10.domain.Quiz;

final public class QuizDao extends AbstractDao {
	
	public void QuizDao() {
		
	}
	
	public List<Quiz> findAll() {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("SELECT q FROM Quiz q");
		List<Quiz> resultList = q.getResultList();
		em.close();
		return resultList;
	}
	
	public Collection<Quiz> findByName(String name) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT q FROM Quiz q WHERE q.quizName = :name").setParameter("name", name);
			Collection<Quiz> quiz = (Collection<Quiz>) q.getResultList();
			return quiz;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
	}
	
	public Quiz findById(int id) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT q FROM Quiz q WHERE q.id = :id").setParameter("id", id);
			Quiz quiz = new Quiz();
			try{
				quiz=(Quiz)q.getSingleResult();
			}
			catch(NoResultException e){
				quiz=null;
			}
			return quiz;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
	}
	
	public Collection<Question> getQuizQuestions(Quiz q){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Query q1 = em.createQuery("SELECT q FROM Quiz q WHERE q.id = :id").setParameter("id", q.getQuizId());
		Quiz quiz = new Quiz();
		try{
			quiz=(Quiz)q1.getSingleResult();
		}
		catch(NoResultException e){
			quiz=null;
		}
		Collection<Question>listOfQuestions = quiz.getQuestions();
		
		em.getTransaction().commit();
		em.close();	
		return listOfQuestions;
	} 
	
	public void addQuestionToQuiz(Quiz q,Question question){
		
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		

		Quiz quiz= (Quiz)em.find(Quiz.class ,q.getQuizId());
		Query q1=em.createQuery("SELECT q FROM Question q WHERE q.text= :text").setParameter("text", question.getQuestionText());
		Question quest = (Question) q1.getSingleResult();
		if(quiz!=null){
			if(!quiz.getQuestions().contains(quest)||quiz.getQuestions().contains(null)){
				quiz.addQuestion(quest);
				em.merge(quiz);
			}
		}
		else{
			em.merge(q);
		}
		
		em.getTransaction().commit();
		em.close();	
		
	}
	
	public void add(Quiz quiz, Question question) {
		this.addQuestionToQuiz(quiz, question);
		
	}
	
	public void update(String quizName, Quiz quiz,boolean delete) {
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		Collection<Quiz> quizzes= this.findByName(quizName);
		
		if(!quizzes.isEmpty()){
			Quiz currentQuiz=quizzes.iterator().next();
			Collection<Question> oldQuizQuestions =currentQuiz.getQuestions();
			Collection<Question> newQuizQuestions =quiz.getQuestions();
			for(Question q : newQuizQuestions){
				oldQuizQuestions.add(q);
				q.addToQuiz(currentQuiz);
				em.merge(q);
			}
			if(delete){
				this.deleteQuiz(quiz);
			}
		}		
		em.getTransaction().commit();
		em.close();	
	}
	
	public void deleteQuiz(Quiz quiz) {

		EntityManager em = createEntityManager();
		em.getTransaction().begin();

		Quiz quizRet =this.findById(quiz.getQuizId());
		Collection<Question> questions=this.getQuizQuestions(quiz);
		Collection<Question> listOfQuizQuestions = quizRet.getQuestions();
		for(Question question : questions){
			if(question.getQuizzes().size()==1&&question.getQuizzes().iterator().next().getQuizName().equals(quizRet.getQuizName())){
				Collection<Answer> listOfAnswers = question.getAnswers();
				Query q = em.createQuery("SELECT a FROM Answer a");
				List<Answer> answers = q.getResultList();
				for(Answer a1 : listOfAnswers){
					for(Answer a2 : answers){
						if(a1.getId()==a2.getId()){
							Collection<Question> listOfQuestions =a2.getQuestions();
							listOfQuestions.clear();
							
							a2=em.merge(a2);
							em.remove(a2);
						}
					}
				}
				listOfAnswers.clear();
				
				question=em.merge(question);
				em.remove(question);
			
			}
		}

		listOfQuizQuestions.clear();
		
		System.out.println(listOfQuizQuestions.size()+"-++++-");
		
		quiz=em.merge(quiz);
		em.remove(quiz);
		
		em.getTransaction().commit();
		em.close();	
	}
}