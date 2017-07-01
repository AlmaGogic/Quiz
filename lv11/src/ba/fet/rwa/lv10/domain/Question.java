package ba.fet.rwa.lv10.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(schema = "TEST")
public class Question {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id")
	private int id;
	private String text;
	private boolean answered;
	private int points;
	
	@ManyToMany(mappedBy="listOfQuestions")
	private Collection<Quiz> listOfQuizzes;
	
	@ManyToMany(mappedBy="listOfQuestions")
	private Collection<Answer> listOfAnswers;
	
	public Question(int _id,String _text, int _points,Answer answer){
		
		id=_id;
		text=_text;
		answered=false;
		points=_points;
		listOfQuizzes=new ArrayList<Quiz>();
		listOfAnswers.add(answer);
	}
	
	public Question() {
		id=0;
		text="";
		answered=false;
		points=0;
		listOfAnswers=new ArrayList<Answer>();
		listOfQuizzes=new ArrayList<Quiz>();
	}

	public int getQuestionId(){
		return id;
	}
	public void setQuestionId(int _id){
		id=_id;
	}
	
	public boolean getAnsweredStatus(){
		return answered;
	}
	public void setAnsweredStatus(boolean _answeredStatus){
		answered=_answeredStatus;
	}
	public String getQuestionText(){
		return text;
	}
	public void setQuestionText(String _text){
		text=_text;
	}
	public int getQuestionPoints(){
		return points;
	}
	public void setQuestionPoints(int _points){
		points=_points;
	}
	
	public void addToQuiz(Quiz quiz){
		listOfQuizzes.add(quiz);
	}
	
	public void removeFromQuiz(Quiz quiz){
		listOfQuizzes.remove(quiz);
	}
	
	public void addAnswer(Answer answer){
		listOfAnswers.add(answer);
	}
	
	public void removeAnswer(Answer answer){
		listOfAnswers.remove(answer);
	}
	
	public Collection<Answer> getAnswers(){
		return listOfAnswers;
	}
	
	public void setAnswers(Collection<Answer> answers){
		listOfAnswers.addAll(answers);
	}

	public Collection<Quiz> getQuizzes(){
		return listOfQuizzes;
	}
	public void setQuizzes(Collection<Quiz>_quizzes){
		for(Quiz quiz : _quizzes){
			listOfQuizzes.add(quiz);
		}
	}
}