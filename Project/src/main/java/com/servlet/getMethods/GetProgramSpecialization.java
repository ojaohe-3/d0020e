package com.servlet.getMethods;
import Data.*;
import neoCommunicator.Neo4jConfigLoader;
import org.json.JSONException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Get program by code from request and return as JSON
 */
@WebServlet("/GetSpecialization/getCourses")
public class GetProgramSpecialization extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println(request.getParameter("startyear"));

        String code = request.getParameter("code");
        String name = request.getParameter("name");
        int year = Integer.parseInt(request.getParameter("startyear"));
        LP lp = null;
        try {
            lp = LP.valueOf(request.getParameter("startperiod"));
        } catch(IllegalArgumentException e) {
            System.out.println("Exception " + request.getParameter("startperiod"));
            lp = LP.getByString(request.getParameter("startperiod"));
        }
        System.out.println(code + " " + year + " " + lp);
        // This will return a course program with a list of all courses in ascending order by year.
        // The nifty feature with this is that all courses within one year will be grouped together. <- used in the canvas.
        ProgramSpecialization data = Neo4jConfigLoader.getApi().getMethods.getProgramSpecialization(name,code,new CourseDate(year,lp),Course.CourseLabels.YEAR,true);
        try {
            String json = data.getAsJson().toString().replaceAll("\\\\","").replaceAll("\"\\{",
                    "{").replaceAll("}\"","}");
            response.setContentType("text/json");
            response.getWriter().write(json);

        } catch (JSONException e) { }
    }
}
