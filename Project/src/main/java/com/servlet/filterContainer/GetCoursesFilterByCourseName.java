package com.servlet.filterContainer;

import java.io.IOException;

<<<<<<< HEAD

=======
>>>>>>> branch 'Neoapi_filter_c_tset' of https://github.com/ojaohe-3/d0020e
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
				
				jArray.put(jobj);
			
				
				
			}
			response.setContentType("text/json");
			response.getWriter().write(jArray.toString());
			
			System.out.println(jArray.toString());
		} catch (JSONException ex) { }
<<<<<<< HEAD
		
=======
>>>>>>> branch 'Neoapi_filter_c_tset' of https://github.com/ojaohe-3/d0020e

		
	}
	
}

