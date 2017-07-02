package ba.fet.rwa.lv10.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ba.fet.rwa.lv10.dao.AnswerDao;
import ba.fet.rwa.lv10.dao.QuestionDao;
import ba.fet.rwa.lv10.dao.QuizDao;
import ba.fet.rwa.lv10.dao.ResultDao;
import ba.fet.rwa.lv10.dao.RoleDao;
import ba.fet.rwa.lv10.dao.UserDao;
import ba.fet.rwa.lv10.domain.Answer;
import ba.fet.rwa.lv10.domain.LoggedStatus;
import ba.fet.rwa.lv10.domain.Question;
import ba.fet.rwa.lv10.domain.Quiz;
import ba.fet.rwa.lv10.domain.Result;
import ba.fet.rwa.lv10.domain.Role;
import ba.fet.rwa.lv10.domain.User;
import ba.fet.rwa.lv10.service.AnswerService;
import ba.fet.rwa.lv10.service.QuestionService;
import ba.fet.rwa.lv10.service.QuizService;
import ba.fet.rwa.lv10.service.ResultService;
import ba.fet.rwa.lv10.service.RoleService;
import ba.fet.rwa.lv10.service.UserService;

public class BootstrapUsersApp {

	public static void main(String[] args) {
		/*** DATABASE INIT METODE ***/
		
		UserService userService = new UserService(new UserDao());
		QuestionService questionService = new QuestionService(new QuestionDao());
		AnswerService answerService = new AnswerService(new AnswerDao());
		QuizService quizService = new QuizService(new QuizDao());
		RoleService roleService = new RoleService(new RoleDao());
		ResultService resultService = new ResultService(new ResultDao());
		
		/*** KREIRANJE KORISNIČKIH ULOGA ***/
		
		Role admin = new Role();
		Role editor = new Role();
		Role common = new Role();
	
		//Collection<Role> users=roleService.findAll();
		
	 	admin.setRole("admin");
		editor.setRole("editor");
		common.setRole("common");
			
		roleService.create(admin);
		roleService.create(editor);
		roleService.create(common);

		/*** KREIRANJE KORISNIKA ***/
		if (userService.findByUsername("dritchie") == null) {
			
			User user = new User();
			Role role =roleService.findByName("admin");
			user.setRole(role);

			role.addUserWithRole(user);
			
			user.setFirstName("Dennis");
			user.setLastName("Ritchie");
			user.setUsername("dritchie");
			user.setPassword("fet.ba");

			userService.create(user,role);
			
		} 

		if (userService.findByUsername("rpike") == null) {
			
			User user = new User();
			Role role =roleService.findByName("editor");
			user.setRole(role);

			role.addUserWithRole(user);
			
			
			user.setFirstName("Rob");
			user.setLastName("Pike");
			user.setUsername("rpike");
			user.setPassword("fet.ba");

			userService.create(user,role);
		}
		
		/*Role role=new Role();
		role.setRole("user");
		role.setRoleId(1);
	
		roleService.update("common",role);
		roleService.delete("editor");*/
		
		/*** KREIRANJE KVIZA ***/
		//Dodaje jedno pitanje sa 2 odgovora
		
		Quiz quiz=new Quiz();

		//quiz.setQuizId(1);
		quiz.setQuizName("Kviz");

		Question question = new Question();
		
		//Ako nema pitanja u bazi
		if(questionService.findAll().isEmpty()) {
				
			Answer answer1=new Answer();
			Answer answer2=new Answer();
					
			question.setQuestionId(0);
			question.setAnsweredStatus(false);
			question.setQuestionPoints(10);
			question.setQuestionText("Volite li programirati u javi?");
			//System.out.println(question.getQuestionId()+","+question.getQuestionText()+" "+answer1.getAnswer()+" "+answer2.getAnswer());
				
			answer1.setAnswer("Da");
			answer1.setCorrectStatus(true);
			answer2.setAnswer("Ne");
				
			answer2.setCorrectStatus(false);
			questionService.create(question,answer1,answer2);
				
				
			quizService.create(quiz, question);
				
		}	
		
		
		
		
		/*** KREIRANJE NOVOG PITANJA I DODAVANJE U POSTOJEĆI KVIZ ***/
		
		Answer answer1=new Answer();
		Answer answer2=new Answer();
				
		question.setAnsweredStatus(false);
		question.setQuestionPoints(10);
		question.setQuestionText("Volite li programirati u c?");
		//System.out.println(question.getQuestionId()+","+question.getQuestionText()+" "+answer1.getAnswer()+" "+answer2.getAnswer());
			
		answer1.setAnswer("Da");
		answer1.setCorrectStatus(true);
		answer2.setAnswer("Ne");
			
		answer2.setCorrectStatus(false);
		questionService.create(question,answer1,answer2);
		System.out.println(quiz+"  "+question);
		quiz.addQuestion(question);
		question.addToQuiz(quiz);
		quizService.add(quiz,question);
		
		/*** KREIRANJE REZULTATA I VEZANJE ZA KVIZ I KORISNIKA ***/
		Result result= new Result();
		
		result.setQuiz(quiz);
		quiz.addQuizResult(result);

		User user=userService.findByUsername("dritchie");
		result.setUser(user);
		user.addUserResult(result);
		resultService.create(result);
		
		Result res=new Result();
		res.setFirstName("Ime");
		res.setUser(user);
		
		/*** MIJENJANJE ULOGE KORISNIKA ***/
		userService.changeRole(user.getUsername(), "common");
		
		/*** LOGIN I LOGOUT ***/
		//userService.LogIn(user);
		//userService.LogOut(user);

		/*** MANIPULACIJA REZULTATOM ***/
		
		//resultService.update(result,res);
		//Collection<Result> r=resultService.findByFirstname("Rob");
		//resultService.addPoints(r.iterator().next(), 100);
		//resultService.clearPoints(r.iterator().next());
		//resultService.setPoints(r.iterator().next(), 1000);
		/*Collection<Result> results=resultService.findAll();
		for(Result r1 : results){
			System.out.println(r1.getResultHash());
		}*/
		/*Collection<Result>rez=resultService.findByFirstname("Rob");
		for(Result r1 : rez){
			System.out.println(r1.getFirstName());
		}*/
		//resultService.create(res);
		//quizService.deleteQuiz(quiz);
		
		
		/*** MANIPULACIJA PITANJIMA I ODGOVORIMA OBAVEZNO PROVJERITI OPET ***/
		/*Question foundQuestion = questionService.findByText("Volite li programirati u C?");
		Answer answer=new Answer();
		answer.setAnswer("Mozda");
		answer.setCorrectStatus(false);
		
		
		if(foundQuestion!=null){
			answer.addToQuestion(foundQuestion);
			foundQuestion.addAnswer(answer);
			foundQuestion.setQuestionText("Volite li programirati u C?");
			//????Čudan -> questionService.update("Volite li programirati u C?", foundQuestion);
			/*questionService.clearQuestion(foundQuestion);
			questionService.deleteQuestion(question);
			
			answerService.updateAnswer(foundQuestion.getQuestionText(), answer.getAnswer(), answer2);
		*/
		}
	}
}
