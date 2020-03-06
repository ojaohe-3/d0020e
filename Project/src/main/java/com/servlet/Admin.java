package com.servlet;

import Data.*;
import neo4j_JVM_API.GetMethods;
import neo4j_JVM_API.Neo4JAPI;
import neoCommunicator.Neo4jConfigLoader;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





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
	 * checks that the admin is logged in.
	 * If logged in return the Admin page, else return the index.jsp
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try { 
			if((boolean)request.getSession().getAttribute("is_admin") == true) {
				request.getRequestDispatcher("/admin.jsp").forward(request, response);
			} else {

				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			
		} catch(NullPointerException e) {
			
		}
		
		
	}

	/**
	 * 	First it checks that the Admin is logged in, then
	 * Depending on the request input different methods are used.
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		try { 
			if((boolean)request.getSession().getAttribute("is_admin") == true) {
				String head = request.getParameter("head");
		
				String resp = "Error, Invalid Request";
				System.out.println(head);
				
				if (head.equals("USER")) {
					resp = user(request);
				}
				if (head.equals("COURSE")) {
					resp = course(request);
				}
				if (head.equals("KC")) {
					resp = kc(request);
				}
				if (head.equals("PROGRAM")) {
					resp = program(request);
				}
				response.setContentType("text/text");
				response.getWriter().write(resp);
			}
		} catch(NullPointerException e) {}
	}

	/**
	 * Takes in inputs from admin.js and handle all User methods
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String user(HttpServletRequest request) throws IOException {

		String method = request.getParameter("method");
		
		if (method.equals("CREATE")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");


			User u = new User(username, password);
			u.hashPassword();
			Neo4jConfigLoader.getApi().userMethods.addUser(u);

			return "User " + username + " created";
		}

		if (method.equals("DELETE")) {
			String username = request.getParameter("username");

			Neo4jConfigLoader.getApi().userMethods.removeUser(username);

			return "User " + username + " removed";

		}

		if (method.equals("MODIFY_PASSWORD")) {
			String username = request.getParameter("username");
			String  password = request.getParameter("newpassword");
			
			System.out.println(password);	
			Neo4jConfigLoader.getApi().userMethods.changeUserPassword(username, password);

			return "User " + username + "'s password has been changed";

		}

		if (method.equals("MODIFY_USERNAME")) {
			String username = request.getParameter("username");
			String newusername = request.getParameter("newusername");

			Neo4jConfigLoader.getApi().userMethods.changeUsername(username, newusername);

			return "User with username " + username + " has been changed to " + newusername;

		}

		if (method.equals("SET_RELATION_TO_COURSE")) {
			String username = request.getParameter("username");
			String courseCode = request.getParameter("courseCode");
			String lp = request.getParameter("lp");
			String year = request.getParameter("year");
			
			LP period = LP.getByString(lp);
			int Year = Integer.parseInt(year);
			CourseDate courseDate = new CourseDate(Year, period);

			Course course = Neo4jConfigLoader.getApi().getMethods.getCourse(courseCode, courseDate);

			User user = new User(username, null);
			Neo4jConfigLoader.getApi().userMethods.addCourseToUser(user, course);

			return "User " + username + " can now make changes to " + courseCode;

		}

		if (method.equals("DELETE_RELATION_TO_COURSE")) {
			String username = request.getParameter("username");
			String courseCode = request.getParameter("courseCode");
			String lp = request.getParameter("lp");
			String year = request.getParameter("year");

			LP period = LP.getByString(lp);
			int Year = Integer.parseInt(year);

			CourseDate courseDate = new CourseDate(Year, period);

			Neo4jConfigLoader.getApi().userMethods.deleteRelationShipBetweenUserAndCourse(username,courseCode, courseDate);

			return "User " + username + " can no longer make changes to " +courseCode;
		} else {

			return "The input must be either CREATE, DELETE, MODIFY, SET_RELATION_TO_COURSE or DELETE_RELATION_TO_COURSE";
		}
	}

	/**
	 * Takes in inputs from admin.js and handle all methods related to courses
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String course(HttpServletRequest request) throws IOException {
		
		String method = request.getParameter("method");
		
		if(method.equals("CREATE")) {
			String courseName = request.getParameter("courseName");
			String courseCode = request.getParameter("courseCode");
			String lp = request.getParameter("lp");
			String year = request.getParameter("year");
			float credits = Float.parseFloat(request.getParameter("credits"));
			String examiner = request.getParameter("examiner");
			String description = request.getParameter("description");
			String topic = request.getParameter("topic");

			LP period = LP.getByString(lp);
			int Year = Integer.parseInt(year);

			CourseDate courseDate = new CourseDate(Year, period);

			Course course = new Course(courseName, courseCode, credits, description, examiner, courseDate);

			Neo4jConfigLoader.getApi().createMethods.createCourse(course);
			// Adds topic
			Neo4jConfigLoader.getApi().createMethods.addTopic(topic);
			Neo4jConfigLoader.getApi().createMethods.createTopicToCourseRelation(course, topic);

			return "Course " + courseCode + " created";
		}

		if(method.equals("DELETE")) {
			String courseCode = request.getParameter("courseCode");
			String lp = request.getParameter("lp");
			String year = request.getParameter("year");

			LP period = LP.getByString(lp);
			int Year = Integer.parseInt(year);

			CourseDate courseDate = new CourseDate(Year, period);

			Neo4jConfigLoader.getApi().deleteMethods.deleteCourse(courseCode, courseDate);

			return "Course " + courseCode + " has been deleted.";
		}

		if(method.equals("MODIFY")) {
			String oldCourseCode = request.getParameter("oldCourseCode");
			String oldLP = request.getParameter("oldLP");
			String oldYear = request.getParameter("oldYear");
			String newCourseName = request.getParameter("newCourseName");
			String newCourseCode = request.getParameter("newCourseCode");
			String newLP = request.getParameter("newLP");
			String newYear = request.getParameter("newYear");
			String newExaminer = request.getParameter("newExaminer");

			LP oldPeriod = LP.getByString(oldLP);
			int oldCourseYear = Integer.parseInt(oldYear);
			CourseDate oldCourseDate = new CourseDate(oldCourseYear, oldPeriod);
			LP newPeriod = LP.getByString(newLP);
			int newCourseYear = Integer.parseInt(newYear);
			CourseDate newCourseDate = new CourseDate(newCourseYear, newPeriod);

			Course oldCourse = Neo4jConfigLoader.getApi().getMethods.getCourse(oldCourseCode, oldCourseDate);
			float copyOfOldCredits = oldCourse.getCredit();
			String copyOfDescription = oldCourse.getDescription();

			Course newCourse = new Course(newCourseName, newCourseCode, copyOfOldCredits, copyOfDescription, newExaminer, newCourseDate);

			Neo4jConfigLoader.getApi().modifyMethods.editCourse(oldCourseCode, oldCourseDate, newCourse);

			return "The course with coursecode " + oldCourseCode + " has been modified";

		}
		if(method.equals("ADD_TOPIC")) {
			
			String code = request.getParameter("courseCode");
			LP lp = LP.getByString(request.getParameter("lp"));
			int year = Integer.parseInt(request.getParameter("year"));
			
			String topic = request.getParameter("topic");
			
			Course c = new Course("", code, 0, "", "", new CourseDate(year, lp));
			
			Neo4jConfigLoader.getApi().createMethods.addTopic(topic);
			Neo4jConfigLoader.getApi().createMethods.createTopicToCourseRelation(c, topic);
			
			return "Added topic to course " + code;

		}
		if(method.equals("DELETE_TOPIC")) {
			
			String code = request.getParameter("courseCode");
			LP lp = LP.getByString(request.getParameter("lp"));
			int year = Integer.parseInt(request.getParameter("year"));
			
			String topic = request.getParameter("topic");

			Neo4jConfigLoader.getApi().deleteMethods.deleteRelationCourseToTopic(code, new CourseDate(year,lp), topic);
			
			return "Removed topic from course " + code;

		} else {
				return "The input must be either CREATE, DELETE or MODIFY";
		}

	}

	/**
	 * Takes in inputs from admin.js and handle all methods that have something with KC
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String kc(HttpServletRequest request) throws IOException {

		String method = request.getParameter("method");
		
		if(method.equals("CREATE")) {

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

			Neo4jConfigLoader.getApi().createMethods.createTopicToKCRelation(kc_level_1, topic);
			Neo4jConfigLoader.getApi().createMethods.createTopicToKCRelation(kc_level_2, topic);
			Neo4jConfigLoader.getApi().createMethods.createTopicToKCRelation(kc_level_3, topic);

			return "KC named " + KCName + " has been created";

		}
		if(method.equals("DELETE")) {

			String KCName = request.getParameter("name");
			String taxonomyLevel = request.getParameter("taxonomyLevel");

			int TaxonomyLevel = Integer.parseInt(taxonomyLevel);

			Neo4jConfigLoader.getApi().deleteMethods.deleteKC(KCName, TaxonomyLevel);

			return "KC with TaxonomyLevel " + taxonomyLevel + " has been removed.";

		}
		if(method.equals("MODIFY_TAXONOMY_DESC")) {

			String KCName = request.getParameter("name");
			String taxonomyLevel = request.getParameter("taxonomyLevel");
			String newTaxonomyDescription = request.getParameter("newtaxonomyDesc");
			
			int TaxonomyLevel = Integer.parseInt(taxonomyLevel);

			
			KC kc = Neo4jConfigLoader.getApi().getMethods.getKCwithTaxonomyLevel(KCName, TaxonomyLevel);
				
			kc.setTaxonomyDescription(newTaxonomyDescription);

			Neo4jConfigLoader.getApi().modifyMethods.editKCTaxonomyDescription(kc);

			return "Taxonomy description of " + KCName + "with TaxonomyLevel " + taxonomyLevel + " has been updated.";

		}
		if(method.equals("MODIFY_GENERAL_DESC")) {

			String KCName = request.getParameter("name");
			String generalDesc = request.getParameter("generalDescription");

			KC kcTaxonomy1 = Neo4jConfigLoader.getApi().getMethods.getKCwithTaxonomyLevel(KCName, 1);
			KC kcTaxonomy2 = Neo4jConfigLoader.getApi().getMethods.getKCwithTaxonomyLevel(KCName, 2);
			KC kcTaxonomy3 = Neo4jConfigLoader.getApi().getMethods.getKCwithTaxonomyLevel(KCName, 3);

			kcTaxonomy1.setGeneralDescription(generalDesc);
			kcTaxonomy2.setGeneralDescription(generalDesc);
			kcTaxonomy3.setGeneralDescription(generalDesc);

			Neo4jConfigLoader.getApi().modifyMethods.editKCDescription(kcTaxonomy1);
			Neo4jConfigLoader.getApi().modifyMethods.editKCDescription(kcTaxonomy2);
			Neo4jConfigLoader.getApi().modifyMethods.editKCDescription(kcTaxonomy3);

			return "General description for " + KCName + " has been changed";
		}

		return "The input must be either CREATE, DELETE, MODIFY, MODIFY_TAXONOMY_DESC or MODIFY_GENERAL_DESC";
	}

	/**
	 * Takes in inputs from admin.js and handle all the program methods
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String program(HttpServletRequest request) throws IOException {

		String method = request.getParameter("method");
		
		if(method.equals("CREATE")) {
			String programName = request.getParameter("name");
			String programCode = request.getParameter("code");
			String startYear = request.getParameter("startYear");
			String startLP = request.getParameter("startLP");
			String description = request.getParameter("description");
			String credits = request.getParameter("credits");
			int readingPeriods = Integer.parseInt(request.getParameter("readingPeriods"));

			LP period = LP.getByString(startLP);
			int Year = Integer.parseInt(startYear);
			float hp = Float.parseFloat(credits);

			CourseDate courseDate = new CourseDate(Year, period);
			CourseProgram program = new CourseProgram(programCode, programName, description, courseDate, hp);
			program.setCourseOrder(new ArrayList<Course>());
				
			
			Neo4jConfigLoader.getApi().createMethods.createProgram(program);

			return "The program named " + programName + " has been created";

		}
		if(method.equals("DELETE")) {

			String programCode = request.getParameter("code");
			String year = request.getParameter("year");
			String lp = request.getParameter("lp");

			LP period = LP.getByString(lp);
			int Year = Integer.parseInt(year);

			CourseDate courseDate = new CourseDate(Year, period);

			Neo4jConfigLoader.getApi().deleteMethods.deleteProgram(programCode, courseDate);

			return "The program with program code " + programCode + " has been removed";
		}

		if(method.equals("DELETE_SPECIAL")) {
			String name = request.getParameter("name");
			int year = Integer.parseInt(request.getParameter("year"));
			LP period = LP.getByString(request.getParameter("lp"));

			CourseDate courseDate = new CourseDate(year, period);

			Neo4jConfigLoader.getApi().deleteMethods.deleteProgramSpecialization(name, courseDate);

			return "The specialization " + name + " has been deleted";
		}

		if(method.equals("COPY_FROM_YEAR")) {
			String programCode = request.getParameter("code");
			String fromYear = request.getParameter("fromYear");
			String fromLP = request.getParameter("fromLP");
			String toYear = request.getParameter("toYear");

			LP fromPeriod = LP.getByString(fromLP);
			int previousYear = Integer.parseInt(fromYear);
			int nextYear = Integer.parseInt(toYear);

			CourseDate earlierProgramDate = new CourseDate(previousYear, fromPeriod);

			CourseProgram oldProgram = Neo4jConfigLoader.getApi().getMethods.getProgram(programCode, earlierProgramDate);

			Neo4jConfigLoader.getApi().createMethods.createCopyOfProgrambyYear(oldProgram, nextYear);

			return "A new program with program code " + programCode + " has been created for year "+ toYear;

		}

		if(method.equals("COPY_FROM_YEAR_SPECIAL")) {
			String name = request.getParameter("name");
			String code = request.getParameter("code");
			int fromYear = Integer.parseInt(request.getParameter("fromYear"));
			LP fromLP = LP.getByString(request.getParameter("fromLP"));
			int toYear = Integer.parseInt(request.getParameter("toYear"));


			CourseDate courseDate = new CourseDate(fromYear, fromLP);
			CourseDate dateForRelations = new CourseDate(toYear, fromLP);

			ProgramSpecialization oldSpecialization = Neo4jConfigLoader.getApi().getMethods.getProgramSpecialization(name, code, courseDate);

			Neo4jConfigLoader.getApi().createMethods.createCopyOfSpecializationbyYear(oldSpecialization, toYear, code, dateForRelations);

			return "The specialization named " + name + " has been copied from " + fromYear + " to " + toYear;
		}

		if(method.equals("MODIFY")) {
			String oldProgramCode = request.getParameter("oldCode");
			String programStartYearYear = request.getParameter("programStartYear");
			String programStartLP = request.getParameter("programStartLP");
			String newName = request.getParameter("newName");
			String newCode = request.getParameter("newCode");
			String newStartYear = request.getParameter("newStartYear");
			String newStartLP = request.getParameter("newStartLP");
			String newDescription = request.getParameter("newDescription");
			String newCredits = request.getParameter("newCredits");
			int readingPeriods = Integer.parseInt(request.getParameter("readingPeriods"));

			LP fromPeriod = LP.getByString(programStartLP);
			int previousYear = Integer.parseInt(programStartYearYear);
			int nextYear = Integer.parseInt(newStartYear);
			LP newStartPeriod = LP.getByString(newStartLP);

			CourseDate earlierProgramDate = new CourseDate(previousYear, fromPeriod);
			CourseDate newProgramDate = new CourseDate(nextYear, newStartPeriod);
			ArrayList<Course> courseOrder = new ArrayList<>();
			
			float newCreditPoints = Float.parseFloat(newCredits);

			CourseProgram updatedProgram = new CourseProgram(courseOrder, newCode, newName,newDescription, newProgramDate, newCreditPoints, CourseProgram.ProgramType.PROGRAM);

			Neo4jConfigLoader.getApi().modifyMethods.editProgram(oldProgramCode, earlierProgramDate, updatedProgram);

			return "The program known as " + oldProgramCode + " has been updated";

		}
		if(method.equals("ADD_COURSE")) {
			String programCode = request.getParameter("programCode");
			String programStartYear = request.getParameter("programStartYear");
			String programStartLP = request.getParameter("programStartLP");
			String courseCode = request.getParameter("courseCode");
			String courseYear = request.getParameter("courseYear");
			String courseLP = request.getParameter("courseLP");

			int programYear = Integer.parseInt(programStartYear);
			LP programStartPeriod = LP.getByString(programStartLP);
			int course_Year = Integer.parseInt(courseYear);
			LP coursePeriod = LP.getByString(courseLP);

			CourseDate programStartDate = new CourseDate(programYear, programStartPeriod);
			CourseDate courseDate = new CourseDate(course_Year, coursePeriod);

			CourseProgram courseProgram = Neo4jConfigLoader.getApi().getMethods.getProgram(programCode, programStartDate);
			Course course = Neo4jConfigLoader.getApi().getMethods.getCourse(courseCode, courseDate);

			//CourseInformation courseInformation = new CourseInformation(course.getName(), course.getCourseCode(), course.getCredit(), course.getDescription(), course.getExaminer(), course.getStartPeriod());
			//ProgramInformation programInformation = new ProgramInformation(courseProgram.getCode(), courseProgram.getName(), courseProgram.getDescription(), courseProgram.getStartDate(), courseProgram.getCredits(), courseProgram.getProgramType());

			courseProgram.setCode(programCode);
			//CourseProgram courseProgram = new CourseProgram(null, programCode, null, null, new CourseDate(programYear, programStartPeriod) ,null );
			Neo4jConfigLoader.getApi().createMethods.createProgramCourseRelation(courseProgram, course);

			return "The relationship between " + programCode + " and " + courseCode + " has been modified";

		} 
		if(method.equals("REMOVE_COURSE")) {
			String programCode = request.getParameter("programCode");
			String programStartYear = request.getParameter("programStartYear");
			String programStartLP = request.getParameter("programStartLP");
			String courseCode = request.getParameter("courseCode");
			String courseYear = request.getParameter("courseYear");
			String courseLP = request.getParameter("courseLP");

			int programYear = Integer.parseInt(programStartYear);
			LP programStartPeriod = LP.getByString(programStartLP);
			int course_Year = Integer.parseInt(courseYear);
			LP coursePeriod = LP.getByString(courseLP);

			CourseDate courseStartDate = new CourseDate(course_Year, coursePeriod);
			CourseDate programStartDate = new CourseDate(programYear, programStartPeriod);
			
			Neo4jConfigLoader.getApi().deleteMethods.deleteRelationCourseInProgram(courseCode, courseStartDate, programCode, programStartDate, CourseProgram.ProgramType.PROGRAM);
		
			return "The relationship between " + programCode + " and " + courseCode + " has been deleted";
		}
		if(method.equals("CREATE_SPECIAL")) {
			String name = request.getParameter("name");
			String programCode = request.getParameter("programCode");
			int programYear = Integer.parseInt(request.getParameter("startProgramYear"));
			String startProgramLP = request.getParameter("startProgramLP");
			String specYear = request.getParameter("specYear");
			String specLP = request.getParameter("specLP");
			String description = request.getParameter("description");
			String credits = request.getParameter("credits");

			LP programStartPeriod = LP.getByString(startProgramLP);
			int specialYear = Integer.parseInt(specYear);
			LP specialPeriod = LP.getByString(specLP);
			CourseDate programDate = new CourseDate(programYear, programStartPeriod);
			CourseDate specDate = new CourseDate(specialYear, specialPeriod);
			float currentCredits = Float.parseFloat(credits);

			int readingPeriods = Integer.parseInt(request.getParameter("readingPeriods"));
			ProgramSpecialization programSpecialization = new ProgramSpecialization(null, programCode, name, description, specDate, currentCredits);
			programSpecialization.setCourseOrder(new ArrayList<Course>());
			
			Neo4jConfigLoader.getApi().createMethods.createProgram(programSpecialization);

			//ProgramSpecialization programSpecialization1 = Neo4jConfigLoader.getApi().getMethods.getProgramSpecialization(name, specDate,programCode);

			Neo4jConfigLoader.getApi().createMethods.createProgramSpecializationRelation(programCode, programDate, programSpecialization);

			return "A relationship between the program " + programCode + " and the specialization " + name + " has been created";

		}
		if (method.equals("MODIFY_SPECIAL")) {
			String oldName = request.getParameter("oldName");
			int programStartYear = Integer.parseInt(request.getParameter("programStartYear"));
			LP programStartLP = LP.getByString(request.getParameter("programStartByString"));
			String newName = request.getParameter("newName");
			String newCode = request.getParameter("newCode");
			int newStartYear = Integer.parseInt(request.getParameter("newStartYear"));
			LP newStartLP = LP.getByString(request.getParameter("newStartByString"));
			String newDescription = request.getParameter("newDescription");
			float newCredits = Float.parseFloat(request.getParameter("newCredits"));
			int readingPeriods = Integer.parseInt(request.getParameter("readingPeriods"));
			ArrayList<Course> courseOrder= new ArrayList<Course>();

			CourseDate programDate = new CourseDate(programStartYear, programStartLP);
			CourseDate specializationDate = new CourseDate(newStartYear, newStartLP);

			ProgramSpecialization programSpecialization = new ProgramSpecialization(courseOrder, newCode, newName,newDescription, specializationDate, newCredits);

			Neo4jConfigLoader.getApi().modifyMethods.editSpecialization(oldName, programDate, programSpecialization);

			return "The progrmspecialization named " + oldName + " has been modified. New name is " + newName;

		}else {
			return "The input must be either CREATE, DELETE, DELETE_SPECIAL, COPY_FROM_YEAR, MODIFY, MODIFY_SPECIAL, SET_RELATION_TO_COURSE, ADD_COURSE or CREATE_SPECIAL";

		}
	}





}


