package SpecificInterfaces;

import Data.Course;
import Data.CourseDate;
import Data.CourseInformation;
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
	 *  Get course names from courses related to a topic
	 * @param topic
	 * @return
	 */
	protected CourseInformation[] getCoursesByTopic(String topic) {
		return this.neoapi.filterMethods.getCourseNameByTopic(topic);
	}
	
	/**
	 * Get all available topics
	 * @return
	 */
	protected String[] getTopics() {
		return this.neoapi.getMethods.getTopics();
	}

	protected CourseInformation[] getCourseByTag(Course.CourseLabels tag, String searchTerm){
		return this.neoapi.filterMethods.filterCourseByTag(tag,searchTerm);
	}

}
