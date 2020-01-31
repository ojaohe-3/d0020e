package SpecificInterfaces;

import Data.Course;
import Data.CourseDate;
import neo4j_JVM_API.Neo4JAPI;

/**
 * A class containing all the functionality that a user should be able to access without signing in
 * 
 * 
 * @author Jesper. Johan RH
 *
 */
public abstract class UserInterface {

	protected Neo4JAPI neoapi;
	
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
	protected Course getCourseByCode(String courseCode, CourseDate courseDate) {
		return this.neoapi.getMethods.getCourse(courseCode, courseDate);
	}

	/**
	 * Get all courses with a course code
	 * @author Johan RH
	 * @param courseCode
	 * @return
	 */
	protected String[] getCourseByCode(String courseCode) {
		return this.neoapi.filterMethods.filterCourseByCode(courseCode);
	}
	
	/**
	 *  Get course names from courses related to a topic
	 * @param topic
	 * @return
	 */
	protected String[] getCoursesByTopic(String topic) {
		return this.neoapi.filterMethods.getCourseNameByTopic(topic);
	}
	
	/**
	 * Get all avaliable topics
	 * @return
	 */
	protected String[] getTopics() {
		return this.neoapi.getMethods.getTopics();
	}

	/**
	 * Get all Program Given Name
	 * @author Johan RH
	 * @param programName
	 * @return
	 */
	protected String[] getProgramByName(String programName){
		return this.neoapi.filterMethods.filterProgramByName(programName);
	}
	/**
	 * Get all Program Given Code
	 * @author Johan RH
	 * @param programCode
	 * @return
	 */
	protected String[] getProgramByCode(String programCode){
		return this.neoapi.filterMethods.filterCourseByCode(programCode);
	}


	protected String[]  getCourseByTag(Course.CourseLabels tag, String searchTerm){
		return this.neoapi.filterMethods.getCourseByTag(tag,searchTerm);
	}
}
