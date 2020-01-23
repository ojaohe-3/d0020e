package neo4j_JVM_API;

import java.util.ArrayList;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course;
import Data.Course.CourseLabels;
import Data.CourseDate;
import Data.Credits;
import Data.KC;
import Data.LP;
import neoCommunicator.Neo4jCommunicator;

public class GetMethods {
	
	public String[] getTopics(Neo4jCommunicator communicator) {
		
		String query = "MATCH (node: Topic) RETURN node";
		
		StatementResult result = communicator.readFromNeo(query);
		
		ArrayList<String> resultArray = new ArrayList<String>();
		while ( result.hasNext() ) {
			
			Record row = result.next();

			
            resultArray.add(row.get("node").get("title").toString());
        }
		return resultArray.toArray(new String[resultArray.size()]);
	}
	
	
	public boolean login() {
		return false;
	}
	
	public String getUser() {
		return "";
	}
	
	public String getProgram() {
		return "";
	}
	public static Course getCourse(String courseCode, CourseDate courseDate, Neo4jCommunicator communicator) {
		String query = "MATCH (course: Course {courseCode: \"" + courseCode + "\", "+ CourseLabels.YEAR + " : \"" + courseDate.getYear() + ", \"" + CourseLabels.LP + "\" : \"" + courseDate.getPeriod() + "}) ";
		query += "MATCH (kcDeveloped)<-[r: DEVELOPED]-(course) ";
		query += "MATCH (kcRequired)<-[r: REQUIRED]-(course) RETURN course, kcDeveloped, kcRequired ";
		
		
		StatementResult result = communicator.readFromNeo(query);
		Course course = null;
		ArrayList<KC> developed = new ArrayList<KC>();
		ArrayList<KC> required = new ArrayList<KC>();
		
		while(result.hasNext()) {
			Record row = result.next();
			if(row.get("course") != null) {
				course = createCourse(row, "course");
				
			} else if(row.get("kcDeveloped") != null) {
				developed.add(createKC(row, "kcDeveloped"));
				
			} else if(row.get("kcRequired") != null) {
				required.add(createKC(row, "kcRequired"));
			}
		}
		
		
		for(KC kc : developed) {
			course.setDevelopedKC(kc);
		}
		for(KC kc: required) {
			course.setRequiredKC(kc);
		}
		
		return course;
	}
	
	/**
	 * Help function for creating a KC
	 * 
	 * @param row
	 * @param nodename
	 * @return
	 */
	private static KC createKC(Record row, String nodename) {
		
		String generalDescription = row.get(nodename).get("generalDescription").toString();
		String taxonomyDescription = row.get(nodename).get("taxonomyDescription").toString();
		int taxonomyLevel = row.get(nodename).get("taxonomyLevel").asInt();
		String name = row.get(nodename).get("name").asString();		
		
		KC kc = new KC(name, generalDescription, taxonomyLevel, taxonomyDescription);
		return kc;
	}
	/**
	 * Help function to createCourse
	 * 
	 * @param row
	 * @return
	 */
	private static Course createCourse(Record row, String nodename) {
		
		String name = row.get(nodename).get("name").toString();
		String courseCode = row.get(nodename).get("courseCode").toString();
		String creds = row.get(nodename).get("credit").toString();
		Credits credits = Credits.valueOf(creds);
		String description = row.get(nodename).get("description").toString();
		String examiner = row.get(nodename).get("examiner").toString();
		int year = Integer.parseInt(row.get(nodename).get("year").toString());
		LP lp = LP.valueOf(row.get(nodename).get("lp").toString());
		CourseDate startDate = new CourseDate(year, lp);
		
		Course course = new Course(name, courseCode, credits, description, examiner, startDate);
		
		return course;
	
	}
	
	/**
	 * Get KC from neo
	 * 
	 * @param name
	 * @param taxonomyLevel
	 * @return
	 */
	public KC getKCwithTaxonomyLevel(String name, int taxonomyLevel, Neo4jCommunicator communicator) {
		String query = "MATCH (node: Kc {name: \"" + name + "\", taxonomyLevel: \"" + taxonomyLevel + "\"}) RETURN node";
		
		StatementResult result = communicator.readFromNeo(query);
		
		Record row = result.next();

		
		KC kc = createKC(row, "node");
		return kc;
	}
		
	
}
