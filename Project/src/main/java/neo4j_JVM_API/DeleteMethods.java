package neo4j_JVM_API;

import java.util.ArrayList;

import javax.management.relation.Relation;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course;
import Data.Course.CourseLabels;
import Data.CourseDate;
import Data.CourseProgram;
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
		
		//String query = "MATCH (course: Course {code: \"" + courseCode + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" })-[relations]-(nodes) DELETE course, relations";
		String query = "MATCH (course: Course {courseCode: \"" + courseCode + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" }) DETACH DELETE course";

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
			int currentTaxonomyLevel = Integer.parseInt(row.get("kc").get("taxonomyLevel").toString().replaceAll("\"", ""));
			
			if(currentTaxonomyLevel >= taxonomyLevel) {
				String query2 = "MATCH (kc: KC {name: \"" + name + "\", taxonomyLevel: \"" + currentTaxonomyLevel + "\" }) DETACH DELETE kc";
				
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
		
		String query = "MATCH (courseProgram: CourseProgram {code: \"" + code + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" }) DETACH DELETE courseProgram";
		
		communicator.writeToNeo(query);
		
	}
	
	/**
	 * Delete program specialization
	 * 
	 * @param name
	 * @param startDate
	 */
	public void deleteProgramSpecialization(String name, CourseDate startDate) {
		
		String query = "MATCH (programSpecialization: ProgramSpecialization {name: \"" + name + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" }) DETACH DELETE programSpecialization";
		
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
	 * @param nameKC
	 * @param taxonomyLevel
	 * @param courseCode
	 * @param startDate
	 * @param relation
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
	
	/**
	 * Delete relation between program and topic
	 * 
	 * @param programCode
	 * @param startDate when the program starts
	 * @param topic
	 */
	public void deleteRelationProgramToTopic(String programCode, CourseDate startDate, String topic) {
		String query = "MATCH (topic: Topic {name: \"" + topic + "\"})<-[relation]-(program: " + CourseProgram.ProgramType.PROGRAM + " {" + CourseProgram.ProgramLabels.CODE + ": \"" + programCode + "\", "+ CourseLabels.YEAR + " : \"" + startDate.getYear() + "\", " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" }) DELETE relation";
		
		communicator.writeToNeo(query);
	}
	
	/**
	 * Delete relation between course and program (IN_PROGRAM)
	 * 
	 * @param courseCode
	 * @param courseStartDate
	 * @param programCode
	 * @param programStartDate
	 * @param type - Define if Program or programSpecialization
	 */
	public void deleteRelationCourseInProgram(String courseCode, CourseDate courseStartDate, String programCode, CourseDate programStartDate, CourseProgram.ProgramType type) {
		String query = "MATCH (program: " + type + " {" + CourseProgram.ProgramLabels.CODE + ": \"" + programCode + "\", "+ 
				CourseLabels.YEAR + " : \"" + programStartDate.getYear() + "\", " + 
				CourseLabels.LP + " :\"" + programStartDate.getPeriod() + 
				"\" })<-[relation]-(course: " + Course.course + " {" + 
				Course.CourseLabels.CODE + ": \"" + courseCode + "\", "+ 
				CourseLabels.YEAR + " : \"" + courseStartDate.getYear() + "\", " + 
				CourseLabels.LP + " : \"" + courseStartDate.getPeriod() + 
				"\" }) DELETE relation";
		
		communicator.writeToNeo(query);
	}


}
