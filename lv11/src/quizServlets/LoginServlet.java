package quizServlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import quizClasses.User;
import quizDao.UserDao;
import quizDaoServices.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "Admin entry point", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;

	public LoginServlet() {
		super();
		userService = new UserService(new UserDao());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (request.getParameter("logout") != null) {
			request.getSession().invalidate();
		}

		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Map<String, String> messages = new HashMap<String, String>();

		if (username == null || username.isEmpty()) {
			messages.put("username", "Please enter a username");
		}

		if (password == null || password.isEmpty()) {
			messages.put("password", "Please enter a password");
		}

		if (messages.isEmpty()) {
			String usnm=username;

			User user = userService.authenticate(username, password);
			//System.out.println(user != null);
			if (user != null) {

				if(!userService.checkIfLogged(user)){
					request.getSession().setAttribute("user", user);
					//System.out.println(usnm+" USNM");
					userService.LogIn(user);
					if(user.getRole().getRole().equals("common")){
						HttpSession session= request.getSession();
						session.setAttribute("username",usnm);
						response.sendRedirect(request.getContextPath() + "/user/home");
					}
					else if(user.getRole().getRole().equals("admin")){
						request.setAttribute("username", usnm);
						response.sendRedirect(request.getContextPath() + "/admin/home");
					}else{
						request.setAttribute("username", usnm);
						response.sendRedirect(request.getContextPath() + "/admin/editorHome");
					}
					return;
				}
				else{
					userService.LogOut(user.getUsername());
					messages.put("logged", "You are logged already! Trying to log you out!");
				}
				
			} else {
				messages.put("login", "Unknown login, please try again");
			}
		}
		
		request.setAttribute("messages", messages);
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

}
