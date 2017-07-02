package ba.fet.rwa.lv10.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ba.fet.rwa.lv10.domain.Answer;
import ba.fet.rwa.lv10.domain.LoggedStatus;
import ba.fet.rwa.lv10.domain.Question;
import ba.fet.rwa.lv10.domain.Quiz;
import ba.fet.rwa.lv10.domain.Result;
import ba.fet.rwa.lv10.domain.Role;
import ba.fet.rwa.lv10.domain.User;

final public class UserDao extends AbstractDao {
	
	public void UserDao() {
		
	}
	
	public List<User> findAll() {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("SELECT u FROM User u");
		List<User> resultList = q.getResultList();
		em.close();
		return resultList;
	}
	
	public User findByUsername(String username) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM User u WHERE u.username = :username").setParameter("username", username);
			User user = new User();
			try{
				user=(User)q.getSingleResult();
			}
			catch(NoResultException e){
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
	
	public Result findUserResult(User user){
		
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM User u WHERE u.id = :id").setParameter("id", user.getId());
			User currentUser = new User();
			try{
				currentUser=(User)q.getSingleResult();
			}
			catch(NoResultException e){
				user=null;
			}
			Result result=new Result();
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
	public void logIn(User user){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		User usr=this.findByUsername(user.getUsername());
		LoggedStatus usrLog=usr.getLogStatus();
		LoggedStatus login=new LoggedStatus();
		
		if(usrLog==null){
			login.logIn(user);
			user.setLogStatus(login);
			em.merge(user);
			em.merge(login);
		}
		
		
		em.getTransaction().commit();
		em.close();	
	}
	public void logOut(User user){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		
		LoggedStatus logStatus = user.getLogStatus();
		if(logStatus!=null){
			Collection<User>listOfUsers=logStatus.getLogInfo();
			listOfUsers.remove(user);
			
			logStatus=em.merge(logStatus);
			em.remove(logStatus);
		}
		em.getTransaction().commit();
		em.close();	
		
	} 
	
	public void update(String username,User user) {
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		User currentUser= this.findByUsername(username);
		if(currentUser!=null){
			currentUser.setEmail(user.getEmail());
			currentUser.setFirstName(user.getFirstName());
			currentUser.setId(user.getId());
			currentUser.setLastName(user.getLastName());
			currentUser.setLogStatus(user.getLogStatus());
			currentUser.setPassword(user.getPassword());
			currentUser.setUsername(user.getUsername());
			currentUser.setRole(user.getRole());
			em.merge(currentUser);
		}
		else{
			em.merge(user);
		}
		
		em.getTransaction().commit();
		em.close();	
	}
	
	public void changeUserRole(String username, String role){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		User currentUser=this.findByUsername(username);
		Query q =em.createQuery("SELECT r FROM Role r WHERE r.title = :role").setParameter("role", role);
		Role currentRole = new Role();
		try{
			currentRole = (Role) q.getSingleResult();
		}
		catch(NoResultException e){
			System.out.println("Role not found!");
		}
		if(currentUser!=null){
			Query q2 =em.createQuery("SELECT r FROM Role r WHERE r.title = :role").setParameter("role", currentUser.getRole().getRole());
			Role currentUserRole=(Role)q2.getSingleResult();
			System.out.println("C: "+currentUserRole.getRole());
			currentUserRole.removeUserWithRole(currentUser);
			currentUser.setRole(new Role());
			em.merge(currentUserRole);
			
			currentRole.addUserWithRole(currentUser);
			currentUser.setRole(currentRole);
			System.out.println("-++-"+currentUser.getRole().getRole());
			em.merge(currentRole);
			em.merge(currentUser);
		}
		
		em.getTransaction().commit();
		em.close();	
	}
	
	public void deleteUser(User user){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
			
		Query q = em.createQuery("SELECT u FROM User u WHERE u.id= :user").setParameter("user", user.getId());
		User retUser = new User();
		try{
			retUser=(User)q.getSingleResult();
		}
		catch(NoResultException e){
			retUser=null;
		}
		if(retUser!=null){
			retUser=em.merge(retUser);
			em.remove(retUser);
		}
		em.getTransaction().commit();
		em.close();	
		
	}
	
	public void delete(String username){
		User user=this.findByUsername(username);
		deleteUser(user);
	}
	
	public Collection<Quiz> getUsersFinishedQuizzes(User user){
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		int numberOfAnswered=0;
		Query q1 = em.createQuery("SELECT r FROM Result r");
		Collection<Result> results = (Collection<Result>)q1.getResultList();
		Collection<Quiz> finishedQuizzes = new ArrayList<Quiz>();
		for(Result result : results){
			if(result.getFirstName().equals(user.getFirstName())){
				Collection<Question>questions=result.getQuiz().getQuestions();
				for(Question question : questions){
					if(question.getAnsweredStatus()!=true){
						numberOfAnswered=0;
						break;
					}
					else{
						numberOfAnswered++;
					}
				}
				if(numberOfAnswered==questions.size()){
					finishedQuizzes.add(result.getQuiz());
				}
			}	
		}
		return finishedQuizzes;
	}
	
	public void save(User user, Role role) {
		EntityManager em = createEntityManager();
		em.getTransaction().begin();
		Query q = em.createQuery("SELECT u FROM User u WHERE u.id= :user").setParameter("user", user.getId());
		Query q2 = em.createQuery("SELECT r FROM Role r WHERE r.title= :role").setParameter("role", role.getRole());
		User retUser = new User();
		Role retRole = new Role();
		try{
			retUser=(User)q.getSingleResult();
		}
		catch(NoResultException e){
			retUser=null;
		}
		try{
			retRole=(Role)q2.getSingleResult();
		}
		catch(NoResultException e){
			retRole=null;
		}
		if(retUser!=null&&retRole!=null){
			retUser.setRole(retRole);
			retRole.addUserWithRole(retUser);
			em.merge(retRole);
			em.merge(retUser);
		}
		
		
		
		em.getTransaction().commit();
		em.close();
		
	}
}