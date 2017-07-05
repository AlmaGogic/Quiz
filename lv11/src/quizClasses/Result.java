package quizClasses;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import quizSecurityUtil.SecurityUtil;


@Entity
public class Result {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id")
	int id;
	String resultHash;
	String firstName;
	String lastName;
	String email;
	int totalPoints;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinTable(name="UserResult",joinColumns=@JoinColumn(name="ResultID", 
	   referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="UserID",
	   referencedColumnName="id"))
	User user;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinTable(name="QuizResult",joinColumns=@JoinColumn(name="ResultID", 
	   referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="QuizID",
	   referencedColumnName="id"))
	Quiz quiz;
	
	public Result(){
		id=0;
		double a=(Math.random()/Math.random())+Math.random();
		//System.out.println(a);
		resultHash = SecurityUtil.Hash(" "+a);
		firstName="";
		lastName="";
		email="";
		totalPoints=0;
		user = new User();
		quiz=new Quiz();
	}
	
	public Result(int _id,String _resultHash, String _firstName,String _lastName,String _email,int _totalPoints,User _user){
		id=_id;
		resultHash=_resultHash;
		firstName=_firstName;
		lastName=_lastName;
		email=_email;
		totalPoints=_totalPoints;
		user= _user;
		quiz= new Quiz();
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
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String _email){
		email=_email;
	}
	
	public int getTotalPoints(){
		return totalPoints;
	}
	
	public void setTotalPoints(int _totalPoints){
		totalPoints=_totalPoints;
	}
	public Quiz getQuiz(){
		return quiz;
	}
	public void setQuiz(Quiz _quiz){
		quiz=_quiz;
	}
	public void setUser(User _user){
		user=_user;
	}
	public User getUser(){
		return user;
	}
	public String getResultHash(){
		return resultHash;
	}
	public void setResultHash(){
		SecurityUtil.Hash(resultHash);
	}
}