package ba.fet.rwa.lv10.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id")
	private int id;
	private String text;
	private boolean correctStatus;
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="QuestionAnswer",joinColumns=@JoinColumn(name="answerID", 
	   referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="questionID",
	   referencedColumnName="id"))
	private Collection<Question> listOfQuestions;
	
	public Answer(int _id, String _text, boolean _correctStatus,Question question){
		id=_id;
		text=_text;
		correctStatus=_correctStatus;
		listOfQuestions.add(question);
	}
	
	public Answer() {
		id=0;
		text="";
		correctStatus=false;
		listOfQuestions=new ArrayList<Question>();
	}

	public int getId(){
		return id;
	}
	public void setId(int _id){
		id=_id;
	}
	
	public String getAnswer(){
		return text;
	}
	
	public void setAnswer(String _text){
		text=_text;
	}
	
	public boolean getCorrectStatus(){
		return correctStatus;
	}
	
	public void setCorrectStatus(boolean _correctStatus){
		correctStatus=_correctStatus;
	}
	
	public void addToQuestion(Question question){
		listOfQuestions.add(question);
	}
	public void removeFromQuestion(Question question){
		for(Question q : listOfQuestions){
			if(q.getQuestionText().equals(question.getQuestionText())){
				listOfQuestions.remove(question);
			}
		}
	}
	public Collection<Question> getQuestions(){
		return listOfQuestions;
	}

}