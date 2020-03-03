package com.servlet.filterContainer;

import java.io.IOException;

import neoCommunicator.Neo4jConfigLoader;
import org.json.JSONArray;
import org.json.JSONException;

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
 * @author Jesper
 */

@WebServlet("/GetCourses/FilterByCourseName")
public class GetCoursesFilterByCourseName extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Send JSON array as string with all matching courses when searching for course by name
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		CourseInformation[] courses = Neo4jConfigLoader.getApi().filterMethods.filterCourseByTag(CourseLabels.NAME, request.getParameter("filter").toString());

		JSONObject json = new JSONObject();
		response.setContentType("text/plain");
		response.getWriter().write("");
		System.out.println("Got from db : " + courses.length);
		
		try {
			JSONArray jArray = new JSONArray();
			
			for(CourseInformation ci: courses) {
				JSONObject jobj = new JSONObject();
				
				jobj.put(CourseLabels.NAME.toString(), ci.getName());
				jobj.put(CourseLabels.CODE.toString(), ci.getCourseCode());
				jobj.put(CourseLabels.LP.toString(), ci.getStartPeriod().getPeriod());
				jobj.put(CourseLabels.YEAR.toString(), ci.getStartPeriod().getYear());
				jobj.put(CourseLabels.EXAMINER.toString(), ci.getExaminer());
				jobj.put(CourseLabels.CREDIT.toString(), ci.getCredit());
				jobj.put(CourseLabels.DESCRIPTION.toString(), ci.getDescription());
				jArray.put(jobj);

			}
			response.setContentType("text/json");
			response.getWriter().write(jArray.toString());
			
		} catch (JSONException ex) { }

	}
	
}