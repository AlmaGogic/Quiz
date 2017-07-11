package quizServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import quizClasses.Answer;
import quizClasses.Question;
import quizClasses.Quiz;
import quizDao.AnswerDao;
import quizDao.QuestionDao;
import quizDao.QuizDao;
import quizDaoServices.AnswerService;
import quizDaoServices.QuestionService;
import quizDaoServices.QuizService;

/**
 * Servlet implementation class quizServlet
 */
@WebServlet("/admin/quiz")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private QuizService quizService;
	private QuestionService questionService;
	private AnswerService answerService;
	private HashMap<String, ArrayList<String>> message;
	private ArrayList<Question> questions;
	private String name;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizServlet() {
        super();
        message = new HashMap<String, ArrayList<String>>();
        quizService = new QuizService(new QuizDao());
        questionService = new QuestionService(new QuestionDao());
        answerService = new AnswerService(new AnswerDao());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Gson gson = new Gson();
		name = request.getParameter("name");
		
		if(name != null){
		Quiz quiz = quizService.findByName(name);
		if(quiz!=null){
		questions = new ArrayList<Question>(quiz.getQuestions());
		}
		else{
			questions = new ArrayList<Question>();
		}
		}
		
		for(int i = 0; i < questions.size(); i++){
			ArrayList<Answer> answers = new ArrayList<Answer>(questions.get(i).getAnswers());
			ArrayList<String> answer = new ArrayList<String>();
			
			int size = answers.size();
			
			for(int j = 0; j < size; j++){
				answer.add(answers.get(j).getAnswer());
			}
			message.put(questions.get(i).getQuestionText(), answer);
		}
		
		request.setAttribute("name", name);
		request.setAttribute("questions", gson.toJson(getMsg()));
		request.getRequestDispatcher("/quiz.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String req = request.getParameter("req");
		name = request.getParameter("name");
		System.out.println(name);
		
		if(req != null){
			if(req.equals("removeQuestion")){
				Question question = questionService.findByText(request.getParameter("question"));
				
				questionService.clearQuestion(question);
				questionService.deleteQuestion(question);
			}else if(req.equals("removeAnswer")){
			
				Question question = questionService.findByText(request.getParameter("question"));
				ArrayList<Answer> answers = answerService.findByText(request.getParameter("answer"));
				Answer ans = null;
				
				ArrayList<Answer> qAnswers = new ArrayList<Answer>(question.getAnswers());
				for(int i = 0; i < qAnswers.size(); i++){
					
					if(qAnswers.get(i).getAnswer().equals(answers.get(0).getAnswer())){
						ans= qAnswers.get(i);
					}
				}
				
				question.removeAnswer(ans);
				questionService.update(question.getQuestionText(), question);
				
				answerService.deleteAnswer(ans);
			}
		}else{
		
			String qTxt = request.getParameter("qTxt");
			String aTxt1 = request.getParameter("aTxt1");
			String aTxt2 = request.getParameter("aTxt2");
			String aTxt3 = request.getParameter("aTxt3");
			String aTxt4 = request.getParameter("aTxt4");
			String[] check1 = request.getParameterValues("ans1");
			String[] check2 = request.getParameterValues("ans2");
			String[] check3 = request.getParameterValues("ans3");
			String[] check4 = request.getParameterValues("ans4");
			int brbod = Integer.parseInt(request.getParameter("bod"));
			
			Question q = new Question();
			Quiz quiz = quizService.findByName(name);
			
			//Ako nema pitanja u bazi
			if(questionService.findAll().isEmpty()) {
					
				Answer answer1=new Answer();
				Answer answer2=new Answer();
				Answer answer3=new Answer();
				Answer answer4=new Answer();
						
				q.setQuestionId(0);
				q.setAnsweredStatus(false);
				q.setQuestionPoints(brbod);
				q.setQuestionText(qTxt);
					
				answer1.setAnswer(aTxt1);
				answer2.setAnswer(aTxt2);
				answer3.setAnswer(aTxt3);
				answer4.setAnswer(aTxt4);
				
				if(check1 != null){
					for(String s : check1){
						   if(s.equals("on"))
							   answer1.setCorrectStatus(true);
					}
				}else
					   answer1.setCorrectStatus(false);
				
				
				if(check2 != null){
					for(String s : check2){
						   if(s.equals("on"))
							   answer2.setCorrectStatus(true);
					}
				}else
					   answer2.setCorrectStatus(false);
				
				if(check3 != null){
					for(String s : check3){
						   if(s.equals("on"))
							   answer3.setCorrectStatus(true);
					}
				}else
					   answer3.setCorrectStatus(false);
				
				if(check4 != null){
					for(String s : check4){
						   if(s.equals("on"))
							   answer4.setCorrectStatus(true);
					}
				}else
					   answer4.setCorrectStatus(false);

				
				questionService.create(q,answer1,answer2);
				questionService.add(q, answer3);
				questionService.add(q, answer4);
				quizService.add(quiz,q);
				System.out.println("dodano pitanje");
				
			}else if(questionService.findByText(qTxt)==null) {
			Answer answer5=new Answer();
			Answer answer6=new Answer();
			Answer answer7=new Answer();
			Answer answer8=new Answer();
					
			q.setAnsweredStatus(false);
			q.setQuestionPoints(brbod);
			q.setQuestionText(qTxt);
				
			answer5.setAnswer(aTxt1);
			answer6.setAnswer(aTxt2);
			answer7.setAnswer(aTxt1);
			answer8.setAnswer(aTxt2);
			
			if(check1 != null){
				for(String s : check1){
					   if(s.equals("on"))
						   answer5.setCorrectStatus(true);
				}
			}else
				   answer5.setCorrectStatus(false);
			
			if(check2 != null){
				for(String s : check2){
					   if(s.equals("on"))
						   answer6.setCorrectStatus(true);
				}
			}else
				   answer6.setCorrectStatus(false);
			
			if(check3 != null){
				for(String s : check3){
					   if(s.equals("on"))
						   answer7.setCorrectStatus(true);
				}
			}else
				   answer7.setCorrectStatus(false);
			
			if(check4 != null){
				for(String s : check4){
					   if(s.equals("on"))
						   answer8.setCorrectStatus(true);
				}
			}else
				   answer8.setCorrectStatus(false);

			questionService.create(q,answer5,answer6);
			questionService.add(q, answer7);
			questionService.add(q, answer8);
			
			quizService.add(quiz,q);
			System.out.println("ubaceno");
			}
		}
		System.out.println("izaz iz post");
		doGet(request, response);
	}
	
	public synchronized HashMap<String, ArrayList<String>> getMsg(){
	    return message;	
	}

}
