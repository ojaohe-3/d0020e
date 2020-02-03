package neo4j_JVM_API;

import java.util.ArrayList;

import javax.management.relation.Relation;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course;
import Data.Course.CourseLabels;
import Data.CourseDate;
import Data.KC;
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
	 * Clear the entire database. Only used for testing purposes.
	 * @author Jesper.
	 */
	public void clear() {
		
		String query = "MATCH (n)-[r]-(m) DELETE n,r,m";
		this.communicator.writeToNeo(query);
		query = "MATCH (n) DELETE n";
		this.communicator.writeToNeo(query);
		
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
	
	/**
	 * Delete program from database
	 * 
	 * @param code
	 * @param startDate
	 */
	public void deleteProgram(String code, CourseDate startDate) {
		
		String query = "MATCH (courseProgram: CourseProgram {code: \"" + code + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" })-[relations]-(nodes) DELETE courseProgram, relations";
		
		communicator.writeToNeo(query);
		
	}
	
	/**
	 * Delete program specialization
	 * 
	 * @param name
	 * @param startDate
	 */
	public void deleteProgramSpecialization(String name, CourseDate startDate, String code) {
		
		String query = "MATCH (programSpecialization: ProgramSpecialization {name: \"" + name + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" , code:" + code + "\" })-[relations]-(nodes) DELETE programSpecialization, relations";
		
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
	
	/**
	 * Delete relations between course and kc
	 * 
	 * @param kc
	 * @param course
	 * @param startDate
	 */
	public void deleteRelationKC(String nameKC, int taxonomyLevel, String courseCode, CourseDate startDate, Relation relation) {
		String query = "MATCH (kc: KC {name: \"" + nameKC + "\", taxonomyLevel:" + taxonomyLevel + "\"})-[relation: " + relation.toString() 
		+ "]-(course: Course {" + Course.CourseLabels.CODE + ": \"" + courseCode + "\", "+ CourseLabels.YEAR + " : \"" + startDate.getYear() 
		+ "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" }) DELETE relation";
		
		communicator.writeToNeo(query);
	}
	
	/**
	 * Delete relation between kc and topic
	 * 
	 * @param nameKC
	 * @param topic
	 */
	public void deleteRelationKCToTopic(String nameKC, String topic) {
		String query = "MATCH (topic: Topic {name: \"" + topic + "\"})<-[relation]-(kc: KC {name: \"" + nameKC + "\") DELETE relation";
		
		communicator.writeToNeo(query);
	}
	
	/**
	 * Delete relation between course and topic
	 * 
	 * @param courseCode
	 * @param startDate
	 * @param topic
	 */
	public void deleteRelationCourseToTopic(String courseCode, CourseDate startDate, String topic) {
		String query = "MATCH (topic: Topic {name: \"" + topic + "\"})<-[relation]-(course: Course {" + Course.CourseLabels.CODE + ": \"" + courseCode + "\", "+ CourseLabels.YEAR + " : \"" + startDate.getYear() + "\", " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" }) DELETE relation";
		
		communicator.writeToNeo(query);
	}
	
	public void deleteRelationProgramToTopic(String programCode, CourseDate startDate, String topic) {
		
	}
	
	public void deleteRelationCourseInProgram() {
		
	}
	
	public void deleteRelationSpecialization() {
		
	}
}
