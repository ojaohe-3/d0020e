package com.servlet.getMethods;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import Data.CourseDate;
import Data.CourseProgram;
import Data.LP;
import neoCommunicator.Neo4jConfigLoader;

@WebServlet("/getAllUsers")
/**
 * Print out all users from database if logged in as admin
 */
public class GetAllUsers extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    	try {
    		if((boolean)request.getSession().getAttribute("is_admin") == true) {
    	    	String[] usernames = Neo4jConfigLoader.getApi().userMethods.getAvaliableUsers();
    	    	
    	    	String s = "";
    	    	for(String name: usernames) {
    	    		s += name+"\n";
    	    	}
    	        response.setContentType("text/text");
    	        response.getWriter().write(s);
        	}
    	} catch (NullPointerException e) {}
    	
    }
    
}
