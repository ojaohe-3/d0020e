package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Data.Course;
import neoCommunicator.Neo4jConfigLoader;

import java.io.IOException;


/**
 * 	Servlet for handling the teacher page
 * 
 * 
 * @author jesper
 */

@WebServlet("/teacher")
public class Teacher extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 	Renders out the .jsp file
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try {
			if((boolean)request.getSession().getAttribute("logged_in") == true && (boolean)request.getSession().getAttribute("is_admin") == false) {
				
				Course[] courses = Neo4jConfigLoader.getApi().userMethods.getUserCourses((String)request.getSession().getAttribute("username"));
				request.setAttribute("courses", courses);
				request.getRequestDispatcher("/teacher.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		} catch(NullPointerException e) {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
	}
	
}

