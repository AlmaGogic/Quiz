package ba.fet.rwa.lv10.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ba.fet.rwa.lv10.domain.Answer;
import ba.fet.rwa.lv10.domain.Question;
import ba.fet.rwa.lv10.domain.Role;


final public class AnswerDao extends AbstractDao {
	
	
		
		public void AnswerDao() {
			
		}
		
		public List<Answer> findAllAnswers() {
			EntityManager em = createEntityManager();
			Query q = em.createQuery("SELECT a FROM Answer a");
			List<Answer> resultList = q.getResultList();
			em.close();
			return resultList;
		}
		
		
		
		public Collection<Answer> findByText(String text) {
			EntityManager em = createEntityManager();
			try {
				Query q = em.createQuery("SELECT a FROM Answer a WHERE a.text = :text").setParameter("text", text);
				Collection<Answer> answers = new ArrayList<Answer>();
				
				answers=(Collection<Answer>)q.getResultList();
				
				return answers;					
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
			} finally {		
				if (em!= null) {
					em.close();
				}
			}		
			return null;
		}
		
		public void save(Answer answer) {
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
			Answer currentAnswer= (Answer)em.find(Answer.class ,answer.getId());
			if(currentAnswer!=null){
				em.persist(answer);
			}
			em.getTransaction().commit();
			em.close();	
		}
		
		public void update(String questionName,String answerName,Answer answer) {
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
			QuestionDao questionDao = new QuestionDao();
			
			Question question = questionDao.findByText(questionName);
			Collection<Answer>answers=question.getAnswers();
			for(Answer currentAnswer : answers){
				if(currentAnswer.getAnswer().equals(answerName)){
					currentAnswer.setAnswer(answer.getAnswer());
					currentAnswer.setCorrectStatus(answer.getCorrectStatus());
					em.merge(currentAnswer);
					break;
				}
			}
			
			em.getTransaction().commit();
			em.close();	
		}
		public void deleteAnswer(Answer answer) {
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
			
			Collection<Question> listOfQuestions = answer.getQuestions();
			listOfQuestions.clear();
			
			answer=em.merge(answer);
			em.remove(answer);
			
			em.getTransaction().commit();
			em.close();	
		}
		
	}

