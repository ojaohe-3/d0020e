package neo4j_JVM_API;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;

import Data.Course.CourseLabels;
import Data.CourseProgram.ProgramLabels;
import Data.CourseProgram.ProgramType;
import Data.*;
import neoCommunicator.Neo4jCommunicator;

public class FilterMethods {
	private final Neo4jCommunicator communicator;
	
	public FilterMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	/**
	 * 			CREDITS NOT WORKING ..
	 * 
 * 			aND COurseDate. .
	 * 
	 * 
	 * Generalized search function for courses. This should be the only search function for 
	 * courses we need.
	 * @param filter - This can be any value of the type {@link Course.CourseLabels}.
	 * @param searchTerm - This is the actual search term for the filter. All search results will contain this string.
	 * @return An array containing the search results.
	 * @author Robin
	 */
	public CourseInformation[] filterCourseByTag(Course.CourseLabels filter, String searchTerm) {

		String query = "MATCH (course: " + Course.course +") WHERE course." + filter + " CONTAINS \"" + searchTerm + "\" RETURN course";
		
		/* This gives us the full list of records returned from neo. */
		List<Record> resultList = this.communicator.readFromNeo(query).list();
		
		/* Iterate through the entire result list and create all the courses. */
		CourseInformation[] result = new CourseInformation[resultList.size()];
		int i = 0;
		for (Record row : resultList) {
			Value course = row.get("course");
			CourseInformation information = new CourseInformation(course.get(Course.CourseLabels.NAME.toString()).toString().replaceAll("\"",""),
					course.get(Course.CourseLabels.CODE.toString()).toString().replaceAll("\"",""),
					Credits.valueOf(course.get(Course.CourseLabels.CREDIT.toString()).toString().replaceAll("\"", "") ),
					course.get(Course.CourseLabels.DESCRIPTION.toString()).toString().replaceAll("\"",""),
					course.get(Course.CourseLabels.EXAMINER.toString()).toString().replaceAll("\"",""),
					new CourseDate(Integer.parseInt(course.get(Course.CourseLabels.YEAR.toString()).toString().replaceAll("\"", "")   ), LP.valueOf(course.get(Course.CourseLabels.LP.toString()).toString().replaceAll("\"", ""))));
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
		String query = "MATCH (program: " + CourseProgram.ProgramType.PROGRAM +") WHERE program." + filter + " CONTAINS \"" + searchTerm + "\" RETURN program";
		
		/* This gives us the full list of records returned from neo. */
		List<Record> resultList = this.communicator.readFromNeo(query).list();
		ProgramInformation[] result = new ProgramInformation[resultList.size()];
		int i = 0;
		for (Record row : resultList) {
			Value prog = row.get("program");
			ProgramInformation information = new ProgramInformation(prog.get(CourseProgram.ProgramLabels.CODE.toString()).toString().replaceAll("\"", ""),
					prog.get(CourseProgram.ProgramLabels.NAME.toString()).toString().replaceAll("\"", ""),
					prog.get(CourseProgram.ProgramLabels.DESCRIPTION.toString()).toString().replaceAll("\"", ""),
					new CourseDate(
							Integer.parseInt(prog.get(CourseProgram.ProgramLabels.YEAR.toString()).toString().replaceAll("\"", "")),
							LP.valueOf(prog.get(CourseProgram.ProgramLabels.LP.toString()).toString().replaceAll("\"", ""))
					),
					Credits.valueOf(prog.get(CourseProgram.ProgramLabels.CREDITS.toString()).toString().replaceAll("\"", "")), 
					CourseProgram.ProgramType.PROGRAM);
			result[i] = information;
		}
		
		return result;
	}
	
	/**
	 * Generalized search function for topics. This should be the only search function for 
	 * topics we need.
	 * @param searchTerm - This is the actual search term for the filter. All search results will contain this string.
	 * @return - An array containing the title of all search results.
	 */
	public String[] filterTopicByTitle(String searchTerm) {
		String query = "MATCH (topic: " + Topic.TopicLabels.TOPIC +") WHERE topic." + Topic.TopicLabels.TITLE.toString() + " CONTAINS \"" + searchTerm + "\" RETURN topic";
		
		/* This gives us the full list of records returned from neo. */
		List<Record> resultList = this.communicator.readFromNeo(query).list();
		String[] result = new String[resultList.size()];
		int i = 0;
		for (Record row : resultList) {
			
			result[i] = row.get("topic").get(Topic.TopicLabels.TITLE.toString()).toString();
		}
		
		return result;
	}
	
	/** 
	 * Return all topics containing search term
	 * @param searchTerm
	 * @return
	 */
	public Topic[] filterTopicByName(String searchTerm) {
		String query = "MATCH (topic: " + Topic.TopicLabels.TOPIC +") WHERE topic." + Topic.TopicLabels.TITLE.toString() + " CONTAINS \"" + searchTerm + "\" RETURN topic";
		/* This gives us the full list of records returned from neo. */
		List<Record> resultList = this.communicator.readFromNeo(query).list();
		Topic[] result = new Topic[resultList.size()];
		int i = 0;
		for (Record row : resultList) {
			Topic topic = new Topic(row.get("topic").get(Topic.TopicLabels.TITLE.toString()).toString().replaceAll("\"", ""));
			result[i] = topic;
			i++;
		}
		
		return result;
	}
	
	/**
	 * Generalized search function for KCs. This should be the only search function for 
	 * KCs we need.
	 * @param filter - This can be any value of the form {@link KC.KCLabel}
	 * @param searchTerm - This is the actual search term for the filter. All search results will contain this string.
	 * @return - An array containing the title of all search results.
	 */
	public KC[] filterKCByTag(KC.KCLabel filter, String searchTerm) {
		String query = "MATCH (kc: " + KC.kc +") WHERE kc." + filter + " CONTAINS \"" + searchTerm + "\" RETURN kc";
		
		/* This gives us the full list of records returned from neo. */
		List<Record> resultList = this.communicator.readFromNeo(query).list();
		KC[] result = new KC[resultList.size()];
		int i = 0;
		for (Record row : resultList) {
			Value tempKC = row.get("kc");

			KC kc = new KC(tempKC.get(KC.KCLabel.NAME.toString()).toString().replaceAll("\"", ""),
					tempKC.get(KC.KCLabel.GENERAL_DESCRIPTION.toString()).toString().replaceAll("\"", ""),
					Integer.parseInt(tempKC.get(KC.KCLabel.TAXONOMYLEVEL.toString()).toString().replaceAll("\"", "")), 
					tempKC.get(KC.KCLabel.TAXONOMY_DESCRIPTION.toString()).toString().replaceAll("\"", ""));

			result[i] = kc;
			i++;
		}
		
		return result;
	}
	
	/**
	 * Filter course by code
	 * 
	 * @param code
	 * @return array with matching course code
	 */
	@Deprecated
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
	 * @param name
	 * @return array with matching course name
	 */
	@Deprecated
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
	@Deprecated
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
	 * @param name
	 * @return array with matching program
	 */
	@Deprecated
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
	 * @param name
	 * @return array with matching topics
	 */
	@Deprecated
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
	 * Get names of courses that has a specific topic
	 * @param topicTitle
	 * @return String[] of available course names
	 * @author Johan RH
	 */
	public CourseInformation[] filterCourseByTopic(String topicTitle) {
		String query = "MATCH(node: Topic {title : \""+ topicTitle +"\"})<-[r]-(course:Course) RETURN course ";
		
		
		StatementResult result = this.communicator.readFromNeo(query);
		ArrayList<CourseInformation> courseNames = new ArrayList<CourseInformation>();
		
		while(result.hasNext()) {
			Record row = result.next();
			courseNames.add(new CourseInformation(
					row.get("course").get(CourseLabels.NAME.toString()).toString(),
					row.get("course").get(CourseLabels.CODE.toString()).toString(),
					Credits.valueOf(row.get("course").get(CourseLabels.CREDIT.toString()).toString().replaceAll("\"", "")),
					row.get("course").get(CourseLabels.DESCRIPTION.toString()).toString(),
					row.get("course").get(CourseLabels.EXAMINER.toString()).toString(),
					new CourseDate(
							Integer.parseInt(row.get("course").get(CourseLabels.YEAR.toString()).toString().replaceAll("\"", "")),
							LP.valueOf(row.get("course").get(CourseLabels.LP.toString()).toString().replaceAll("\"", ""))
					)
			));
		}
		return courseNames.toArray(new CourseInformation[courseNames.size()]);
		
	}

	/**
	 * Filter program by topic
	 * 
	 * @param topicTitle
	 * @return
	 */
	public ProgramInformation[] filterProgramByTopic(String topicTitle) {
		String query = "MATCH(topic: Topic {title : \""+ topicTitle +"\"})<-[r]-(courseProgram: CourseProgram) RETURN courseProgram ";
		
		StatementResult result = this.communicator.readFromNeo(query);
		ArrayList<ProgramInformation> programs = new ArrayList<ProgramInformation>();
		
		while(result.hasNext()) {
			Record row = result.next();
			programs.add(new ProgramInformation(
					row.get("courseProgram").get(ProgramLabels.NAME.toString()).toString().replaceAll("\"", ""),
					row.get("courseProgram").get(ProgramLabels.CODE.toString()).toString().replaceAll("\"", ""),
					row.get("courseProgram").get(ProgramLabels.DESCRIPTION.toString()).toString().replaceAll("\"", ""),
					new CourseDate(
							Integer.parseInt(row.get("courseProgram").get(ProgramLabels.YEAR.toString()).toString().replaceAll("\"", "")),
							LP.valueOf(row.get("courseProgram").get(ProgramLabels.LP.toString()).toString().replaceAll("\"", ""))),
					Credits.valueOf(row.get("courseProgram").get(ProgramLabels.CREDITS.toString()).toString().replaceAll("\"", "")),
					ProgramType.PROGRAM
			));
		}
		return programs.toArray(new ProgramInformation[programs.size()]);
		
	}
	
	public KC[] filterKCByTopic(String topic) {
		String query = "MATCH(node: Topic {title : \""+ topic +"\"})<-[r]-(kc:"+KC.kc+") RETURN kc ";

		StatementResult result = this.communicator.readFromNeo(query);
		ArrayList<KC> KCs = new ArrayList<KC>();

		while(result.hasNext()) {
			Record row = result.next();
			KCs.add(new KC(
					row.get("kc").get(KC.KCLabel.NAME.toString()).toString().replaceAll("\"", ""),
					row.get("kc").get(KC.KCLabel.GENERAL_DESCRIPTION.toString()).toString().replaceAll("\"", ""),
					Integer.parseInt(row.get("kc").get(KC.KCLabel.TAXONOMYLEVEL.toString()).toString().replaceAll("\"", "")),
					row.get("kc").get(KC.KCLabel.TAXONOMY_DESCRIPTION.toString()).toString().replaceAll("\"", "")
					));
		}
		return KCs.toArray(new KC[KCs.size()]);

	}
}
