package com.servlet.filterContainer;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Data.Course;
import Data.Course.CourseLabels;
import Data.CourseInformation;
import neo4j_JVM_API.Neo4JAPI;

@WebServlet("/GetCourses/FilterByCourseCode")
public class GetCoursesFilterByCourseCode extends HttpServlet {

	/**
	 *  No idea what this is.. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Send JSON array with all matching courses when searching for course by code
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		CourseInformation[] courses = Neo4JAPI.filterMethods.filterCourseByTag(CourseLabels.CODE, request.getParameter("filter"));
			
		try {
			JSONArray array = new JSONArray();
			for (CourseInformation course : courses) {
			    JSONObject obj = new JSONObject();
			    obj.put(CourseLabels.NAME.toString(), course.getName().replaceAll("\"", ""));
			    obj.put(CourseLabels.CODE.toString(), course.getCourseCode().replaceAll("\"", ""));
			    obj.put(CourseLabels.YEAR.toString(), course.getStartPeriod().getYear());
			    obj.put(CourseLabels.LP.toString(), course.getStartPeriod().getPeriod());
			    obj.put(CourseLabels.CREDIT.toString(), course.getCredit());
			    obj.put(CourseLabels.EXAMINER.toString(), course.getExaminer().replaceAll("\"", ""));
			    array.put(obj);
			}
			response.setContentType("text/json");
			response.getWriter().write(array.toString());
		} catch (JSONException exception) {}
		
		
		
	}
	
}