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

import quizClasses.Role;
import quizClasses.User;
import quizDao.UserDao;
import quizDaoServices.UserService;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/admin/users")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, ArrayList<String>> message;
	private UserService userService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        userService = new UserService(new UserDao());
        message = new HashMap<String, ArrayList<String>>();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<User> users = new ArrayList<User>(userService.findAll());
		Gson gson = new Gson();
		
		for(int i = 0; i < users.size(); i++){
			String username = users.get(i).getUsername();
			ArrayList<String> param = new ArrayList<String>();
			
			param.add(users.get(i).getFirstName());
			param.add(users.get(i).getLastName());
			param.add(users.get(i).getEmail());
			param.add(users.get(i).getPassword());
			param.add(users.get(i).getRole().getRole());
			
			message.put(username, param);
		}
		
		request.setAttribute("users", gson.toJson(getMsg()));
		request.getRequestDispatcher("/user.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String req = request.getParameter("req");
		
		if(req != null){
			if(req.equals("removeUser")){
				userService.delete(request.getParameter("user"));
				
			}
		}else{
			String username = request.getParameter("username");
			String firstName = request.getParameter("firstName");
			String lastName = request.getParameter("lastName");
			String email = request.getParameter("email");
			String pass = request.getParameter("pass");
			String r = request.getParameter("role");
			
			User user = new User();
			user.setUsername(username);
			user.setFirstName(firstName);
			user.setEmail(email);
			user.setLastName(lastName);
			user.setPassword(pass);
			
			Role role = new Role();
			role.setRole(r);
			
			user.setRole(role);
			
			userService.create(user, role);
		}
		doGet(request, response);
	}

	public synchronized HashMap<String, ArrayList<String>> getMsg(){
	    return message;	
	}
	
}
