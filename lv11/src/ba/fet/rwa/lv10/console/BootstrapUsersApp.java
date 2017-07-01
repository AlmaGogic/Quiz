package ba.fet.rwa.lv10.console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ba.fet.rwa.lv10.dao.AnswerDao;
import ba.fet.rwa.lv10.dao.QuestionDao;
import ba.fet.rwa.lv10.dao.QuizDao;
import ba.fet.rwa.lv10.dao.RoleDao;
import ba.fet.rwa.lv10.dao.UserDao;
import ba.fet.rwa.lv10.domain.Answer;
import ba.fet.rwa.lv10.domain.LoggedStatus;
import ba.fet.rwa.lv10.domain.Question;
import ba.fet.rwa.lv10.domain.Quiz;
import ba.fet.rwa.lv10.domain.Role;
import ba.fet.rwa.lv10.domain.User;
import ba.fet.rwa.lv10.service.AnswerService;
import ba.fet.rwa.lv10.service.QuestionService;
import ba.fet.rwa.lv10.service.QuizService;
import ba.fet.rwa.lv10.service.RoleService;
import ba.fet.rwa.lv10.service.UserService;

public class BootstrapUsersApp {

	public static void main(String[] args) {

		UserService userService = new UserService(new UserDao());
		QuestionService questionService = new QuestionService(new QuestionDao());
		AnswerService answerService = new AnswerService(new AnswerDao());
		QuizService quizService = new QuizService(new QuizDao());
		RoleService roleService = new RoleService(new RoleDao());
		Role admin = new Role();
		Role editor = new Role();
		Role common = new Role();
	
		Collection<Role> users=roleService.findAll();
		System.out.println(users.size());
	/* PRVO OVO OTKOMENTARIŠI!!! TREBAJU SE PRVO ULOGE UNIJETI U BAZU!
	 		admin.setRole("admin");
			editor.setRole("editor");
			common.setRole("common");
			
			roleService.create(admin);
			roleService.create(editor);
			roleService.create(common);
*/
		
		
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
			LoggedStatus login = new LoggedStatus();

			Role role =roleService.findByName("editor");
			user.setRole(role);
			role.addUserWithRole(user);
			
			user.setFirstName("Rob");
			user.setLastName("Pike");
			user.setUsername("rpike");
			user.setPassword("fet.ba");
			
			login.logIn(user);
			user.setLogStatus(login);
			userService.create(user,role);
			
		}
		
		/*Role role=new Role();
		role.setRole("user");
		role.setRoleId(1);
	
		roleService.update("common",role);
		
			*/
		//roleService.delete("editor");
		
		Quiz quiz=new Quiz();

			quiz.setQuizId(1);
			quiz.setQuizName("Kviz");

			Question question = new Question();
			
			if(questionService.findAll().isEmpty()) {
				
				Answer answer1=new Answer();
				Answer answer2=new Answer();
					
				question.setQuestionId(0);
				question.setAnsweredStatus(false);
				question.setQuestionPoints(10);
				question.setQuestionText("Volite li programirati u javi?");
				System.out.println(question.getQuestionId()+","+question.getQuestionText()+" "+answer1.getAnswer()+" "+answer2.getAnswer());
				
				answer1.setAnswer("Da");
				answer1.setCorrectStatus(true);
				answer2.setAnswer("Ne");
				
				answer2.setCorrectStatus(false);
				questionService.create(question,answer1,answer2);
				
				
				quizService.create(quiz, question);
				
				
				
			}	
			Quiz quiz1=quizService.findById(quiz.getQuizId());
			Answer answer1=new Answer();
			Answer answer2=new Answer();
				
			question.setAnsweredStatus(false);
			question.setQuestionPoints(10);
			question.setQuestionText("Volite li programirati u c?");
			System.out.println(question.getQuestionId()+","+question.getQuestionText()+" "+answer1.getAnswer()+" "+answer2.getAnswer());
			
			answer1.setAnswer("Da");
			answer1.setCorrectStatus(true);
			answer2.setAnswer("Ne");
			
			answer2.setCorrectStatus(false);
			questionService.create(question,answer1,answer2);
			quizService.add(quiz1,question);
			
			User user=userService.findByUsername("dritchie");
			userService.changeRole(user.getUsername(), "common");
			//quizService.deleteQuiz(quiz);
		
		
		/* NOVE METODE
		Question question = questionService.findByText("Volite li programirati u javi?");
		Answer answer=new Answer();
		answer.setAnswer("Mozda");
		answer.setCorrectStatus(false);
		answer.setId(1203);
		
		User user= userService.findByUsername("rpike");
		answer.addToQuestion(question);
		question.addAnswer(answer);
		questionService.update(question, answer);
		userService.LogOut(user);
		questionService.clearQuestion(question);
		questionService.deleteQuestion(question);
	*/
	}
}
