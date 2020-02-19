package com.servlet;

import Data.*;
import neo4j_JVM_API.GetMethods;
import neo4j_JVM_API.Neo4JAPI;
import neoCommunicator.Neo4jConfigLoader;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * 
 * Responsible for the admin page
 * 
 * 
 * @author Jesper 
 *
 */
@WebServlet("/admin")
public class Admin extends HttpServlet {
    
	
	/**
	 * 
	 * Needs to check that admin is valid
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try { 
			if((boolean)request.getSession().getAttribute("is_admin") == true) {
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			
		} catch(NullPointerException e) {
			
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String head = request.getParameter("head");

		if (head.equals("USER")) {

			user(request);

		}
		if (head.equals("COURSE")) {
			course(request);
		}
		if (head.equals("KC")) {
			kc(request);
		}
		if (head.equals("PROGRAM")) {
			program(request);

		}


	}
	private String user(HttpServletRequest request) throws IOException {

		if (request.equals("CREATE")) {
			String userName = request.getParameter("userName");
			String password = request.getParameter("password");

			Neo4jConfigLoader.getApi().userMethods.addUser(new User(userName, password));

			return "User " + userName + " created";


		}
		if (request.equals("DELETE")) {
			String userName = request.getParameter("userName");

			Neo4jConfigLoader.getApi().userMethods.removeUser(userName);

			return "User " + userName + " removed";

		}
		if (request.equals("MODIFY")) {
			String userName = request.getParameter("userName");
			String  password = request.getParameter("password");

			Neo4jConfigLoader.getApi().userMethods.changeUserPassword(userName, password);

			return "User " + userName + "'s password has been changed";


		}
		if (request.equals("SET_RELATION_TO_COURSE")) {
			String userName = request.getParameter("userName");
			String courseCode = request.getParameter("courseCode");
			String lp = request.getParameter("lp");
			String year = request.getParameter("year");

			User user = Neo4jConfigLoader.getApi().userMethods.getUser(userName);

			LP period = LP.getByString(lp);
			int Year = Integer.parseInt(year);
			CourseDate courseDate = new CourseDate(Year, period);

			Course course = Neo4jConfigLoader.getApi().getMethods.getCourse(courseCode, courseDate);

			Neo4jConfigLoader.getApi().userMethods.addCourseToUser(user, course);

			return "User " + userName + " can now make changes to " + courseCode;

		}
		//Inte färdig än
		if (request.equals("REMOVE_RELATION_TO_COURSE")) {
			String userName = request.getParameter("userName");
			String courseCode = request.getParameter("courseCode");

			return "User " + userName + " can now make changes to " ;
		} else {

			return "The input must be either DELETE, MODIFY, SET_RELATION_TO_COURSE or REMOVE_RELATION_TO_COURSE";
		}

	}

	private String course(HttpServletRequest request) throws IOException {
		if(request.equals("CREATE")) {
			String courseName = request.getParameter("courseName");
			String courseCode = request.getParameter("courseCode");
			String lp = request.getParameter("lp");
			String year = request.getParameter("year");
			String credits = request.getParameter("credits");
			String examiner = request.getParameter("examiner");
			String description = request.getParameter("description");

			LP period = LP.getByString(lp);
			int Year = Integer.parseInt(year);

			CourseDate courseDate = new CourseDate(Year, period);

			Credits Credits = Data.Credits.getByString(credits);

			Course course = new Course(courseName, courseCode, Credits, description, examiner, courseDate);

			Neo4jConfigLoader.getApi().createMethods.createCourse(course);

			return "Course " + courseCode + " created";
		}
		if(request.equals("DELETE")) {
			String courseCode = request.getParameter("courseCode");
			String lp = request.getParameter("lp");
			String year = request.getParameter("year");

			LP period = LP.getByString(lp);
			int Year = Integer.parseInt(year);

			CourseDate courseDate = new CourseDate(Year, period);

			Neo4jConfigLoader.getApi().deleteMethods.deleteCourse(courseCode, courseDate);

			return "Course " + courseCode + " has been deleted.";
		}

		//Lämnar denna tillsvidare
		if(request.equals("MODIFY")) {

		}


	}

	private String kc(HttpServletRequest request) throws IOException {

		if(request.equals("CREATE")) {

			String KCName = request.getParameter("name");
			String generalDescription = request.getParameter("generalDescription");
			String topic = request.getParameter("topic");
			String taxonomyDesc = request.getParameter("taxonomyDesc1");
			String taxonomyDesc2 = request.getParameter("taxonomyDesc2");
			String taxonomyDesc3 = request.getParameter("taxonomyDesc3");

			KC kc_level_1 = new KC(KCName, generalDescription, 1, taxonomyDesc);
			KC kc_level_2 = new KC(KCName, generalDescription, 2, taxonomyDesc2);
			KC kc_level_3 = new KC(KCName, generalDescription, 3, taxonomyDesc3);

			Neo4jConfigLoader.getApi().createMethods.createKC(kc_level_1);
			Neo4jConfigLoader.getApi().createMethods.createKC(kc_level_2);
			Neo4jConfigLoader.getApi().createMethods.createKC(kc_level_3);

			Neo4jConfigLoader.getApi().createMethods.addTopic(topic);

			Neo4jConfigLoader.getApi().createMethods.createToKCRelation(kc_level_1, topic);

			return "KC named " + KCName + " has been created";

		}
		if(request.equals("DELETE")) {

			String KCName = request.getParameter("name");
			String taxonomyLevel = request.getParameter("taxonomyLevel");

			int TaxonomyLevel = Integer.parseInt(taxonomyLevel);

			Neo4jConfigLoader.getApi().deleteMethods.deleteKC(KCName, TaxonomyLevel);

			return "KC with TaxonomyLevel " + taxonomyLevel + " has been removed.";

		}
		if(request.equals("MODIFY_TAXONOMY_DESC")) {

			String KCName = request.getParameter("name");
			String taxonomyLevel = request.getParameter("taxonomyLevel");
			String newTaxonomyDescription = request.getParameter("newTaxonomyDescription");

			int TaxonomyLevel = Integer.parseInt(taxonomyLevel);

			KC kc = Neo4jConfigLoader.getApi().getMethods.getKCwithTaxonomyLevel(KCName, TaxonomyLevel);

			kc.setTaxonomyDescription(newTaxonomyDescription);

			Neo4jConfigLoader.getApi().modifyMethods.editKCTaxonomyDescription(kc);

			return "Taxonomy description of " + KCName + "with TaxonomyLevel " + taxonomyLevel + " has been updated.";

		}
		if(request.equals("MODIFY_GENERAL_DESC")) {

			String KCName = request.getParameter("name");
			String taxonomyDesc = request.getParameter("taxonomyDesc1");
			String taxonomyDesc2 = request.getParameter("taxonomyDesc2");
			String taxonomyDesc3 = request.getParameter("taxonomyDesc3");



			KC kc_level_1 = new KC(KCName, generalDescription, 1, taxonomyDesc);
			KC kc_level_2 = new KC(KCName, generalDescription, 2, taxonomyDesc2);
			KC kc_level_3 = new KC(KCName, generalDescription, 3, taxonomyDesc3);




			KC kc =
		}


	}

	private void program(HttpServletRequest request) {

	}





}


