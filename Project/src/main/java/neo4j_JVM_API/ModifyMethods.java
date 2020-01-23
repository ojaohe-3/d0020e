package neo4j_JVM_API;

import Data.*;
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
	public void editKCWithTaxonomyLevel(String name, int taxlvl, KC kcData) {
		String query = "MATCH(n:KC{"+ KC.KCLabel.NAME +":\""+name+"\","+ KC.KCLabel.TAXONOMYLEVEL +":"+taxlvl+"}) SET n={";
		query += KC.KCLabel.NAME+":\""+kcData.getName()+"\",";
		query += KC.KCLabel.TAXONOMYLEVEL+":\""+kcData.getTaxonomyLevel()+"\",";
		query += KC.KCLabel.GENERAL_DESCRIPTION+":\""+kcData.getGeneralDescription()+"\",";
		query += KC.KCLabel.TAXONOMY_DESCRIPTION+":\""+kcData.getTaxonomyDescription()+"\"}";

		communicator.writeToNeo(query);
	}
	
	public void editProgram() {
		
	}
	
	public void removeKCByName(String name, int taxlvl) {
		String query = "MATCH(n:KC{"+ KC.KCLabel.NAME +":"+name+","+ KC.KCLabel.TAXONOMYLEVEL+ ":"+ taxlvl +"}) DETACH DELETE n";
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
		String query = "MATCH (n:Course{CourseCode:\""+ courseID+"\"} SET n={";
		query += Course.CourseLabels.CODE.toString() +":"+nCourse.getCourseCode();
		query +=  Course.CourseLabels.CREDIT.toString() +":"+nCourse.getCredit();
		query += Course.CourseLabels.DESCRIPTION.toString() +":"+nCourse.getDescription();
		query +=Course.CourseLabels.LP.toString() +"="+nCourse.getStartPeriod().getPeriod().name();
		query +=  Course.CourseLabels.YEAR.toString() +"="+nCourse.getStartPeriod().getYear();
		query += Course.CourseLabels.EXAMINER.toString() +"="+nCourse.getExaminer();
		query += Course.CourseLabels.NAME.toString() +"="+nCourse.getName();

		query +="}";
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


	public static void main(String[] args) {
		//editKCWithTaxonomyLevel("test",2,new KC("test2","blabla",3,"blabla"));
	}
}
