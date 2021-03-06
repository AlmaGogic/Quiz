package quizClasses;

import java.util.*;
import javax.persistence.*;

@Entity
public class Quiz {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "id")
	private int id;
	private String quizLogo;
	private String quizName;
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="QuizQuestion",joinColumns=@JoinColumn(name="quizID", 
	   referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="questionID",
	   referencedColumnName="id"))
	private Collection<Question> listOfQuestions;
	@OneToMany(mappedBy="quiz")
	private Collection<Result>listOfResults;
	
	
	public Quiz(){
		id=0;
		quizName="";
		listOfQuestions=new ArrayList<Question>();
		listOfResults=new ArrayList<Result>();
		
	}
	
	public Quiz(int _id,String _quizName,Question question){
		id=_id;
		quizName=_quizName;
		listOfQuestions.add(question);
		listOfResults=new ArrayList<Result>();
		
	}
	
	public int getQuizId(){
		return id;
	}
	
	public void setQuizId(int _id){
		id=_id;
	}
	
	public String getQuizName(){
		return quizName;
	}
	
	public void setQuizName(String _quizName){
		quizName=_quizName;
	}
	
	public String getQuizLogo(){
		return quizLogo;
	}
	
	public void setQuizLogo(String _quizLogo){
		quizLogo=_quizLogo;
	}
	
	public void addQuestion(Question question){
			listOfQuestions.add(question);
		
		
	}
	
	public void removeQuestion(Question question){
			listOfQuestions.remove(question);
		
	}
	
	public Collection<Question> getQuestions(){
		return listOfQuestions;
	}
	
	public Collection<Result> getResults(){
		return listOfResults;
	}
	public void addResults(Collection<Result>_results){
		for(Result result : _results){
			listOfResults.add(result);
		}
	}
	public void setResults(Collection<Result>_results){
		listOfResults.clear();
		addResults(_results);
	}
	public void addQuizResult(Result quizResult){
		listOfResults.add(quizResult);
	}
	public void addQuestions(Collection<Question>_questions){
		for(Question question : _questions){
			listOfQuestions.add(question);
		}
	}
	public void removeQuizResult(Result quizResult){	
		listOfResults.remove(quizResult);
	}

	public void setQuizQuestions(Collection<Question> _questions) {
		listOfQuestions.clear();
		this.addQuestions(_questions);
		
	}

	

}