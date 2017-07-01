package ba.fet.rwa.lv10.domain;

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
	
	@OneToMany(mappedBy="loggedIn")
	private Collection<User> loggedUsers;
	
	public void logIn(User user){
		loggedUsers.add(user);
	}
	
	public void logOut(User user){
		loggedUsers.remove(user);
	}
	
	public Collection<User> getLogInfo(){
		return loggedUsers;
	}

}