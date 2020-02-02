package neo4j_JVM_API;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course.CourseLabels;
import Data.*;
import neoCommunicator.Neo4jCommunicator;

public class FilterMethods {
	private final Neo4jCommunicator communicator;
	
	public FilterMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	/**
	 * Generalized search function for courses. This should be the only search function for 
	 * courses we need.
	 * @param filter - This can be any value of the type {@link Course.CourseLabels}.
	 * @param searchTerm - This is the actual search term for the filter. All search results will contain this string.
	 * @return An array containing the search results.
	 * @author Robin
	 */
	public CourseInformation[] filterCourseByTag(Course.CourseLabels filter, String searchTerm) {

		String query = "MATCH (course: " + Course.course +") WHERE ";
		query += "course." + filter + " CONTAINS \"" + searchTerm + "\" ";
		
		/* This gives us the full list of records returned from neo. */
		List<Record> resultList = this.communicator.readFromNeo(query).list();
		
		/* Iterate through the entire result list and create all the courses. */
		CourseInformation[] result = new CourseInformation[resultList.size()];
		int i = 0;
		for (Record row : resultList) {
			CourseInformation information = new CourseInformation(row.get(Course.CourseLabels.NAME.toString()).toString(), 
					row.get(Course.CourseLabels.NAME.toString()).toString(), 
					Credits.valueOf(row.get(Course.CourseLabels.CREDIT.toString()).toString()), 
					row.get(Course.CourseLabels.DESCRIPTION.toString()).toString(),
					row.get(Course.CourseLabels.EXAMINER.toString()).toString(), 
					new CourseDate(Integer.parseInt(row.get(Course.CourseLabels.YEAR.toString()).toString()), LP.valueOf(row.get(Course.CourseLabels.LP.toString()).toString())));
			result[i++] = information;
		}
		
		return result;
	}
	
	/**
	 * Generalized search function for programs (not specializations, yet). This should be the only search function for
	 * programs we need.
	 * @param filter - This can be any value of the type {@link CourseProgram.ProgramLabels}
	 * @param searchTerm - This is the actual search term for the filter. All search results will contain this string.
	 * @return An array containing all the search results.
	 */
	public ProgramInformation[] filterProgramByTag(CourseProgram.ProgramLabels filter, String searchTerm) {
		String query = "MATCH (program: " + CourseProgram.ProgramType.PROGRAM +") WHERE ";
		query += "program." + filter + " CONTAINS \"" + searchTerm + "\" ";
		
		/* This gives us the full list of records returned from neo. */
		List<Record> resultList = this.communicator.readFromNeo(query).list();
		ProgramInformation[] result = new ProgramInformation[resultList.size()];
		int i = 0;
		for (Record row : resultList) {
			ProgramInformation information = new ProgramInformation(row.get(CourseProgram.ProgramLabels.CODE.toString()).toString(), 
					row.get(CourseProgram.ProgramLabels.NAME.toString()).toString(), 
					row.get(CourseProgram.ProgramLabels.DESCRIPTION.toString()).toString(), 
					new CourseDate(Integer.parseInt(row.get(CourseProgram.ProgramLabels.YEAR.toString()).toString()), LP.valueOf(row.get(CourseProgram.ProgramLabels.LP.toString()).toString())), 
					Credits.valueOf(row.get(CourseProgram.ProgramLabels.CREDITS.toString()).toString()), 
					CourseProgram.ProgramType.PROGRAM);
			result[i] = information;
		}
		
		return result;
	}
	
	/**
	 * Filter course by code
	 * 
	 * @param code
	 * @return array with matching course code
	 */
	public String[] filterCourseByCode(String code) {
		String query = "MATCH (course: Course) WHERE course.courseCode STARTS WITH \"" + code + "\" " ;
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
		
		System.out.println(query);
		
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
		String query = "MATCH (topic: "+ Topic.TopicLabels.TOPIC.toString() +") WHERE topic."+ Topic.TopicLabels.TITLE.toString() + " STARTS WITH \"" + name + "\" ";
		query += "RETURN topic";
		
		System.out.println(query);
		
		StatementResult result = this.communicator.readFromNeo(query);
		ArrayList<String> topics = new ArrayList<String>();
		while(result.hasNext()) {
			topics.add(result.next().get("topic").get("name").toString());
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
