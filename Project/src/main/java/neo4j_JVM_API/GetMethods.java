package neo4j_JVM_API;

import java.util.ArrayList;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course;
import Data.CourseDate;
import Data.Credits;
import Data.KC;
import Data.LP;
import neoCommunicator.Neo4jCommunicator;

public class GetMethods {
	
	private final Neo4jCommunicator communicator;
	
	public GetMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
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
	
	public String getProgram() {
		return "";
	}
	
	public Course getCourse(String courseCode) {
		String query = "MATCH (node: Course {courseCode: \"" + courseCode + "\"}) RETURN node";
		
		StatementResult result = this.communicator.readFromNeo(query);
		
		Record row = result.next();
		String name = row.get("node").get("name").toString();
		//String courseCode = row.get("node").get("courseCode").toString();
		String creds = row.get("node").get("credit").toString();
		Credits credits = Credits.valueOf(creds);
		String description = row.get("node").get("description").toString();
		String examiner = row.get("node").get("examiner").toString();
		int year = Integer.parseInt(row.get("node").get("year").toString());
		LP lp = LP.valueOf(row.get("node").get("lp").toString());
		CourseDate startDate = new CourseDate(year, lp);
		
		Course course = new Course(name, courseCode, credits, description, examiner, startDate);
		return course;
	}
	
	public KC getKCwithTaxonomyLevel(String name, int taxonomyLevel) {
		String query = "MATCH (node: Kc {name: \"" + name + "\", taxonomy_level: \"" + taxonomyLevel + "\"}) RETURN node";
		
		StatementResult result = this.communicator.readFromNeo(query);
		
		Record row = result.next();
		
		String generalDescription = row.get("node").get("general_description").toString();
		String taxonomyDescription = row.get("node").get("taxonomy_description").toString();
		
		
		KC kc = new KC(name, generalDescription, taxonomyLevel, taxonomyDescription);
		return kc;
	}
	
	
}
