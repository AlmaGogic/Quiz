package quizInitConsole;


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
	
		//Collection<Role> users=roleService.findAll();
		
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
		quiz.setQuizName("Java");
		
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
		if(questionService.findByText("Volite li programirati u c?")==null) {
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
		

		//System.out.println(quiz+"  "+question);
		
		quizService.add(quiz,question);
		}
		/*** KREIRANJE REZULTATA I VEZANJE ZA KVIZ I KORISNIKA ***/
		Result result= new Result();

		User user=userService.findByUsername("dritchie");
		result.setUser(user);
		//System.out.println(user);
		user.addUserResult(result);
		resultService.create(quiz,result);
		
		Result res=new Result();
		res.setFirstName("Ime");
		res.setUser(user);
		
		/*** MIJENJANJE ULOGE KORISNIKA ***/
		//userService.changeRole(user.getUsername(), "common");
		
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
		
		
		/*** MANIPULACIJA PITANJIMA (KO FOL RADI :-D) ***/
		Question foundQuestion = questionService.findByText("Volite li programirati u c?");
		/*Answer answer=new Answer();
		answer.setAnswer("Mozda");
		answer.setCorrectStatus(true);
		*/
		
		if(foundQuestion!=null){
			
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
		}
	}
}
