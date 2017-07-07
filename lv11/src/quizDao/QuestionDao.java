package quizDao;

import java.util.*;
import javax.persistence.*;
import quizClasses.*;


final public class QuestionDao extends AbstractDao {
		
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
			Question currentQuestion = new Question();
			try{
				currentQuestion=(Question)q.getSingleResult();
			}
			catch(NoResultException e){
				currentQuestion=null;
			}
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
			Question retQuestion = new Question();
			try{
				retQuestion=(Question)q1.getSingleResult();
			}
			catch(NoResultException e){
				retQuestion=null;
			}
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
			int points;
			try{
				points=(Integer)q.getSingleResult();
			}
			catch(NoResultException e){
				points=0;
			}
			return points;
		}
		
		public Collection<Answer> findCorrectAnswers(Question question){
			EntityManager em = createEntityManager();
			Query q = em.createQuery("SELECT q FROM Question q where q.id = :id ").setParameter("id", question.getQuestionId());
			Question retQuestion = new Question();
			Collection<Answer>listOfAnswers=new ArrayList<Answer>();
			Collection<Answer>listOfCorrectAnswers=new ArrayList<Answer>();
			try{
				retQuestion= (Question) q.getSingleResult();
			}
			catch(NoResultException e){
				retQuestion=null;
			}
			if(retQuestion!=null){
				listOfAnswers=retQuestion.getAnswers();
				for(Answer currentAnswer : listOfAnswers){
					//System.out.println(currentAnswer.getCorrectStatus());
					if(currentAnswer.getCorrectStatus()==true){

						listOfCorrectAnswers.add(currentAnswer);
					}
				}
			}
			em.close();
			return listOfCorrectAnswers;
		}
		
		public int numberOfAnswers(Question question){
			EntityManager em = createEntityManager();
			Question q=this.findByText(question.getQuestionText());
			int size=0;
			if(q!=null){
				Collection<Answer>answers=q.getAnswers();
				size=answers.size();
			}
			return size;
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
				Question question = new Question();
				try{
					question=(Question)q.getSingleResult();
				}
				catch(NoResultException e){
					question=null;
				}
				//System.out.println("Answers size:" +question.getAnswers().size());
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
		
		public void addAnswer(Question question,Answer answer){
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
			
			boolean addingAllowed=true;
			Question q=this.findByText(question.getQuestionText());
			if(q!=null){
				Collection<Answer> answers = q.getAnswers();
				for(Answer a : answers){
					if(a.getAnswer().equals(answer.getAnswer())){
						addingAllowed=false;
						break;
					}
				}
				if(addingAllowed){
					q.addAnswer(answer);
					answer.addToQuestion(question);
					em.merge(answer);
					em.merge(q);
				}
			}
			
			em.getTransaction().commit();
			em.close();	
			
		}
		
		public void save(Question question, Answer answer1, Answer answer2) {
			//System.out.println("AA");
				
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
					
			Question currentQuestion = this.findByText(question.getQuestionText());
			boolean addSecond=true;
			boolean addFirst=true;
			if(currentQuestion!=null){
				Collection<Answer> currentAnswers=currentQuestion.getAnswers();
				for(Answer a : currentAnswers){
					if(a.getAnswer().equals(answer1.getAnswer())){
						addFirst=false;	
					}
					if(a.getAnswer().equals(answer2.getAnswer())){
						addSecond=false;
					}
				}
				if(addFirst){
					answer1.addToQuestion(currentQuestion);
					currentQuestion.addAnswer(answer1);
					em.merge(answer1);
				}
				if(addSecond){
					answer2.addToQuestion(currentQuestion);
					currentQuestion.addAnswer(answer2);
					em.merge(answer2);
				}

				em.merge(currentQuestion);
				
			}
			if(currentQuestion==null){
				//System.out.println("AAA");
				
				Question q1=new Question();
				q1.setQuestionPoints(question.getQuestionPoints());
				q1.setAnsweredStatus(question.getAnsweredStatus());
				q1.setQuestionText(question.getQuestionText());
				answer1.addToQuestion(q1);
				
				q1.addAnswer(answer1);
				em.merge(answer1);
				answer2.addToQuestion(q1);
				q1.addAnswer(answer2);

				em.merge(answer2);
				em.merge(q1);
				//System.out.println(q1.getNumberOfAnswers());
				
			}
					
			em.getTransaction().commit();
			em.close();	
					
		}
	
		
		public void update(String questionName,Question question) {
			EntityManager em = createEntityManager();
			em.getTransaction().begin();
			Question currentQuestion= this.findByText(questionName);
			//
			if(currentQuestion!=null){
				//System.out.println("Q: "+currentQuestion.getQuestionText());
				currentQuestion.setAnsweredStatus(question.getAnsweredStatus());
				currentQuestion.setQuestionPoints(question.getQuestionPoints());
				currentQuestion.setQuestionText(question.getQuestionText());
				Collection<Answer>answers=question.getAnswers();
				Collection<Answer>currentAnswers=currentQuestion.getAnswers();
				boolean updateQuestion=true;
				for(Answer answer : answers){
					//System.out.println("ljflsdkfjsl"+currentAnswers.size());
					for(Answer curAnswer : currentAnswers){
						//System.out.println(curAnswer.getAnswer()+",mfljnfsa");
						if(curAnswer.getAnswer().equals(answer.getAnswer())){
							updateQuestion=false;
							break;
						}
					}
					if(updateQuestion){
						Answer asw=new Answer();
						asw.setCorrectStatus(answer.getCorrectStatus());
						asw.setAnswer(answer.getAnswer());
						currentQuestion.addAnswer(asw);
						asw.addToQuestion(currentQuestion);
						
						em.merge(asw);
						em.merge(currentQuestion);
					}
					
				}

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

		public Question findById(int questionId) {
			EntityManager em = createEntityManager();
			try {
				Query q = em.createQuery("SELECT q FROM Question q WHERE q.id = :id").setParameter("id", questionId);
				Question question = new Question();
				try{
					question=(Question)q.getSingleResult();
				}
				catch(NoResultException e){
					question=null;
				}
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
	}
