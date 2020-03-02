package com.servlet.getMethods;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Data.Course;
import Data.CourseDate;
import Data.CourseOrder;
import Data.CourseProgram;
import Data.LP;
import neoCommunicator.Neo4jConfigLoader;

/**
 * Get list with courses in program from request by code, year, lp if logged in as admin
 */
@WebServlet("/getCoursesInProgram")
public class GetCoursesInProgram extends HttpServlet {

	/**
	 * Checks if admin then reads out the program from database and find course order
	 * prints out every course to a string with name, year and lp
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    	try {
    		if((boolean)request.getSession().getAttribute("is_admin") == true) {
    	    	
    			String code = request.getParameter("code");
    			LP lp = LP.getByString(request.getParameter("lp"));
    			int year = Integer.parseInt(request.getParameter("year"));
    			
    			CourseProgram cp = Neo4jConfigLoader.getApi().getMethods.getProgram(code, new CourseDate(year, lp));
    			
    			String s = "";
    			
    			ArrayList<Course> co = cp.getCourseOrder();

				for (Course c: co) {
					s += c.getName() + ": " + c.getStartPeriod().getYear() + ": " + c.getStartPeriod().getPeriod() + "\n";
				}
    			
    	        response.setContentType("text/text");
    	        response.getWriter().write(s);
        	}
    	} catch (NullPointerException e) {}
    	
    }
    
}