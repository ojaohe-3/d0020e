package com.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Data.Course;
import neo4j_JVM_API.Neo4JAPI;


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

@WebServlet("project/GetCoursesFilterByCourseName")
public class GetCoursesFilterByCourseName extends HttpServlet {

	/**
	 *  No idea what this is.. 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		//Replace this.
		Neo4JAPI neoapi = new Neo4JAPI("bolt://130.240.200.254:7687", "neo4j", "neo4j-d0020e");
		
		String[] courses = neoapi.filterMethods.filterCourseByName(request.getParameter("filter").trim());
		
		String s = "";
		for(String c: courses) {
			s += c + ", ";
		}
		response.setContentType("text/plain");
		response.getWriter().write(s);
		
	}
	
}
