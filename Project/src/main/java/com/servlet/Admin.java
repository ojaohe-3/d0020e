package com.servlet;

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
		
		System.out.println("WARNING, NOT CHECKING IF ADMIN IS LOGGED IN");
		
		request.getRequestDispatcher("/admin.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	}
	
}


