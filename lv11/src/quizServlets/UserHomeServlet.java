package quizServlets;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private UserService userService;
	private RoleService roleService;
	private ResultService resultService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserHomeServlet() {
        super();
        roleService = new RoleService(new RoleDao());
        quizService = new QuizService(new QuizDao());
        userService = new UserService(new UserDao());
        resultService = new ResultService(new ResultDao());
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("search");
		String logout = request.getParameter("logout");
		String task = request.getParameter("t");
		System.out.println("AAA"+task);
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
			}
			else{
				Collection<Quiz>quiz=quizService.findByName(search);
				System.out.println(quiz.size());
				request.setAttribute("quizzes", quiz);
				request.setAttribute("task", "search");
			}
		}
		else{

			
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
		String email = request.getParameter("email");
		System.out.println(firstName+" "+lastName+" "+ username+" "+password+" "+email);
		String uname=(String) request.getSession().getAttribute("username");
		if(uname==null){
			
		}
		else{
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
