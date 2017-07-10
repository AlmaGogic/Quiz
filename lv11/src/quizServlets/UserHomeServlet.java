package quizServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import quizClasses.Answer;
import quizClasses.Question;
import quizClasses.Quiz;
import quizClasses.Result;
import quizClasses.Role;
import quizClasses.User;
import quizDao.QuestionDao;
import quizDao.QuizDao;
import quizDao.ResultDao;
import quizDao.RoleDao;
import quizDao.UserDao;
import quizDaoServices.QuestionService;
import quizDaoServices.QuizService;
import quizDaoServices.ResultService;
import quizDaoServices.RoleService;
import quizDaoServices.UserService;

/**
 * Servlet implementation class UserHomeServlet
 */
@WebServlet("/user/home")
public class UserHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private QuizService quizService;
	private QuestionService questionService;
	private UserService userService;
	private RoleService roleService;
	private ResultService resultService;
	private HashMap<Object, Object> message;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserHomeServlet() {
        super();
        roleService = new RoleService(new RoleDao());
        questionService = new QuestionService(new QuestionDao());
        quizService = new QuizService(new QuizDao());
        userService = new UserService(new UserDao());
        resultService = new ResultService(new ResultDao());
        message = new HashMap<Object,Object>();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("search");
		String logout = request.getParameter("logout");
		String task = request.getParameter("t");
		String quizParam = request.getParameter("quiz");
		//System.out.println("AAA"+task);
		QuizService q=new QuizService(new QuizDao());
		
		String username=(String) request.getSession().getAttribute("username");
		
		if(logout!=null&&logout.equals("true")){
			if(username!=null){	
				userService.LogOut(username);
				request.getSession().invalidate();
			}
			//System.out.println("AAAAAAA");
			response.sendRedirect(request.getContextPath()+"/login");
		}
		else{
		
		if(search!=null){
			if(search.equals("All")||search.equals("all")||search.equals("ALL")){
				Collection<Quiz>quiz=quizService.findAll();
				System.out.println(quiz.size());
				request.setAttribute("quizzes", quiz);
				request.setAttribute("task", "search");
				request.setAttribute("results", resultService.findAll());
			}
			else{
				Collection<Quiz>quiz=quizService.findByName(search);
				System.out.println(quiz.size());
				request.setAttribute("quizzes", quiz);
				request.setAttribute("task", "search");
				request.setAttribute("results", resultService.findAll());
			}
		}
		else{

			if(quizParam!=null){
				Quiz qz=quizService.findOneByName(quizParam);
				String uname=(String)request.getSession().getAttribute("username");
				if(uname!=null){
					boolean find=false;
					User u=userService.findByUsername(uname);
					Collection<Result> results=resultService.findAll();
					for(Result r:results){
						if(qz.getQuizName().equals(r.getQuiz().getQuizName())){
							find=true;
							break;
						}
					}
					if(find){
						request.setAttribute("unanswered", userService.findUnanswered(u,qz));
						
					}
					else{
						request.setAttribute("unanswered", qz.getQuestions());
					}
					request.setAttribute("user", u);
					HashMap<Object,Object> quizInfo=new HashMap<Object,Object>();
					HashMap<Object,Object> questionInfo=new HashMap<Object,Object>();
					HashMap<Object,Object> answerInfo=new HashMap<Object,Object>();
					
					
					
				}
				else{
					System.out.println("Session has expired!");
				}

				request.setAttribute("quiz", quizParam);
			}
			if(task!=null){
				if(task.equals("profile")){
					request.setAttribute("task", "profile");
				}
				else{
					User user=userService.findByUsername(username);
					try{
						Collection<Result>results=user.getUserResults();
						request.setAttribute("results", results);
					}
					catch(Exception e){
						request.setAttribute("results", "none");
					}
					request.setAttribute("task", "results");
				}
			}
		}
			System.out.println(search);
			request.getRequestDispatcher("/userHome.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String pointsAjax = request.getParameter("points");
		String questionAjax = request.getParameter("questionAjax");
		String quizAjax = request.getParameter("quiz");
		
		String email = request.getParameter("email");
		System.out.println(firstName+" "+lastName+" "+ username+" "+password+" "+email);
		String uname=(String) request.getSession().getAttribute("username");
		if(uname==null){
			System.out.println("III");			
		}
		else{
			if(username==null){
				System.out.println(pointsAjax+","+questionAjax+","+quizAjax);
			}
			if(pointsAjax!=null&&questionAjax!=null&&quizAjax!=null){
				Result result=new Result();
				Quiz q=quizService.findOneByName(quizAjax);
				User u=userService.findByUsername(uname);
				result.setTotalPoints(Integer.parseInt(pointsAjax));
				result.setQuiz(q);
				q.addQuizResult(result);
				result.setFirstName(u.getFirstName());
				result.setLastName(u.getLastName());
				result.setEmail(u.getEmail());
				result.setUser(u);
				u.addUserResult(result);
				userService.update(username, u);
				quizService.update(quizAjax, q);
				resultService.create(q, result);
			
				System.out.println(questionAjax);
			}
			if(firstName!=null&&lastName!=null&&username!=null&&username!=null&&password!=null&&email!=null){
				User user1=userService.findByUsername(uname);
				User user2=userService.findByUsername(username);
				
				if(user1!=null&&user2==null){
					Role role=roleService.findByName("common");
					User newUser= new User();
					newUser.setFirstName(firstName);
					newUser.setLastName(lastName);
					newUser.setEmail(email);
					newUser.setPassword(quizSecurityUtil.SecurityUtil.hashPassword(password));
					newUser.setUsername(username);
					newUser.setRole(role);
					userService.update(uname, newUser);
					request.getSession().setAttribute("username", username);
					
				}
			}
		}
		doGet(request, response);

		
	}

}
