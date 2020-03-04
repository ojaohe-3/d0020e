package com.servlet.getMethods;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neoCommunicator.Neo4jConfigLoader;
import org.json.JSONException;
import org.json.JSONObject;

import Data.Course;
import Data.CourseDate;
import Data.KC;
import Data.LP;
import neo4j_JVM_API.Neo4JAPI;

/**
 * Find course in database 
 */
@WebServlet("/GetCourse/byCodeYearLP")
public class GetCourseByCodeYearLP extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Find course from request and send it as a response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException { 
		
		Course course = Neo4jConfigLoader.getApi().getMethods.getCourse(request.getParameter("courseCode"), new CourseDate(Integer.parseInt(request.getParameter("year")), LP.valueOf(request.getParameter("lp"))));
		response.setContentType("text/json");
		response.getWriter().write(course.toString());
		
	}
	
}
