package com.servlet.filterContainer;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neoCommunicator.Neo4jConfigLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Data.Course;
import Data.Course.CourseLabels;
import Data.CourseInformation;
import neo4j_JVM_API.Neo4JAPI;

/**
 * Get matching courses from database when filtering by course code from frontend
 */
@WebServlet("/GetCourses/FilterByCourseCode")
public class GetCoursesFilterByCourseCode extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Send JSON array as string with all matching courses when searching for course by code
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		CourseInformation[] courses = Neo4jConfigLoader.getApi().filterMethods.filterCourseByTag(CourseLabels.CODE, request.getParameter("filter"));
			
		try {
			JSONArray array = new JSONArray();
			for (CourseInformation course : courses) {
			    JSONObject obj = new JSONObject();
			    obj.put(CourseLabels.NAME.toString(), course.getName());
			    obj.put(CourseLabels.CODE.toString(), course.getCourseCode());
			    obj.put(CourseLabels.YEAR.toString(), course.getStartPeriod().getYear());
			    obj.put(CourseLabels.LP.toString(), course.getStartPeriod().getPeriod());
			    obj.put(CourseLabels.CREDIT.toString(), course.getCredit());
			    obj.put(CourseLabels.EXAMINER.toString(), course.getExaminer());
				obj.put(CourseLabels.DESCRIPTION.toString(), course.getDescription());
			    array.put(obj);
			}
			response.setContentType("text/json");
			response.getWriter().write(array.toString());
		} catch (JSONException exception) {}
		
	}
	
}