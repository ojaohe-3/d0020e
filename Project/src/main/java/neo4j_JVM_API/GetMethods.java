package neo4j_JVM_API;

import java.util.ArrayList;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course;
import Data.CourseDate;
import Data.Credits;
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
		String query = "MATCH (node: Course {" + Course.CourseLabels.CODE.toString() +": \"" + courseCode + "\"}) RETURN node";
		
		StatementResult result = this.communicator.readFromNeo(query);
		
		Record row = result.next();
		String name = row.get("node").get(Course.CourseLabels.NAME.toString()).toString();
		//String courseCode = row.get("node").get("courseCode").toString();
		String creds = row.get("node").get(Course.CourseLabels.CREDIT.toString()).toString();
		Credits credits = Credits.valueOf(creds);
		String description = row.get("node").get(Course.CourseLabels.DESCRIPTION.toString()).toString();
		String examiner = row.get("node").get(Course.CourseLabels.EXAMINER.toString()).toString();
		int year = Integer.parseInt(row.get("node").get(Course.CourseLabels.YEAR.toString()).toString());
		LP lp = LP.valueOf(row.get("node").get(Course.CourseLabels.LP.toString()).toString());
		CourseDate startDate = new CourseDate(year, lp);
		
		
		
		Course course = new Course(name, courseCode, credits, description, examiner, startDate);
		return course;
	}
	
	public void getKCwithTaxonomyLevel() {
		
	}
	
	
}
