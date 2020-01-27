package neo4j_JVM_API;

import java.util.ArrayList;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course.CourseLabels;
import Data.CourseDate;
import neoCommunicator.Neo4jCommunicator;
import Data.Relations;

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
	/**
	*Delete program from the database
	*Remove all relationships before
	*
	*@param code
	*@param startDate
	*/
	public void deleteProgram(String code, CourseDate startDate) {
		
		String query = "MATCH (courseProgram: CourseProgram {code: \"" + code + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" })-[relations]-(nodes) DELETE courseProgram, relations";
		
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
	* Delete relationships between KC and courses
	*
	*@param KCName
	*@param taxonomyLevel
	*@param courseCode
	*@param startDate
	*@param relation
	*
	*/
	public void deleteRelationKC(String nameKC, int taxonomyLevel, String courseCode, CourseDate startDate, Relations relation) {

		String query = "MATCH (kc : KC{name: \"" + nameKC + "\", taxonomyLevel: \"" + taxonomyLevel + "\"})<-[r:"+relation+"]-(course: Course{courseCode: \"" + 
		courseCode + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\", " + CourseLabels.LP +":\"" +
		startDate.getPeriod() + "\"}) DELETE r";

		communicator.writeToNeo(query);
		
	}

	public void deleteRelationCourseToTopic(String topic, String courseCode, CourseDate startDate) {

		String query = "MATCH (topic : Topic{name: \"" + topic + "\"})<-[r:"+Relations.BELONGS_TO+"]-(course: Course{courseCode: \"" + 
		courseCode + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\", " + CourseLabels.LP +":\"" +
		startDate.getPeriod() + "\"}) DELETE r";
		
		communicator.writeToNeo(query);
	}

	public void deleteRelationKCToTopic(String topic, String nameKC, int taxonomyLevel) {

		String query = "MATCH (topic : Topic{name: \"" + topic + "\"})<-[r:"+Relations.BELONGS_TO+"]-(kc: KC{name: \"" + 
		nameKC + "\", taxonomyLevel: \"" + taxonomyLevel + "\"}) DELETE r";
		
		communicator.writeToNeo(query);
	}

	public void deleteRelationProgramToTopic(String topic, String programCode, CourseDate startDate) {

		String query = "MATCH (topic : Topic{name: \"" + topic + "\"})<-[r:"+Relations.BELONGS_TO+"]-(courseProgram: CourseProgram{: \"" + 
		programCode + "\", " + CourseLabels.YEAR + " : \"" + startDate.getYear() + "\", " + CourseLabels.LP +":\"" +
		startDate.getPeriod() + "\"}) DELETE r";
		
		communicator.writeToNeo(query);
	}

	public void deleteRelationCourseInProgram(String courseCode, CourseDate startDateCourse, String programCode, CourseDate startDateProgram) {

		String query = "MATCH (courseCode : CourseCode{code: \"" + courseCode + "\"," + CourseLabels.YEAR + ": \"" + startDateCourse.getYear() +
		 "\", " + CourseLabels.LP + ": \"" + startDateCourse.getPeriod() + "\"})-[r:"+ Relations.IN_PROGRAM +"]->(courceProgram: CourseProgram{courseProgram: \"" + 
		programCode + "\", " + CourseLabels.YEAR + " : \"" + startDateProgram.getYear() + "\", " + CourseLabels.LP +":\"" +
		startDateProgram.getPeriod() + "\"}) DELETE r";
		
		communicator.writeToNeo(query);
	}
}
