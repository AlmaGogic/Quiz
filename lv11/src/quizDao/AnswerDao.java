package quizDao;

import java.util.*;

import javax.persistence.*;

import quizClasses.*;


final public class AnswerDao extends AbstractDao {
	
	
		
		public AnswerDao() {
			
		}
		
		public Collection<Answer> findAllAnswers() {
			EntityManager em = createEntityManager();
			Query q = em.createQuery("SELECT a FROM Answer a");
			Collection<Answer> resultList = q.getResultList();
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
			Answer currentAnswer= em.find(Answer.class ,answer.getId());
			if(currentAnswer!=null){
				em.persist(answer);
			}
			em.getTransaction().commit();
			em.close();	
		}
		
		public void update(Question question,Answer answer3,Answer answer) {
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
			QuestionDao questionDao = new QuestionDao();
			
			Question q1=questionDao.findByText(question.getQuestionText());
			Collection<Answer>answers=q1.getAnswers();
			//System.out.println(answers.size());
			boolean addAnswer=true;
			//boolean changeAnswer=false;
			//boolean found=false;
			for(Answer currentAnswer : answers){
				//System.out.println(currentAnswer.getAnswer()+" "+answer.getAnswer()+" "+answer3.getAnswer());
				if(currentAnswer.getAnswer().equals(answer.getAnswer())){
					//System.out.println("?");
				    //changeAnswer=true;
					addAnswer=false;
					break;
				}
			}
			for(Answer currentAnswer : answers){
				//System.out.println(":-) "+currentAnswer.getAnswer()+","+answer.getAnswer());
				if(currentAnswer.getAnswer().equals(answer3.getAnswer())){
					
					if(addAnswer){
						//System.out.println(":-| "+currentAnswer.getAnswer()+","+answer.getAnswer());
						currentAnswer.setAnswer(answer.getAnswer());
						//System.out.println(":-( "+currentAnswer.getAnswer()+","+answer.getAnswer());	
						currentAnswer.setCorrectStatus(answer.getCorrectStatus());
						//currentAnswer.addToQuestion(q1);
						//q1.addAnswer(currentAnswer);
						em.merge(currentAnswer);
						
					
						break;
					}
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

