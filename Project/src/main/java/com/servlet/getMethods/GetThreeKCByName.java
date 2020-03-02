package com.servlet.getMethods;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import Data.Course;
import Data.CourseDate;
import Data.KC;
import Data.LP;
import neoCommunicator.Neo4jConfigLoader;

/**
 * Get all taxonomy levels from a kc name request
 */
@WebServlet("/GetKC/byName")
public class GetThreeKCByName extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Find all three kc's from request, convert to JSON
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException { 
		
		String name = request.getParameter("name");
		
		KC kc1 = Neo4jConfigLoader.getApi().getMethods.getKCwithTaxonomyLevel(name, 1);
		KC kc2 = Neo4jConfigLoader.getApi().getMethods.getKCwithTaxonomyLevel(name, 2);
		KC kc3 = Neo4jConfigLoader.getApi().getMethods.getKCwithTaxonomyLevel(name, 3);
		
		try {
			
			JSONObject jsonobj = new JSONObject();
			
			jsonobj.put("ONE", kc1.getAsJSON());
			jsonobj.put("TWO", kc2.getAsJSON());
			jsonobj.put("THREE", kc3.getAsJSON());
			
			response.setContentType("text/json");
			
			response.getWriter().write(jsonobj.toString());
			
		} catch (JSONException e) { }
		
	}
	

}