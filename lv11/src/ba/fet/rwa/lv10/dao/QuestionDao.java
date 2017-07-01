package ba.fet.rwa.lv10.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import ba.fet.rwa.lv10.domain.Question;
import ba.fet.rwa.lv10.domain.Answer;


final public class QuestionDao extends AbstractDao {
	
	
		
		public QuestionDao() {
			
		}
		
		public List<Question> findAllQuestions() {
			EntityManager em = createEntityManager();
			Query q = em.createQuery("SELECT q FROM Question q");
			List<Question> resultList = q.getResultList();
			em.close();
			return resultList;
		}
		
		public Collection<Answer> findAllQuestionAnswers(Question question) {
			EntityManager em = createEntityManager();
			Query q = em.createQuery("SELECT q FROM Question q where q.id = :id ").setParameter("id", question.getQuestionId());
			Question currentQuestion = (Question) q.getSingleResult();
			Collection<Answer>resultList=currentQuestion.getAnswers();
			em.close();
			return resultList;
		}
		
		public void deleteAnswersFromQuestion(Question question) {
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
			
			Query q2 = em.createQuery("SELECT a FROM Answer a");
			Collection<Answer>listOfQuestionAnswers=q2.getResultList();
			for(Answer a : listOfQuestionAnswers){
				Collection<Question>questions=a.getQuestions();
				for(Question q : questions){
					if(q.getQuestionId()== question.getQuestionId()){
						a=em.merge(a);
						em.remove(a);
					}
				}
			}
			listOfQuestionAnswers.clear();
			for(Answer a : listOfQuestionAnswers){
				em.merge(a);
			}
			
			Query q1 = em.createQuery("SELECT q FROM Question q WHERE q.id = :id").setParameter("id", question.getQuestionId());
			Question retQuestion=(Question) q1.getSingleResult();
			Collection<Answer>listOfAnswers=retQuestion.getAnswers();
			
			listOfAnswers.clear();
			
			retQuestion=em.merge(retQuestion);
			em.remove(retQuestion);
			
			em.getTransaction().commit();
			em.close();	
		}
		
		public int findQuestionPoints(Question question){
			EntityManager em = createEntityManager();
			Query q = em.createQuery("SELECT q.points FROM Question q where q.id = :id ").setParameter("id", question.getQuestionId());
			int points =  (Integer) q.getSingleResult();
			return points;
		}
		
		public Collection<Answer> findCorrectAnswers(Question question){
			EntityManager em = createEntityManager();
			Query q = em.createQuery("SELECT q FROM Question q where q.id = :id ").setParameter("id", question.getQuestionId());
			Question retQuestion = (Question) q.getSingleResult();
			Collection<Answer>listOfAnswers=retQuestion.getAnswers();
			for(Answer currentAnswer : listOfAnswers){
				if(currentAnswer.getCorrectStatus()==false){
					listOfAnswers.remove(currentAnswer);
				}
			}
			em.close();
			return listOfAnswers;
		}
		
		public Collection<Question> findAnsweredQuestions(){
			EntityManager em = createEntityManager();
			Query q = em.createQuery("SELECT q FROM Question q where q.answered = :status ").setParameter("status", true);
			Collection<Question> retList = q.getResultList();
			
			em.close();
			return retList;
		}
		
		public Question findByText(String text) {
			EntityManager em = createEntityManager();
			try {
				Query q = em.createQuery("SELECT q FROM Question q WHERE q.text = :text").setParameter("text", text);
				Question question = (Question) q.getSingleResult();
				return question;					
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
			} finally {		
				if (em!= null) {
					em.close();
				}
			}		
			return null;
		}
		
		public void save(Question question, Answer answer1, Answer answer2) {
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
			Answer currentAnswer1= (Answer)em.find(Answer.class ,answer1.getId());
			Answer currentAnswer2= (Answer)em.find(Answer.class ,answer2.getId());
			if(currentAnswer1==null){
				em.persist(answer1);
				if(currentAnswer2==null){
					em.persist(answer2);
				}
				em.persist(question);
			}
			em.getTransaction().commit();
			em.close();	
		}
		public void update(Question question, Answer answer) {
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
			Question currentQuestion= (Question)em.find(Question.class ,question.getQuestionId());
			if(currentQuestion!=null){
				currentQuestion.setAnsweredStatus(question.getAnsweredStatus());
				currentQuestion.setQuestionPoints(question.getQuestionPoints());
				currentQuestion.setQuestionText(question.getQuestionText());
				Answer currentAnswer= (Answer)em.find(Answer.class ,answer.getId());
				if(currentAnswer!=null){
					currentAnswer.setAnswer(answer.getAnswer());
					currentAnswer.setCorrectStatus(answer.getCorrectStatus());
					em.merge(currentAnswer);
				}
				else{
					em.merge(answer);
				}
				

				em.merge(currentQuestion);
			}
			else{
				em.merge(question);
			}
			
			em.getTransaction().commit();
			em.close();	
		}
		
		public void deleteQuestion(Question question) {
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
			
			Collection<Answer> listOfAnswers = question.getAnswers();
			listOfAnswers.clear();
			
			question=em.merge(question);
			em.remove(question);
			
			em.getTransaction().commit();
			em.close();	
		}
	}


