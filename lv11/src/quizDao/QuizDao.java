package quizDao;

import java.util.*;
import javax.persistence.*;
import quizClasses.*;

final public class QuizDao extends AbstractDao {
	
	public List<Quiz> findAll() {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("SELECT q FROM Quiz q");
		List<Quiz> resultList = q.getResultList();
		em.close();
		return resultList;
	}
	
	public Collection<Quiz> findManyByName(String name) {
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
	
	public int numberOfQuestions(Quiz quiz){
		EntityManager em = createEntityManager();
		Quiz retQuiz=this.findByName(quiz.getQuizName());
		int size=0;
		if(retQuiz!=null){
			Collection<Question>questions=retQuiz.getQuestions();
			size=questions.size();
		}
		
		em.close();
		return size;
	}
	
	public Quiz findByName(String name) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT q FROM Quiz q WHERE q.quizName = :name").setParameter("name", name);
			Quiz quiz = (Quiz) q.getSingleResult();
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
		
		Query q0=em.createQuery("SELECT q from Quiz q WHERE q.quizName = :text").setParameter("text", q.getQuizName() );
		Quiz quiz = new Quiz();
		try{
			quiz=(Quiz)q0.getSingleResult();
		}
		catch(NoResultException e){
			quiz=null;
		}
		Query q1=em.createQuery("SELECT q FROM Question q WHERE q.text= :text").setParameter("text", question.getQuestionText());
		Question quest = new Question();
		try{
			quest = (Question) q1.getSingleResult();
		}
		catch(NoResultException e){
			quest=null;
		}
		boolean allowAdding=true;
		if(quiz!=null){
			Collection<Question>quizQuestions=quiz.getQuestions();
			for(Question qu : quizQuestions){
				if(qu.getQuestionText().equals(quest.getQuestionText())){
					//System.out.println(quest.getQuestionText()+","+qu.getQuestionText());		
					allowAdding=false;
					break;
					
				}
			}
			if(allowAdding){
				//quest.addToQuiz(quiz);
				quiz.addQuestion(quest);
				em.merge(quiz);
				//em.merge(quest);
			}
		}
		else{
			
			quiz = new Quiz();
			Query query=em.createQuery("SELECT e FROM Question e WHERE e.text = :text").setParameter("text", question.getQuestionText());
			Question qu=new Question();
			try{
				qu=(Question)query.getSingleResult();
			}
			catch(NoResultException e){
				qu=null;
			}
			quiz.setQuizName(q.getQuizName());
			quiz.setResults(q.getResults());
			if(qu!=null){
				quiz.addQuestion(qu);
				qu.addToQuiz(quiz);
				em.merge(quiz);
				em.merge(qu);
			}
			else{
				quiz.addQuestion(question);
				question.addToQuiz(quiz);
				em.merge(quiz);
				em.merge(question);
			}

		//	em.merge(question);
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
				Collection<Quiz> quizzes= this.findManyByName(quizName);
				
				if(!quizzes.isEmpty()){
					for(Quiz qz : quizzes){
						if(qz.getQuizName().equals(quizName)){
							Collection<Question> oldQuizQuestions =qz.getQuestions();
							Collection<Question> newQuizQuestions =quiz.getQuestions();
							for(Question q : newQuizQuestions){
								oldQuizQuestions.add(q);
								q.addToQuiz(qz);
								em.merge(q);
								
							}
							if(delete){
								this.deleteQuiz(quiz);
							}
							
						}
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
		
		//System.out.println(listOfQuizQuestions.size()+"-++++-");
		
		quiz=em.merge(quiz);
		em.remove(quiz);
		
		em.getTransaction().commit();
		em.close();	
	}
}