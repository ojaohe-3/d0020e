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

	
	private void print(String name, String code, float credits, String description,String examiner, int year, LP lp, String[] dev, String[] req) {
		
		System.out.println("name " + name);
		System.out.println("code " + code);
		System.out.println("credits " + credits);
		System.out.println("desc " + description);
		System.out.println("examiner " + examiner);
		System.out.println("year " + year);
		System.out.println("lp " + lp);
		//System.out.println("dev len " + dev.length);
		//System.out.println("req len " + req.length);
		
		
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		System.out.println("YEAH doPost");
		
		
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
				
				System.out.println(developed);
				System.out.println(required);
				
				if(developed != null) {
					String[] dev = developed.split(";;;;");
					for(int i = 0; i < dev.length; i++) {
						String[] s = dev[i].split(";;;");
						updatedCourse.setDevelopedKC(new KC(s[0], null, Integer.parseInt(s[1]), null));				
					}
				}
				
				if(required != null) {
					String[] req = required.split(";;;;");
					for(int i = 0; i < req.length; i++) {
						String[] s = req[i].split(";;;");
						updatedCourse.setRequiredKC(new KC(s[0], null, Integer.parseInt(s[1]), null));
					}
				}
				

				//print(name, courseCode, credits, description, examiner, year, lp, null, null);
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
			System.out.println("NUll execp");
			response.setContentType("text/text");
			response.getWriter().write("Failed, not logged in");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} 
		
		
	}
	
}

