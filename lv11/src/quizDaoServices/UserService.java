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
	
	public void LogIn(User user){
		userDao.logIn(user);
	}
	
	public void LogOut(User user){
		userDao.logOut(user);
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
	
	public User authenticate(String username, String password) {
		
		User user = findByUsername(username);
		
		if (user == null) {
			return null;
		}
		
		if (SecurityUtil.checkPassword(password, user.getPassword())) {
			user.setPassword("");
			return user;
		}
		
		return null;
	}
}