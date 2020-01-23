package neo4j_JVM_API;

import java.util.ArrayList;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course;
import Data.CourseDate;
import Data.CourseOrder;
import Data.Credits;
import Data.KC;
import Data.LP;
import neoCommunicator.Neo4jCommunicator;

public class GetMethods {
	
	private final Neo4jCommunicator communicator;
	
	public GetMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	/**
	 * get all topics from neo
	 * 
	 * @return string array
	 */
	public String[] getTopics() {
		
		String query = "MATCH (node: Topic) RETURN node";
		
		StatementResult result = this.communicator.readFromNeo(query);
		
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
	
	/**
	 * get program from neo
	 * 
	 * @param code
	 * @return
	 */
	public CourseProgram getProgram(String code) {
		String query = "MATCH (node: Program {code: \"" + code + "\"}) return node";
		
		StatementResult result = this.communicator.readFromNeo(query);
		
		Record row = result.next();
		int readingPeriods = Integer.parseInt(row.get("node").get("readingPeriods").toString());
		
		
		String query2 = "MATCH (node: Program {code: \"" + code + "\"}) ";
		query2 += "MATCH (node)<-[r: BELONGS_TO]-(courses) REUTRN courses";
		
		result = this.communicator.readFromNeo(query2);
		
		CourseOrder courseOrder = new CourseOrder(readingPeriods);
		
		while(result.hasNext()) {
		
			row = result.next();
			
			Course course = createCourse(row);
			
			
			
		}
		
		
		
		CourseProgram program = new CourseProgram();
		
		return program;
	}
	
	/**
	 * Get course from neo
	 * 
	 * @param courseCode
	 * @return
	 */
	public Course getCourse(String courseCode) {
		String query = "MATCH (course: Course {courseCode: \"" + courseCode + "\"}) ";
		query += "MATCH (kcDeveloped)<-[r: DEVELOPED]-(course) ";
		query += "MATCH (kcRequired)<-[r: REQUIRED]-(course) RETURN course, kcDeveloped, kcRequired ";
		
		
		StatementResult result = this.communicator.readFromNeo(query);
		
		while(result.hasNext()) {
			Record row = result.next();
			if(row.get("course") != null) {
				
			}
		}
		
		Record row = result.next();
		Course course = createCourse(row, "node");
		
		return course;
	}
	
	/**
	 * Help function to createCourse
	 * 
	 * @param row
	 * @return
	 */
	private Course createCourse(Record row, String nodename) {
		
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
	public KC getKCwithTaxonomyLevel(String name, int taxonomyLevel) {
		String query = "MATCH (node: Kc {name: \"" + name + "\", taxonomyLevel: \"" + taxonomyLevel + "\"}) RETURN node";
		
		StatementResult result = this.communicator.readFromNeo(query);
		
		Record row = result.next();
		
		String generalDescription = row.get("node").get("generalDescription").toString();
		String taxonomyDescription = row.get("node").get("taxonomyDescription").toString();
		
		
		KC kc = new KC(name, generalDescription, taxonomyLevel, taxonomyDescription);
		return kc;
	}
	
	
}
