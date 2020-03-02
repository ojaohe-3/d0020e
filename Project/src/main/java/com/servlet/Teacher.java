package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Data.Course;
import Data.CourseDate;
//import Data.Credits;
import Data.KC;
import Data.LP;
import neoCommunicator.Neo4jConfigLoader;

import java.io.IOException;


/**
 * 	Servlet for handling the teacher page
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
	 * 	Renders out the .jsp file if the Teacher is logged in and is not an admin.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try {
			if((boolean)request.getSession().getAttribute("logged_in") == true && (boolean)request.getSession().getAttribute("is_admin") == false) {
				
				Course[] courses = Neo4jConfigLoader.getApi().userMethods.getUserCourses((String)request.getSession().getAttribute("username"));
				request.setAttribute("courses", courses);
				request.getRequestDispatcher("/teacher.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}
		} catch(NullPointerException e) {
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
		
	}

	
	/**
	 * 	This method is called when a teacher is making changes to a course. 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		try {
			if((boolean)request.getSession().getAttribute("logged_in") == true) {
				
				System.out.println("logged in");
				
				String name = request.getParameter("name");
				String courseCode = request.getParameter("code");
				float credits = Float.parseFloat(request.getParameter("credits"));
				String description = request.getParameter("description");
				String examiner = request.getParameter("examiner");
				int year = Integer.parseInt(request.getParameter("startyear"));
				LP lp = LP.getByStringByText(request.getParameter("startperiod"));
				
				
				Course updatedCourse = new Course(name, courseCode, credits, description, examiner, new CourseDate(year, lp));
				
				
				String developed = request.getParameter("developedKCs");
				String required = request.getParameter("requiredKCs");
				
				/*
				 * To solve the problem with convering a Javascript array to a java array we decided to use ;;;; to separate each tuple in the array
				 *  and use ;;; the separate each element in the tuple
				 */
				if(developed != null) {
					String[] dev = developed.split(";;;;");
					for(int i = 0; i < dev.length; i++) {
						String[] s = dev[i].split(";;;");
						if(s.length > 1) {
							updatedCourse.setDevelopedKC(new KC(s[0], null, Integer.parseInt(s[1]), null));				
						}
					}
				}
				
				if(required != null) {
					String[] req = required.split(";;;;");
					for(int i = 0; i < req.length; i++) {
						String[] s = req[i].split(";;;");
						if(s.length > 1) {
							updatedCourse.setRequiredKC(new KC(s[0], null, Integer.parseInt(s[1]), null));
						}
					}
				}
				

				Neo4jConfigLoader.getApi().modifyMethods.deleteKCsFromCourseAndAddTheNewOnes(updatedCourse);
				Neo4jConfigLoader.getApi().modifyMethods.editCourse(courseCode, new CourseDate(year,lp), updatedCourse);
				
				response.setContentType("text/text");
				response.getWriter().write("Success");
				
			} else {
				response.setContentType("text/text");
				response.getWriter().write("Failed, not logged in");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			
		} catch(NullPointerException e) {
			response.setContentType("text/text");
			response.getWriter().write("Failed, not logged in");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} 
		
		
	}
	
}

