package neo4j_JVM_API;

import Data.Course;
import Data.CourseDate;
import neoCommunicator.Neo4jCommunicator;

public class ModifyMethods {
	
	private final Neo4jCommunicator communicator;
	
	public ModifyMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	public void changeUserPrivileges() {
		
	}
	
	public void removeUser() {
		
	}
	public void editKCWithTaxonomyLevel() {
		
	}
	
	public void editProgram() {
		
	}
	
	public void removeKCByName() {
		
	}
	
	public void removeProgram() {
		
	}

	/**
	 *  Edit a Course at Selector. todo KCs handling
	 * @param courseID Course Code ex D0020E, Selector
	 * @param nCourse new Course object, will write the new object
	 * @author Johan RH
	 */
	public void editCourse(String courseID,Course nCourse) {
		String query = "MATCH (n:Course{CourseCode:\""+ courseID+"\"} SET " +
				"n.CourseCode = \""+ nCourse.getCourseCode()+"\"" +
				"n.Name = \""+nCourse.getName()+"\"" +
				"n.Examiner =\""+nCourse.getExaminer()+"\"" +
				"n.Size =\""+nCourse.getCredit()+"\"";
		//not done yet
		communicator.writeToNeo(query);
	}

	/**
	 * Select by Course Code and Starting Year and remove and detach that object from the database
	 * @param couseID Course Code; ex D0020E
	 * @param startyear Starting Year object, What year, period?
	 * @author Johan RH
	 */
	public void removeCourse(String couseID, CourseDate startyear) {
		String query  ="MATCH(n:Course{CourseCode:\""+ couseID +"\", Startdate:\""+ startyear.getYear() +" "+ startyear.getPeriod().name() +"\"})" +"DETACH DELETE n";

		communicator.writeToNeo(query);
	}
	
	public void removeKCtaxonomy() {
		
	}
	
	

}
