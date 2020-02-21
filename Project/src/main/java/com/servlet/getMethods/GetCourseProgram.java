package com.servlet.getMethods;

import Data.CourseDate;
import Data.CourseProgram;
import Data.LP;
import neoCommunicator.Neo4jConfigLoader;
import org.json.JSONException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GetProgram/getCourses")
public class GetCourseProgram extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String code = request.getParameter("code");
        int year = Integer.parseInt(request.getParameter("startyear"));
        LP lp = LP.valueOf(request.getParameter("startperiod"));
        CourseProgram data = Neo4jConfigLoader.getApi().getMethods.getProgram(code,
                new CourseDate(year, lp));
        try {
            String json = data.getAsJson().toString();
            response.setContentType("text/json");
            response.getWriter().write(json);

        } catch (JSONException e) { }
    }
}
