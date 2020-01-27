package neo4j_JVM_API;

import java.util.ArrayList;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course.CourseLabels;
import Data.CourseDate;
import neoCommunicator.Neo4jCommunicator;

/**
 * Delete course, kc, program, topic, relations
 * 
 * @author Wilma and Tommy
 *
 */
public class DeleteMethods {

	private final Neo4jCommunicator communicator;
	
	public DeleteMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	/**
	 * Delete course from neo with courseCode and startDate
	 * 
	 * @param courseCode
	 * @param startDate
	 */
	public void deleteCourse(String courseCode, CourseDate startDate) {
		
		String query = "MATCH (course: Course {code: \"" + courseCode + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" })<-[relations]-(nodes) DELETE course, relations";
		
		communicator.writeToNeo(query);
	
	}
	
	/**
	 * Delete KC with taxonomyLevel
	 * Together with the KC all KCs with higher taxonomyLevels will be deleted
	 * 
	 * @param name
	 * @param taxonomyLevel
	 */
	public void deleteKC(String name, int taxonomyLevel) {
		
		String query = "MATCH (kc: KC {name: \"" + name + "\"}) RETURN kc";
		
		StatementResult result = this.communicator.readFromNeo(query);
		
		while(result.hasNext()) {
			
			Record row = result.next();
			int currentTaxonomyLevel = row.get("kc").get("taxonomyLevel").asInt();
			
			if(currentTaxonomyLevel >= taxonomyLevel) {
				String query2 = "MATCH (kc: KC {name: \"" + name + "\", taxonomyLevel: \"" + currentTaxonomyLevel + "\" })<-[relations]-(nodes) DELETE kc, relations";
				
				communicator.writeToNeo(query2);
			}
		}
		
	}
	
	public void deleteProgram(String name, CourseDate startDate) {
		
		String query = "MATCH (courseProgram: CourseProgram {name: \"" + name + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" })-[relations]-(nodes) DELETE courseProgram, relations";
		
		communicator.writeToNeo(query);
		
	}
	
	/**
	 * Delete topic from database
	 * 
	 * @param name
	 */
	public void deleteTopic(String name) {
		
		String query = "MATCH (topic: Topic {name: \"" + name + "\"})<-[relations]-(nodes) DELETE topic, relations";
		
		communicator.writeToNeo(query);
	
	}
	
	public void deleteRelation() {
		
	}
}
