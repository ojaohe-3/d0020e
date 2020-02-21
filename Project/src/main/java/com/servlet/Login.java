package com.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Data.User;
import neo4j_JVM_API.Neo4JAPI;
import neoCommunicator.Neo4jConfigLoader;

/**
 * 
 * 	Responsible for the login page and handling login attempts.
 * 
 * @author Jesper
 *
 */

@WebServlet("/login")
public class Login extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6557231559307292097L;
	
	
	/**
	 * 	Renders out the .jsp file
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try {
			if((boolean)request.getSession().getAttribute("logged_in") == true) {
				request.getSession().invalidate();
			}
		} catch (NullPointerException e) {}
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}
	
	/**
	 * 	Handles the login and sets session cookie for logged_in and is_admin
	 * 	
	 * 	When Teacher.jsp is finished, change the commented code below
	 * 
	 * 	@author Jesper
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User loginAttempt = new User(username, password);
		loginAttempt.hashPassword();
		
		User isLoggedIn = Neo4jConfigLoader.getApi().userMethods.login2(loginAttempt);
		
		// If login method returns null the user is not valid
		if(isLoggedIn == null) {
			Map<String, String> error = new HashMap<String, String>();
			
			request.setAttribute("error", error);
			error.put("err", "Invalid login attempt");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			request.getSession().setAttribute("logged_in", true);
			request.getSession().setAttribute("username", username);
			
			if(isLoggedIn.isAdmintag()) {
				request.getSession().setAttribute("is_admin", true);
				
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			} else {
				request.getSession().setAttribute("is_admin", false);
				
				request.getRequestDispatcher("/teacher.jsp").forward(request, response);
			}
				
			
		}
		
		
		
	}

}
