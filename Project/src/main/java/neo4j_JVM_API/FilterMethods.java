package neo4j_JVM_API;

import java.util.ArrayList;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course.CourseLabels;
import neoCommunicator.Neo4jCommunicator;

public class FilterMethods {
	private final Neo4jCommunicator communicator;
	
	public FilterMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	/**
	 * Filter course by code
	 * 
	 * @param code
	 * @return array with matching course code
	 */
	public String[] filterCourseByCode(String code) {
		String query = "MATCH (course: Course) WHERE course.courseCode STARTS WITH \"" + code + "\"";
		query += "RETURN course";
		
		StatementResult result = this.communicator.readFromNeo(query);
		ArrayList<String> courses = new ArrayList<String>();
		while(result.hasNext()) {
			courses.add(result.next().get("course").get("courseCode").toString());
		}
		
		return courses.toArray(new String[courses.size()]);
	}
	
	/**
	 * Filter course by name
	 * 
	 * @param code
	 * @return array with matching course name
	 */
	public String[] filterCourseByName(String name) {
		String query = "MATCH (course: Course) WHERE course.name STARTS WITH \"" + name + "\"";
		query += "RETURN course";
		
		StatementResult result = this.communicator.readFromNeo(query);
		ArrayList<String> courses = new ArrayList<String>();
		while(result.hasNext()) {
			courses.add(result.next().get("course").get("name").toString());
		}
		
		return courses.toArray(new String[courses.size()]);
	}
	
	/**
	 * Filter course program by code
	 * 
	 * @param code
	 * @return array with matching program
	 */
	public String[] filterProgramByCode(String code) {
		String query = "MATCH (courseProgram: CourseProgram) WHERE courseProgram.code STARTS WITH \"" + code + "\"";
		query += "RETURN courseProgram";
		
		StatementResult result = this.communicator.readFromNeo(query);
		ArrayList<String> programs = new ArrayList<String>();
		while(result.hasNext()) {
			programs.add(result.next().get("courseProgram").get("code").toString());
		}
		
		return programs.toArray(new String[programs.size()]);
	}
	
	/**
	 * Filter course program by name
	 * 
	 * @param code
	 * @return array with matching program
	 */
	public String[] filterProgramByName(String name) {
		String query = "MATCH (courseProgram: CourseProgram) WHERE courseProgram.name STARTS WITH \"" + name + "\"";
		query += "RETURN courseProgram";
		
		StatementResult result = this.communicator.readFromNeo(query);
		ArrayList<String> programs = new ArrayList<String>();
		while(result.hasNext()) {
			programs.add(result.next().get("courseProgram").get("name").toString());
		}
		
		return programs.toArray(new String[programs.size()]);
	}
	
	/**
	 * Filter topic
	 * 
	 * @param code
	 * @return array with matching topics
	 */
	public String[] filterTopic(String name) {
		String query = "MATCH (topic: Topic) WHERE topic.name STARTS WITH \"" + name + "\"";
		query += "RETURN topic";
		
		StatementResult result = this.communicator.readFromNeo(query);
		ArrayList<String> topics = new ArrayList<String>();
		while(result.hasNext()) {
			topics.add(result.next().get("topics").get("name").toString());
		}
		
		return topics.toArray(new String[topics.size()]);
	}
	
	/**
	 * Filter kc
	 * 
	 * @param code
	 * @return array with matching kcs
	 */
	public void filterKC() {
		
	}

	/**
	 * Get names of courses that has a specific topic
	 * @param topicTitle
	 * @return String[] of available course names
	 */
	public String[] getCourseNameByTopic(String topicTitle) {
		String query = "MATCH(node: Topic {title : \""+ topicTitle +"\"})<-[r]-(course) RETURN course ";
		
		StatementResult result = this.communicator.readFromNeo(query);
		ArrayList<String> courseNames = new ArrayList<String>();
		
		while(result.hasNext()) {
			Record row = result.next();
			courseNames.add(row.get("course").get("name").asString());
		}
		return courseNames.toArray(new String[courseNames.size()]);
		
	}
}
