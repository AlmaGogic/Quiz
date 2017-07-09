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
		questions = new ArrayList<Question>(quiz.getQuestions());
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
		
		request.setAttribute("questions", gson.toJson(getMsg()));
		request.getRequestDispatcher("/quiz.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String req = request.getParameter("req");
		
		
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

			Question q = new Question();
			Quiz quiz = quizService.findByName(name);
			
			//Ako nema pitanja u bazi
			if(questionService.findAll().isEmpty()) {
					
				Answer answer1=new Answer();
				Answer answer2=new Answer();
						
				q.setQuestionId(0);
				q.setAnsweredStatus(false);
				q.setQuestionPoints(10);
				q.setQuestionText(qTxt);
					
				answer1.setAnswer(aTxt1);
				answer1.setCorrectStatus(true);
				answer2.setAnswer(aTxt2);
					
				answer2.setCorrectStatus(false);
				questionService.create(q,answer1,answer2);
					
				quizService.add(quiz,q);
				System.out.println("dodano pitanje");
				
			}else if(questionService.findByText(qTxt)==null) {
			Answer answer3=new Answer();
			Answer answer4=new Answer();
					
			q.setAnsweredStatus(false);
			q.setQuestionPoints(10);
			q.setQuestionText(qTxt);
			System.out.println(q.getQuestionId()+","+q.getQuestionText()+" "+answer3.getAnswer()+" "+answer4.getAnswer());
				
			answer3.setAnswer(aTxt1);
			answer3.setCorrectStatus(true);
			answer4.setAnswer(aTxt2);

			answer4.setCorrectStatus(false);


			questionService.create(q,answer3,answer4);
			
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
