package com.servlet.filterContainer;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Data.Course;
import neo4j_JVM_API.*;
import neoCommunicator.Neo4jCommunicator;

/**
 * 
 * Will handle the Filter course by course name
 * 
 * 			For testing I am creating a new  Neoapi, remove it later.. 
 * 
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

		response.setContentType("text/plain");
		response.getWriter().write("This is the response from filter by course name");
		
	}
	
}

