package com.servlet.filterContainer;

import java.io.IOException;

import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Data.Course;
import Data.Course.CourseLabels;
import Data.CourseInformation;
import neo4j_JVM_API.*;
import neoCommunicator.Neo4jCommunicator;

/**
 * 
 * Will handle the Filter course by course name
 * 
 * 			Using a static Neo4jAPI..
 * @author Jesper
 *
 *
 */

@WebServlet("/GetCourses/FilterByCourseName")
public class GetCoursesFilterByCourseName extends HttpServlet {

	/**
	 *  No idea what this is.. 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		
		
		CourseInformation[] courses = Neo4JAPI.filterMethods.filterCourseByTag(CourseLabels.NAME, request.getParameter("filter").toString());
		
		JSONObject json = new JSONObject();
		
		response.setContentType("text/plain");
		response.getWriter().write("");
		
	}
	
}

