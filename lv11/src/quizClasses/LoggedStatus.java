package quizClasses;

import java.util.*;
import javax.persistence.*;

@Entity
public class LoggedStatus {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id")
	private int id;
	@Column(name="username", unique = true)
	private String username;
	@OneToMany(mappedBy="loggedIn")
	private Collection<User> loggedUsers;
	
	public LoggedStatus(){
		username="";
		loggedUsers= new ArrayList<User>();
	}
	
	public int getId(){
		return id;
	}
	
	public void logIn(User user){
		username=user.getUsername();
		loggedUsers.add(user);
	}
	
	public void logOut(User user){
		username="";
		loggedUsers.remove(user);
	}
	
	public Collection<User> getLogInfo(){
		return loggedUsers;
	}

}