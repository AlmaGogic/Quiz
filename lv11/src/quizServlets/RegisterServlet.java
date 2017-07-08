package quizServlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quizClasses.Role;
import quizClasses.User;
import quizDao.RoleDao;
import quizDao.UserDao;
import quizDaoServices.RoleService;
import quizDaoServices.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "User registration", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;
	private RoleService roleService;

	public RegisterServlet() {
		super();
		userService = new UserService(new UserDao());
		roleService = new RoleService(new RoleDao());
		
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		Map<String, String> messages = new HashMap<String, String>();

		if (firstName == null || firstName.isEmpty()) {
			messages.put("firstname", "Please enter your firstname");
		}
		if (lastName == null || lastName.isEmpty()) {
			messages.put("lastname", "Please enter your lastname");
		}
		
		if (username == null || username.isEmpty()) {
			messages.put("username", "Please enter a username");
		}

		if (password == null || password.isEmpty()) {
			messages.put("password", "Please enter a password");
		}
		
		if (email == null || email.isEmpty()) {
			messages.put("email", "Please enter an email");
		}

		if (messages.isEmpty()) {
			User tempUser=userService.findByUsername(username);
			if(tempUser == null){
				Role role=roleService.findByName("common"); 
				if(role == null){
					Role admin = new Role();
					Role editor = new Role();
					Role common = new Role();

					admin.setRole("admin");
					editor.setRole("editor");
					common.setRole("common");
						
					roleService.create(admin);
					roleService.create(editor);
					roleService.create(common);
				}
				if(username.equals("Alma")||username.equals("alma")||username.equals("admin")){
					role=roleService.findByName("admin");
				}
				else if(username.equals("Mladen")||username.equals("mladen")||username.equals("editor")){
					role=roleService.findByName("editor");
				}
				else{
					role=roleService.findByName("common");
				}
					
				User user= new User();
				user.setEmail(email);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setPassword(password);
				user.setRole(role);
				role.addUserWithRole(user);
				
				user.setUsername(username);
				userService.create(user, role);
				
				request.getSession().setAttribute("user", user);
				response.sendRedirect(request.getContextPath() + "/login");
				return;
				
			}
			else{	
				messages.put("register", "Username already taken!");
			}
		}
		

		request.setAttribute("messages", messages);
		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}

}
