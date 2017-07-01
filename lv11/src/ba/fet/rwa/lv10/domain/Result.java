package ba.fet.rwa.lv10.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Result {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id")
	int id;
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
		firstName="";
		lastName="";
		email="";
		totalPoints=0;
		quiz=new Quiz();
	}
	
	public Result(int _id,String _firstName,String _lastName,String _email,int _totalPoints){
		id=_id;
		firstName=_firstName;
		lastName=_lastName;
		email=_email;
		totalPoints=_totalPoints;
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
}