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

import Data.ProgramInformation;
import Data.Topic;
import Data.CourseProgram.ProgramLabels;
import neo4j_JVM_API.Neo4JAPI;
@WebServlet("/GetTopics/FilterByName")
public class GetTopicFilterByName extends HttpServlet {

	/**
	 *  No idea what this is.. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Send JSON array with all matching programs when searching for programs by name
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Topic[] topics = Neo4jConfigLoader.getApi().filterMethods.filterTopicByName(request.getParameter("filter"));

		try {
			JSONArray array = new JSONArray();
			for (Topic topic : topics) {
			    JSONObject obj = new JSONObject();
			    obj.put(Topic.TopicLabels.TITLE.toString(), topic.toString());
			    array.put(obj);
			}
			response.setContentType("text/json");
			response.getWriter().write(array.toString());
		} catch (JSONException exception) {
		}
		
	}
	
}