package com.servlet.filterContainer;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import neoCommunicator.Neo4jConfigLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Data.CourseInformation;
import Data.KC;
import Data.KC.KCLabel;
import Data.Course.CourseLabels;
import neo4j_JVM_API.Neo4JAPI;

/**
 * Find all kc's by name from request
 */
@WebServlet("/GetKCs/FilterByName")
public class GetKCFilterByName extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Send JSON array as string with all matching kc when searching for kc by name
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		KC[] kcs = Neo4jConfigLoader.getApi().filterMethods.filterKCByTag(KC.KCLabel.NAME, request.getParameter("filter"));
		
		try {
			JSONArray array = new JSONArray();
			for (KC kc : kcs) {
				
					JSONObject obj = new JSONObject();
				    obj.put(KCLabel.NAME.toString(), kc.getName());
				    obj.put(KCLabel.TAXONOMYLEVEL.toString(), kc.getTaxonomyLevel());
				    obj.put(KCLabel.TAXONOMY_DESCRIPTION.toString(), kc.getTaxonomyDescription());
				    obj.put(KCLabel.GENERAL_DESCRIPTION.toString(), kc.getGeneralDescription());
				    array.put(obj);
			}
			    
			response.setContentType("text/json");
			response.getWriter().write(array.toString());
		} catch (JSONException exception) {}
		
	}
	
}