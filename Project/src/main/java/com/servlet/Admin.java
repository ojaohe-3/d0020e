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
			String lp = request.getParameter("lp");
			String year = request.getParameter("year");

			LP period = LP.getByString(lp);
			int Year = Integer.parseInt(year);

			CourseDate courseDate = new CourseDate(Year, period);

			Neo4jConfigLoader.getApi().userMethods.deleteRelationShipBetweenUserAndCourse(courseCode, userName, courseDate);

			return "User " + userName + " can no longer make changes to " +courseCode;
		} else {

			return "The input must be either CREATE, DELETE, MODIFY, SET_RELATION_TO_COURSE or REMOVE_RELATION_TO_COURSE";
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

		if(request.equals("MODIFY")) {
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
			Credits copyOfOldCredits = oldCourse.getCredit();
			String copyOfDescription = oldCourse.getDescription();

			Course newCourse = new Course(newCourseName, newCourseCode, copyOfOldCredits, copyOfDescription, newExaminer, newCourseDate);

			Neo4jConfigLoader.getApi().modifyMethods.editCourse(oldCourseCode, oldCourseDate, newCourse);

			return "The course with coursecode " + oldCourseCode + " has been modified";

		} else {
				return "The input must be either CREATE, DELETE or MODIFY";
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

			Neo4jConfigLoader.getApi().createMethods.createTopicToKCRelation(kc_level_1, topic);
			Neo4jConfigLoader.getApi().createMethods.createTopicToKCRelation(kc_level_2, topic);
			Neo4jConfigLoader.getApi().createMethods.createTopicToKCRelation(kc_level_3, topic);

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

	private String program(HttpServletRequest request) throws IOException {

		if(request.equals("CREATE")) {
			String programName = request.getParameter("name");
			String programCode = request.getParameter("code");
			String startYear = request.getParameter("startYear");
			String startLP = request.getParameter("startLP");
			String description = request.getParameter("description");
			String credits = request.getParameter("credits");

			LP period = LP.getByString(startLP);
			int Year = Integer.parseInt(startYear);
			Credits hp = Credits.getByString(credits);

			CourseDate courseDate = new CourseDate(Year, period);
			CourseProgram program = new CourseProgram(programCode, programName, description, courseDate, hp);

			Neo4jConfigLoader.getApi().createMethods.createProgram(program);

			return "The program named " + programName + " has been created";

		}
		if(request.equals("DELETE")) {

			String programCode = request.getParameter("code");
			String year = request.getParameter("year");
			String lp = request.getParameter("lp");

			LP period = LP.getByString(lp);
			int Year = Integer.parseInt(year);

			CourseDate courseDate = new CourseDate(Year, period);

			Neo4jConfigLoader.getApi().deleteMethods.deleteProgram(programCode, courseDate);

			return "The program with program code " + programCode + " has been removed";
		}

		if(request.equals("COPY_FROM_YEAR")) {
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

		if(request.equals("MODIFY")) {
			String oldProgramCode = request.getParameter("oldCode");
			String programStartYearYear = request.getParameter("programStartYear");
			String programStartLP = request.getParameter("programStartLP");
			String newName = request.getParameter("newName");
			String newCode = request.getParameter("newCode");
			String newStartYear = request.getParameter("newStartYear");
			String newStartLP = request.getParameter("newStartLP");
			String newDescription = request.getParameter("newDescription");
			String newCredits = request.getParameter("newCredits");

			LP fromPeriod = LP.getByString(programStartLP);
			int previousYear = Integer.parseInt(programStartYearYear);
			int nextYear = Integer.parseInt(newStartYear);
			LP newStartPeriod = LP.getByString(newStartLP);

			CourseDate earlierProgramDate = new CourseDate(previousYear, fromPeriod);
			CourseDate newProgramDate = new CourseDate(nextYear, newStartPeriod);

			Credits newCreditPoints = Credits.getByString(newCredits);

			CourseProgram updatedProgram = new CourseProgram(newCode, newName,newDescription, newProgramDate, newCreditPoints);

			Neo4jConfigLoader.getApi().modifyMethods.editProgram(oldProgramCode, earlierProgramDate, updatedProgram);

			return "The program known as " + oldProgramCode + " has been updated";

		}
		if(request.equals("ADD_COURSE")) {
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

			CourseInformation courseInformation = new CourseInformation(course.getName(), course.getCourseCode(), course.getCredit(), course.getDescription(), course.getExaminer(), course.getStartPeriod());
			ProgramInformation programInformation = new ProgramInformation(courseProgram.getCode(), courseProgram.getName(), courseProgram.getDescription(), courseProgram.getStartDate(), courseProgram.getCredits(), courseProgram.getProgramType());

			Neo4jConfigLoader.getApi().modifyMethods.editInProgramCourseRelation(courseInformation, programInformation);

			return "The relationship between " + programCode + " and " + courseCode + " has been modified";

		}
		if(request.equals("CREATE_SPECIAL")) {
			String name = request.getParameter("name");
			String programCode = request.getParameter("programCode");
			String startProgramYear = request.getParameter("startProgramYear");
			String startProgramLP = request.getParameter("startProgramLP");
			String specYear = request.getParameter("specYear");
			String specLP = request.getParameter("specLP");
			String description = request.getParameter("description");
			String credits = request.getParameter("credits");

			int programYear = Integer.parseInt(startProgramYear);
			LP programStartPeriod = LP.getByString(startProgramLP);
			int specialYear = Integer.parseInt(specYear);
			LP specialPeriod = LP.getByString(specLP);
			CourseDate programDate = new CourseDate(programYear, programStartPeriod);
			CourseDate specDate = new CourseDate(specialYear, specialPeriod);
			Credits currentCredits = Credits.getByString(credits);

			CourseProgram programSpecialization = new CourseProgram(programCode, name, description, specDate, currentCredits);

			Neo4jConfigLoader.getApi().createMethods.createProgram(programSpecialization);

			ProgramSpecialization programSpecialization1 = Neo4jConfigLoader.getApi().getMethods.getProgramSpecialization(name, specDate,programCode);

			Neo4jConfigLoader.getApi().createMethods.createProgramSpecializationRelation(programCode, programDate, programSpecialization1);

			return "A relationship between the program " + programCode + " and the specialization " + name + " has been created";

		} else {
			return "The input must be either CREATE, DELETE, COPY_FROM_YEAR, MODIFY, SET_RELATION_TO_COURSE, ADD_COURSE or CREATE_SPECIAL";

		}
	}





}


