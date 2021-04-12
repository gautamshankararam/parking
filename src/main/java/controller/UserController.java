package controller;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.UserDAO;
import model.User;


public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public UserController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] path = request.getPathInfo().split("/");
		String json = null;
		Gson gson= new Gson();
		if(path.length==0) {
			json = gson.toJson(UserDAO.getUsers());
		}
		if(path.length==2) {
			json = gson.toJson(UserDAO.getUser(path[1]));
		}
		response.setContentType("application/json");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.getWriter().println(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] path = request.getPathInfo().split("/");
		String json = null;
		Gson gson= new Gson();
		if(path.length==0) {
			InputStreamReader reader = new InputStreamReader(request.getInputStream());
			User user = gson.fromJson(reader, User.class);
			json = gson.toJson(user);
			UserDAO.signup(user);
		}
		
		response.setContentType("application/json");
		response.getWriter().println(json);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     
		String[] path = req.getPathInfo().split("/");
		int id;
		
		Gson gson= new Gson();
		if(path.length==2) {
			InputStreamReader reader = new InputStreamReader(req.getInputStream());
			User user = gson.fromJson(reader, User.class);
			id = Integer.parseInt(path[1]);
			
			UserDAO.updateUser(user,id);
			
			
		}
	}
	
	

}
