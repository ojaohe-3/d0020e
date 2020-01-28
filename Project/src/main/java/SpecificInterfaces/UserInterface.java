package SpecificInterfaces;

import Data.Course;
import Data.CourseDate;
import Data.CourseProgram;
import neo4j_JVM_API.Neo4JAPI;

/**
 * A class containing all the functionality that a user should be able to access without signing in
 * 
 * 
 * @author Jesper.
 *
 */
public class UserInterface {

	private Neo4JAPI neoapi;
	
	/**
	 * Constructor
	 * @param neoapi
	 */
	public UserInterface(Neo4JAPI neoapi) {
		this.neoapi = neoapi;
	}
	
	/**
	 *  Gets course information using the course code and course date
	 * @param courseCode
	 * @param courseDate
	 * @return
	 */
	public Course getCourseByCode(String courseCode, CourseDate courseDate) {
		return this.neoapi.getMethods.getCourse(courseCode, courseDate);
	}
	
	/**
	 *  Get course names from courses related to a topic
	 * @param topic
	 * @return
	 */
	public String[] getCoursesByTopic(String topic) {
		return this.neoapi.filterMethods.getCourseNameByTopic(topic);
	}
	
	/**
	 * Get all available topics
	 * @return
	 */
	public String[] getTopics() {
		return this.neoapi.getMethods.getTopics();
	}
	
	/**
	 * 	Returns name of available Courses that got a name that starts with the parameter
	 * @param startsWith
	 * @return
	 */
	public String[] getCourseFilterByName(String startsWith) {
		return this.neoapi.filterMethods.filterCourseByName(startsWith);
	}
	
	/**
	 * 	Returns name of available Courses that got a course code that starts with the parameter
	 * @param startWith
	 * @return
	 */
	public String[] getCourseFilterByCode(String startWith) {
		return this.neoapi.filterMethods.filterCourseByCode(startWith);
	}
	
	/**
	 * 	Returns name of programs that got a name that start with the parameter
	 * @param startsWith
	 * @return
	 */
	public String[] getCourseProgramFilterByName(String startsWith) {
		return this.neoapi.filterMethods.filterProgramByName(startsWith);
	}
	
	/**
	 * 	Returns a CourseProgram
	 * @param code Code of the program
	 * @param startDate Start date for the program
	 * @return Program containing a Course order
	 */
	public CourseProgram getCourseProgramByCodeAndStartDate(String code, CourseDate startDate) {
		return this.neoapi.getMethods.getProgram(code, startDate);
	}
	
	
	
}
