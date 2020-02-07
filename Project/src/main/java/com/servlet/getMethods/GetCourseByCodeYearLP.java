package com.servlet.getMethods;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import Data.Course;
import Data.CourseDate;
import Data.KC;
import Data.LP;
import neo4j_JVM_API.Neo4JAPI;


@WebServlet("/GetCourse/byCodeYearLP")
public class GetCourseByCodeYearLP extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException { 
		
		

		Course course = Neo4JAPI.getMethods.getCourse(request.getParameter("courseCode"), new CourseDate(Integer.parseInt(request.getParameter("year")), LP.valueOf(request.getParameter("lp"))));
		
		
		try {
			response.setContentType("text/json");
			response.getWriter().write(course.getAsJson());
			
		} catch (JSONException e) { }
		
	}
	

}
