package quizClasses;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id")
	private int id;
	private String firstName;
	private String lastName;
	@Column(name="username", unique = true)
	private String username;
	private String password;
	private String email;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinTable(name="UserRole",joinColumns=@JoinColumn(name="UserID", 
	   referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="RoleID",
	   referencedColumnName="id"))
	private Role role;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinTable(name="LoginStat",joinColumns=@JoinColumn(name="UserID", 
	   referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="LogID",
	   referencedColumnName="id"))
	LoggedStatus loggedIn;
	
	@OneToMany(mappedBy="user")
	private Collection<Result> listOfResults;
	
	public User() {
		id=0;
		firstName="";
		lastName="";
		username="";
		password="";
		email="";
		role=new Role();
		loggedIn=new LoggedStatus();
		
	}
	
	public User(int _id, String _firstName,String _lastName,String _userName,String _password,String _email, Role _role){
	
		id=_id;
		firstName=_firstName;
		lastName=_lastName;
		username=_userName;
		password=_password;
		email=_email;
		role=_role;
		loggedIn=new LoggedStatus();
	}
	
	public int getId(){
		return id;
	}
	
	public LoggedStatus getLogStatus(){
		return loggedIn;
	}
	
	public void setLogStatus(LoggedStatus _loggedIn){
		loggedIn=_loggedIn;
	}
	
	public void setId(int _id){
		id=_id;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public void setFirstName(String _firstName){
		firstName=_firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public void setLastName(String _lastName){
		lastName=_lastName;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String _userName){
		username=_userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String _password){
		password=_password;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String _email){
		email=_email;
	}
	
	public Role getRole(){
		return role;
	}
	
	public void setRole(Role _role){
		role=_role;
	}
	public Collection<Result> getUserResults(){
		return listOfResults;
	}
	public void setUserResults(Collection<Result> userResults){
		listOfResults.clear();
		for(Result result : userResults){
			listOfResults.add(result);
		}
	}
	public void mergeUserResults(Collection<Result> userResults){
		for(Result result : userResults){
			listOfResults.add(result);
		}
	}
	public void addUserResult(Result userResult){
		listOfResults.add(userResult);
	}
	public void removeUserResult(Result userResult){	
		listOfResults.remove(userResult);
	}
	
}