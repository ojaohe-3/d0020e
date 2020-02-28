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

@WebServlet("/getCoursesInProgram")
public class GetCoursesInProgram extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    
    	try {
    		if((boolean)request.getSession().getAttribute("is_admin") == true) {
    	    	
    			String code = request.getParameter("code");
    			LP lp = LP.getByString(request.getParameter("lp"));
    			int year = Integer.parseInt(request.getParameter("year"));
    			
    			CourseProgram cp = Neo4jConfigLoader.getApi().getMethods.getProgram(code, new CourseDate(year, lp));
    			
    			String s = "";
    			
    			ArrayList<Course> co = cp.getCourseOrder();
    			/*for(int i = 0; i < co.getReadingPeriods(); i++) {
    				for(int j = 0; j < co.COURSES_PER_PERIOD; j++) {
    					if(co.getCourseArray()[j][i] != null) {
    						Course c = co.getCourseArray()[j][i];
    						s += c.getName() + ": " + c.getStartPeriod().getYear() + ": " + c.getStartPeriod().getPeriod() + "\n";
    					}
    				}
    			}*/
				for (Course c: co) {
					s += c.getName() + ": " + c.getStartPeriod().getYear() + ": " + c.getStartPeriod().getPeriod() + "\n";
				}
    			
    	        response.setContentType("text/text");
    	        response.getWriter().write(s);
        	}
    	} catch (NullPointerException e) {}
    	
       
    }
    
}