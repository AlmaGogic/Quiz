package ba.fet.rwa.lv10.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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