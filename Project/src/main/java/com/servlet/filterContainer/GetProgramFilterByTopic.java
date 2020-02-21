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
import Data.CourseProgram.ProgramLabels;
import neo4j_JVM_API.Neo4JAPI;

@WebServlet("/GetPrograms/FilterByTopic")
public class GetProgramFilterByTopic extends HttpServlet {

	/**
	 *  No idea what this is.. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Send JSON array with all matching programs when searching for programs by topic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ProgramInformation[] programs = Neo4jConfigLoader.getApi().filterMethods.filterProgramByTopic(request.getParameter("filter"));
		
		try {
			JSONArray array = new JSONArray();
			for (ProgramInformation program : programs) {
			    JSONObject obj = new JSONObject();
			    obj.put(ProgramLabels.NAME.toString(), program.getName().replaceAll("\"", ""));
			    obj.put(ProgramLabels.CODE.toString(), program.getCode().replaceAll("\"", ""));
			    obj.put(ProgramLabels.DESCRIPTION.toString(), program.getDescription().replaceAll("\"", ""));
			    obj.put(ProgramLabels.YEAR.toString(), program.getStartDate().getYear());
			    obj.put(ProgramLabels.LP.toString(), program.getStartDate().getPeriod());
			    obj.put(ProgramLabels.CREDITS.toString(), program.getCredits());
			    //obj.put(ProgramLabels.READING_PERIODS.toString(), program.getReadingPeriods());
			    array.put(obj);
			}
			response.setContentType("text/json");
			response.getWriter().write(array.toString());
		} catch (JSONException exception) {}
		
	}
	
}