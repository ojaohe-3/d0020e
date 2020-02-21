package com.servlet;

import Data.Course;
import Data.KC;
import Data.ProgramSpecialization;
import Data.User;
import neoCommunicator.Neo4jConfigLoader;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * 
 * Responsible for the admin page
 * 
 * 
 * @author Jesper 
 *
 */
@WebServlet("/admin")
public class Admin extends HttpServlet {
    
	
	/**
	 * 
	 * Needs to check that admin is valid
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try { 
			if((boolean)request.getSession().getAttribute("is_admin") == true) {
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			
		} catch(NullPointerException e) {
			
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String head = request.getParameter("head");

		if (head.equals("USER")) {

			user(request);

		}
		if (head.equals("COURSE")) {
			course();
		}
		if (head.equals("KC")) {
			kc();
		}
		if (head.equals("PROGRAM")) {
			program();

		}


	}
	private String user(HttpServletRequest request) throws IOException {


		if (request.equals("CREATE")) {
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");

			Neo4jConfigLoader.getApi().userMethods.addUser(new User(userName, password));

			return "User " + userName + " created";


		}
		if (request.equals("DELETE")) {
			String userName = request.getParameter("userName");

			Neo4jConfigLoader.getApi().userMethods.removeUser(userName);

			return "User " + userName + " removed";

		}
		if (request.equals("MODIFY")) {
			String userName = request.getParameter("userName");
			String  password = request.getParameter("password");

			Neo4jConfigLoader.getApi().userMethods.changeUserPassword(userName, password);

			return "User " + userName + "'s password has been changed";


		}
		if (request.equals("SET_RELATION_TO_COURSE")) {

		}
		if (request.equals("REMOVE_RELATION_TO_COURSE")) {

		}
	}

	private void course() {

	}

	private void kc() {

	}

	private void program() {

	}





}


