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

import quizClasses.Quiz;
import quizDao.QuizDao;
import quizDaoServices.QuizService;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("/admin/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private QuizService quizService;
	private HashMap<String, String> messages;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	public HomeServlet() {
		super();
		messages = new HashMap<String, String>();
		quizService = new QuizService(new QuizDao());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		ArrayList<Quiz> qList = new ArrayList<Quiz>(quizService.findAll());
		
		for(int i = 0; i<qList.size(); i++){
			messages.put(qList.get(i).getQuizName(), "https://4a7efb2d53317100f611-1d7064c4f7b6de25658a4199efb34975.ssl.cf1.rackcdn.com/dark-days-oracle-kills-java-plug-in-dead-showcase_image-9-p-2047.jpg");
		}
		
	    request.setAttribute("q", gson.toJson(getMsg()));
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}
	
	public synchronized HashMap<String, String> getMsg(){
	    return messages;	
	}

}
