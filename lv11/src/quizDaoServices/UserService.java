package quizDaoServices;

import java.util.*;
import quizClasses.*;
import quizDao.*;
import quizSecurityUtil.*;

public class UserService {
	
	private UserDao userDao;
	
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void create(User user,Role role) {
		user.setPassword(SecurityUtil.hashPassword(user.getPassword()));
		userDao.save(user,role);	
	}
	
	
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	public boolean LogIn(User user){
		return userDao.logIn(user);
	}
	
	public boolean LogOut(User user){
		return userDao.logOut(user);
	}
	public boolean LogOut(String username){
		return userDao.logOut(username);
	}
	
	public void update(String username,User user){
		userDao.update(username,user);
	}
	
	public void delete(String username){
		userDao.delete(username);
	}
	
	public void changeRole(String username,String role){
		userDao.changeUserRole(username, role);
	}
	
	public boolean checkIfLogged(User user){
		return userDao.checkIfLogged(user);
	}
	
	public User authenticate(String username, String password) {
		
		User user = findByUsername(username);
		
		if (user == null) {
			return null;
		}
		try{
			if (SecurityUtil.checkPassword(password, user.getPassword())) {
				//user.setPassword("");
				return user;
			}
		}
		catch(Exception e){
			return user;
		}
		
		return null;
	}

	public Collection<Question> findUnanswered(User user, Quiz quiz) {
		return userDao.findUnanswered(user,quiz);
		
	}
}