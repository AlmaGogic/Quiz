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
import quizDao.QuizDao;
import quizDao.UserDao;
import quizDaoServices.QuizService;
import quizDaoServices.UserService;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/admin/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private QuizService quizService;
	private UserService userService;
	private HashMap<String, String> messages;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	public HomeServlet() {
		super();
		messages = new HashMap<String, String>();
		quizService = new QuizService(new QuizDao());
		userService = new UserService(new UserDao());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		ArrayList<Quiz> qList = new ArrayList<Quiz>(quizService.findAll());
		String logout = request.getParameter("logout");
		
		String username=(String) request.getSession().getAttribute("username");
		
		if(logout!=null&&logout.equals("true")){
			if(username!=null){	
				userService.LogOut(username);
				request.getSession().invalidate();
			}
			response.sendRedirect(request.getContextPath()+"/login");
		}else{
		
		for(int i = 0; i<qList.size(); i++){
			messages.put(qList.get(i).getQuizName(), "https://4a7efb2d53317100f611-1d7064c4f7b6de25658a4199efb34975.ssl.cf1.rackcdn.com/dark-days-oracle-kills-java-plug-in-dead-showcase_image-9-p-2047.jpg");
		}
		
	    request.setAttribute("q", gson.toJson(getMsg()));
		request.getRequestDispatcher("/home.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String req = request.getParameter("req");
		
		if(req != null){
			if(req.equals("addQuiz")){
				String quizName = request.getParameter("quiz");
				Quiz newQuiz = new Quiz();
				newQuiz.setQuizName(quizName);
				quizService.create(newQuiz, null);
			}
		}
		
		doGet(request, response);
	}
		
	public synchronized HashMap<String, String> getMsg(){
	    return messages;	
	}

}
