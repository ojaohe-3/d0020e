package com.servlet.getMethods;

import Data.CourseProgram;
import neoCommunicator.Neo4jConfigLoader;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GetProgram/getCourses")
public class GetCourseProgram extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        super.service(req, res);

        try {
            JSONObject obj = new JSONObject();
            obj.put("g","g");
            res.setContentType("text/json");
            res.getWriter().write("hello");

        } catch (JSONException e) { }
    }
}
