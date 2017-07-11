package quizInitConsole;


import java.util.Collection;

import quizClasses.*;
import quizDao.*;
import quizDaoServices.*;

public class BootstrapUsersApp {

	public static void main(String[] args) {
		/*** DATABASE INIT METODE ***/
		
		UserService userService = new UserService(new UserDao());
		QuestionService questionService = new QuestionService(new QuestionDao());
		//AnswerService answerService = new AnswerService(new AnswerDao());
		QuizService quizService = new QuizService(new QuizDao());
		RoleService roleService = new RoleService(new RoleDao());
		ResultService resultService = new ResultService(new ResultDao());
		
		/*** KREIRANJE KORISNIČKIH ULOGA ***/
		
		Role admin = new Role();
		Role editor = new Role();
		Role common = new Role();
	
		Collection<Role> users=roleService.findAll();
		
	 	admin.setRole("admin");
		editor.setRole("editor");
		common.setRole("common");
			
		roleService.create(admin);
		roleService.create(editor);
		roleService.create(common);


		//System.out.println("U:"+(userService.findByUsername("dritchie")==null));
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
		Quiz quiz2=new Quiz();
		quiz.setQuizName("Java");
		quiz2.setQuizName("CSS");
		
		Question question = new Question();
		Question question2 = new Question();
		Question question3 = new Question();
		Question question4 = new Question();
		Question question5 = new Question();
		
		//Ako nema pitanja u bazi
	//	if(questionService.findAll().isEmpty()) {
				
			Answer answer1=new Answer();
			Answer answer2=new Answer();
			Answer answer3=new Answer();
			Answer answer4=new Answer();
					
			question.setQuestionId(0);
			question.setAnsweredStatus(false);
			question.setQuestionPoints(10);
			question.setQuestionText("Which of the following is not a keyword in java?");
			//System.out.println(question.getQuestionId()+","+question.getQuestionText()+" "+answer1.getAnswer()+" "+answer2.getAnswer());
				
			answer1.setAnswer("static");
			answer1.setCorrectStatus(false);
			answer2.setAnswer("Boolean");
			answer2.setCorrectStatus(true);
			answer3.setAnswer("void");
			answer3.setCorrectStatus(false);
			answer4.setAnswer("private");
			answer4.setCorrectStatus(false);;
			questionService.create(question,answer1,answer2);
				
			questionService.add(question, answer3);
			questionService.add(question, answer4);
				
			quiz.addQuestion(question);
			quizService.create(quiz, question);
			
			Answer answer5=new Answer();
			Answer answer6=new Answer();
			Answer answer7=new Answer();
			Answer answer8=new Answer();
			
			question2.setQuestionId(0);
			question2.setAnsweredStatus(false);
			question2.setQuestionPoints(10);
			question2.setQuestionText("What is the size of double variable?");
			//System.out.println(question.getQuestionId()+","+question.getQuestionText()+" "+answer1.getAnswer()+" "+answer2.getAnswer());
				
			answer5.setAnswer("8 bit");
			answer5.setCorrectStatus(false);
			answer6.setAnswer("16 bit");
			answer6.setCorrectStatus(false);
			answer7.setAnswer("32 bit");
			answer7.setCorrectStatus(false);
			answer8.setAnswer("64 bit");
			answer8.setCorrectStatus(true);
			questionService.create(question2,answer5,answer6);
				
			questionService.add(question2, answer7);
			questionService.add(question2, answer8);
				
			quiz.addQuestion(question2);
			quizService.update(quiz.getQuizName(), quiz);
			
			Answer answer9=new Answer();
			Answer answer10=new Answer();
			Answer answer11=new Answer();
			Answer answer12=new Answer();
			
			question3.setQuestionId(0);
			question3.setAnsweredStatus(false);
			question3.setQuestionPoints(10);
			question3.setQuestionText("What is the default value of String variable?");
			//System.out.println(question.getQuestionId()+","+question.getQuestionText()+" "+answer1.getAnswer()+" "+answer2.getAnswer());
				
			answer9.setAnswer("\"\"");
			answer9.setCorrectStatus(false);
			answer10.setAnswer("''");
			answer10.setCorrectStatus(false);
			answer11.setAnswer("null");
			answer11.setCorrectStatus(true);
			answer12.setAnswer("not defined");
			answer12.setCorrectStatus(false);
			questionService.create(question3,answer9,answer10);
				
			questionService.add(question3, answer11);
			questionService.add(question3, answer12);
				
			quiz.addQuestion(question3);
			quizService.update(quiz.getQuizName(), quiz);
				
	//	}
	
		Answer answer13=new Answer();
		Answer answer14=new Answer();

		question4.setAnsweredStatus(false);
		question4.setQuestionPoints(10);
		question4.setQuestionText("Volite li dizajnirati u CSS-u?");

		
		answer13.setAnswer("Da");
		answer13.setCorrectStatus(true);
		answer14.setAnswer("Ne");
			
		answer14.setCorrectStatus(false);
		questionService.create(question4,answer13,answer14);
			
			
		quizService.create(quiz2, question4);
		/*Question q=questionService.findByText(question2.getQuestionText());
		Collection<Answer>answers=q.getAnswers();
		System.out.println(answers.size());
		for(Answer a : answers){
			System.out.println(a.getAnswer());
		}*/
		
		
		
		/*** KREIRANJE NOVOG PITANJA I DODAVANJE U POSTOJEĆI KVIZ ***/
		/*if(questionService.findByText("Volite li programirati u c?")==null) {
		Answer answer3=new Answer();
		Answer answer4=new Answer();
				
		question.setAnsweredStatus(false);
		question.setQuestionPoints(10);
		question.setQuestionText("Volite li programirati u c?");
		System.out.println(question.getQuestionId()+","+question.getQuestionText()+" "+answer3.getAnswer()+" "+answer4.getAnswer());
			
		answer3.setAnswer("Da");
		answer3.setCorrectStatus(true);
		answer4.setAnswer("Ne");

		answer4.setCorrectStatus(false);


		questionService.create(question,answer3,answer4);
		*/

		//System.out.println(quiz+"  "+question);
		
		/*quizService.add(quiz,question);
		}*/
		/*** KREIRANJE REZULTATA I VEZANJE ZA KVIZ I KORISNIKA ***/
		/*Result result= new Result();

		User user=userService.findByUsername("dritchie");
		result.setUser(user);
		//System.out.println(user);
		user.addUserResult(result);
		resultService.create(quiz,result);
		
		Result res=new Result();
		res.setFirstName("Ime");
		res.setUser(user);
		*/
		/*** MIJENJANJE ULOGE KORISNIKA ***/
		//userService.changeRole(user.getUsername(), "common");
		
		/*** LOGIN I LOGOUT ***/
		//userService.LogIn(user);
		/*User  user = userService.findByUsername("g");
		userService.LogOut(user);
*/
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
		
		
		/*** MANIPULACIJA PITANJIMA (KO FOL RADI :-D) ***/
		//Question foundQuestion = questionService.findByText("Volite li programirati u c?");
		/*Answer answer=new Answer();
		answer.setAnswer("Mozda");
		answer.setCorrectStatus(true);
		*/
		
		//if(foundQuestion!=null){
			
			//answer.addToQuestion(foundQuestion);
			//foundQuestion.addAnswer(answer);
			//System.out.println(foundQuestion.getAnswers().iterator().next().getAnswer());
			//foundQuestion.setQuestionText("Volite li programirati u C?");
			//questionService.update("Volite li programirati u c?", foundQuestion);
			//questionService.clearQuestion(foundQuestion);
			//questionService.deleteQuestion(foundQuestion);
			/*if(foundQuestion.getAnswers().iterator().hasNext()){
				Answer answer3=foundQuestion.getAnswers().iterator().next();
				System.out.println("A: "+answer3.getAnswer());
				answerService.updateAnswer(foundQuestion, answer3, answer);
			}
			Collection<Answer>answers=questionService.findCorrectAnswers(foundQuestion);
			System.out.println(answers.size());
			for(Answer a : answers){
				System.out.println(a.getAnswer());
			}*/
		//}
	}
}
