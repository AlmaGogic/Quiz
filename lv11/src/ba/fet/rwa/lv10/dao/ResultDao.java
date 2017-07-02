package ba.fet.rwa.lv10.dao;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ba.fet.rwa.lv10.domain.Quiz;
import ba.fet.rwa.lv10.domain.Result;
import ba.fet.rwa.lv10.domain.User;
import ba.fet.rwa.lv10.util.SecurityUtil;

final public class ResultDao extends AbstractDao {
	
	public void RoleDao() {
		
	}
	
	public Collection<Result> findAll() {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("SELECT r FROM Result r");
		Collection<Result> resultList = q.getResultList();
		em.close();
		return resultList;
	}
	
	public Collection<Result> findByFirstname(String firstname) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT r FROM Result r WHERE r.firstName = :firstName").setParameter("firstName", firstname);
			Collection<Result> result=(Collection<Result>)q.getResultList();
			
			return result;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
	}
	
	public Result findByHash(String hash) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT r FROM Result r");
			Collection<Result> results=(Collection<Result>)q.getResultList();
			for(Result result : results){
				if(result.getResultHash().equals(hash)){
					System.out.println(result.getResultHash());
					return result;
				}
			}
			
								
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
	}
	
	
	public Result findById(int id) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT r FROM Result r WHERE r.id = :id").setParameter("id", id);
			Result result = new Result();
			try{
				result=(Result)q.getSingleResult();
			}
			catch(NoResultException e){
				result=null;
			}
			return result;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
	}	
		
	public void save(Result result){

		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Collection<Result> currentResults=this.findByFirstname(result.getFirstName());
		Result save = new Result();
		for(Result res : currentResults){
			if(!SecurityUtil.checkHash(res.getResultHash(), result.getResultHash())){
				save=null;
				break;
			}
		}
		if(save!=null){
			em.merge(result);
		}
		em.getTransaction().commit();
		em.close();	
		
	}
	
	public void delete(Result result){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Query q2=em.createQuery("SELECT r FROM Result r");
		Collection<Result>listOfResults= new ArrayList<Result>();
		
		
		listOfResults=(Collection<Result>)q2.getResultList();
		for(Result r : listOfResults){
			System.out.println(r.getResultHash()+" , "+ result.getResultHash());
			if(r.getResultHash().equals(result.getResultHash())){
				r=em.merge(r);
				em.remove(r);
				System.out.println("AAA+++---");
				break;
				}
			}
		
		
		
		em.getTransaction().commit();
		em.close();	
	}
	
	public void delete(Collection<Result> results){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Query q2=em.createQuery("SELECT r FROM Result r");
		Collection<Result>listOfResults= new ArrayList<Result>();
		
		
		listOfResults=(Collection<Result>)q2.getResultList();
		for(Result r1 : listOfResults){
			for(Result r2 : results){
				System.out.println(r1.getResultHash()+" , "+ r2.getResultHash());
				if(r1.getResultHash().equals(r2.getResultHash())){
					r1=em.merge(r1);
					em.remove(r1);
					System.out.println("AAA+++---");
					break;
				}
			}
		}
		
		em.getTransaction().commit();
		em.close();	
	}
	
	public void update(Result oldResult,Result newResult){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		System.out.println("METOD");
		Query q1=em.createQuery("SELECT u FROM User u WHERE u.username = :username").setParameter("username", oldResult.getUser().getUsername());
		User user= new User();
		try{
			user=(User)q1.getSingleResult();
		}
		catch (NoResultException e){
			user=null;
		}
		Query q2=em.createQuery("SELECT q FROM Quiz q WHERE q.quizName = :name").setParameter("name", oldResult.getQuiz().getQuizName());
		Quiz quiz= new Quiz();
		try{
			quiz=(Quiz)q2.getSingleResult();
		}
		catch (NoResultException e){
			quiz=null;
		}
		System.out.println("METOD!");
		
		Collection<Result> currentResults=this.findByFirstname(oldResult.getFirstName());
			
			for(Result res : currentResults){
				System.out.println(res.getUser().getUsername()+" , "+newResult.getUser().getUsername());
				if(res.getUser().getUsername().equals(newResult.getUser().getUsername())){
					res.setEmail(newResult.getEmail());
					res.setFirstName(newResult.getFirstName());
					res.setLastName(newResult.getLastName());
					res.setQuiz(newResult.getQuiz());
					res.setTotalPoints(newResult.getTotalPoints());
					res.setUser(newResult.getUser());
					user.addUserResult(res);
					quiz.addQuizResult(res);
					em.merge(res);
					break;
			}
		}
		
		em.getTransaction().commit();
		em.close();	
	}

	public User getUser(Result result) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT r FROM Result r WHERE r.hashResult = :hash").setParameter("hash", result.getResultHash());
			Result _result = new Result();
			User user = new User();
			try{
				_result=(Result)q.getSingleResult();
				user=_result.getUser();
			}
			catch(NoResultException e){
				_result=null;
				user=null;
			}
			
			return user;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;	
	}
	
	public Collection<Result>getUserResults(User user){
		EntityManager em=createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :username").setParameter("username", user.getUsername());
			User _user = new User();
			Collection<Result> results = new ArrayList<Result>();
			try{
				_user=(User)q.getSingleResult();
				results=_user.getUserResults();
			}
			catch(NoResultException e){
				_user=null;
				results=null;
			}
			
			return results;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
		
	}

	public Quiz getQuiz(Result result) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT r FROM Result r WHERE r.hashResult = :hash").setParameter("hash", result.getResultHash());
			Result _result = new Result();
			Quiz quiz = new Quiz();
			try{
				_result=(Result)q.getSingleResult();
				quiz=_result.getQuiz();
			}
			catch(NoResultException e){
				_result=null;
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
	
	public Collection<Result>getQuizResults(Quiz quiz){
		EntityManager em=createEntityManager();
		try {
			Query q = em.createQuery("SELECT q FROM Quiz q WHERE u.quizName = :quizName").setParameter("quizName", quiz.getQuizName());
			Quiz _quiz = new Quiz();
			Collection<Result> results = new ArrayList<Result>();
			try{
				_quiz=(Quiz)q.getSingleResult();
				results=_quiz.getResults();
			}
			catch(NoResultException e){
				_quiz=null;
				results=null;
			}
			
			return results;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;

	}

	public void addScore(Result result, int points) {
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Result res=this.findByHash(result.getResultHash());
		if(res!=null){
			res.setTotalPoints(res.getTotalPoints()+points);
			em.merge(res);
		}
		em.getTransaction().commit();
		em.close();	
	}

	public void setScore(Result result, int points) {
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		Result res=this.findByHash(result.getResultHash());
		System.out.println(res);
		if(res!=null){
			res.setTotalPoints(points);
			System.out.println(res.getTotalPoints());
			em.merge(res);
		}
		em.getTransaction().commit();
		em.close();	
		
	}
	public void clearScore(Result result) {
		this.setScore(result, 0);
	}
}