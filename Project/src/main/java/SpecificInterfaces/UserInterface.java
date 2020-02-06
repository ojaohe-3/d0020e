package SpecificInterfaces;
import Data.*;

import neo4j_JVM_API.Neo4JAPI;
import neoCommunicator.Neo4jCommunicator;
import neoCommunicator.Neo4jConfigLoader;

import java.io.IOException;

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
	 */
	public UserInterface() {
		try {
			this.neoapi = Neo4jConfigLoader.getApi();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * Get all available♦♣ topics
	 * @return
	 */
	protected String[] getTopics() {
		return this.neoapi.getMethods.getTopics();
	}

	/**
	 * get information of course nodes by search question
	 * @param tag attribute selected
	 * @param searchTerm search key
	 * @return
	 */
	protected CourseInformation[] getCourseByTag(Course.CourseLabels tag, String searchTerm){
		return this.neoapi.filterMethods.filterCourseByTag(tag,searchTerm);
	}

	/**
	 * Search for programs by attribute tag and search key returning all related tags
	 * @param tag search on what attribute
	 * @param searchKey Search question
	 * @return
	 */
	protected ProgramInformation[] searchProgram(CourseProgram.ProgramLabels tag, String searchKey){
		return neoapi.filterMethods.filterProgramByTag(tag,searchKey);
	}

	/**
	 * Find through topic All related KCs
	 * @param topic Exact topic name
	 * @return
	 */
	protected KC[] searchKcTopic(String topic){
		return neoapi.filterMethods.filterKCByTopic(topic);
	}
	/**
	 * Search KC
	 * @param tag the attribute to search on
	 * @param searchKey search key
	 * @return
	 */
	protected KC[] searchKC(KC.KCLabel tag, String searchKey){
		return neoapi.filterMethods.filterKCByTag(tag,searchKey);
	}
}
