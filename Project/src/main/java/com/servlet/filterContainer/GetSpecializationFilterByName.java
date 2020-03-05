package com.servlet.filterContainer;

import Data.CourseProgram;
import Data.ProgramInformation;
import neoCommunicator.Neo4jConfigLoader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Get programs when searching by name
 */
@WebServlet("/GetSpecializations/FilterByName")
public class GetSpecializationFilterByName extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Send JSON array as string with all matching programs when searching for programs by name
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ProgramInformation[] programs = Neo4jConfigLoader.getApi().filterMethods.filterSpecializationByTag(CourseProgram.ProgramLabels.NAME, request.getParameter("filter"));


        try {
            JSONArray array = new JSONArray();
            for (ProgramInformation program : programs) {
                JSONObject obj = new JSONObject();
                obj.put(CourseProgram.ProgramLabels.NAME.toString(), program.getName());
                obj.put(CourseProgram.ProgramLabels.CODE.toString(), program.getCode());
                obj.put(CourseProgram.ProgramLabels.DESCRIPTION.toString(), program.getDescription());
                obj.put(CourseProgram.ProgramLabels.YEAR.toString(), program.getStartDate().getYear());
                obj.put(CourseProgram.ProgramLabels.LP.toString(), program.getStartDate().getPeriod());
                obj.put(CourseProgram.ProgramLabels.CREDITS.toString(), program.getCredits());
                //obj.put(ProgramLabels.READING_PERIODS.toString(), program.getReadingPeriods());
                array.put(obj);
            }
            response.setContentType("text/json");
            response.getWriter().write(array.toString());
        } catch (JSONException exception) {}

    }

}