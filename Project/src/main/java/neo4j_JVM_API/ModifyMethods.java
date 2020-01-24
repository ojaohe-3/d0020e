package neo4j_JVM_API;

import Data.*;
import neoCommunicator.Neo4jCommunicator;


public class ModifyMethods {
	
	private final Neo4jCommunicator communicator;

	/**
	 * Manipulate and edit object already existing in the topology
	 * @param communicator Neo4j communicator object
	 */
	public ModifyMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	public void changeUserPrivileges() {
		
	}
	
	public void removeUser() {
		
	}

	/**
	 * Edit a Specific KC to a new generated KC object
	 * @param name name Selector
	 * @param taxlvl Taxonomy level Selector
	 * @param kcData New Object to change selected element
	 * @author Johan RH
	 */
	public void editKC(String name, int taxlvl, KC kcData) {
		String query = "MATCH(n:KC{"+ KC.KCLabel.NAME +":\""+name+"\","+ KC.KCLabel.TAXONOMYLEVEL +":"+taxlvl+"}) SET n={";
		query += KC.KCLabel.NAME+":\""+kcData.getName()+"\",";
		query += KC.KCLabel.TAXONOMYLEVEL+":\""+kcData.getTaxonomyLevel()+"\",";
		query += KC.KCLabel.GENERAL_DESCRIPTION+":\""+kcData.getGeneralDescription()+"\",";
		query += KC.KCLabel.TAXONOMY_DESCRIPTION+":\""+kcData.getTaxonomyDescription()+"\"}";

		communicator.writeToNeo(query);
	}
	
	public void editProgram() {
		
	}

	/**
	 * Remove and detach selected KC object
	 * @param name name selector
	 * @param taxlvl taxonomy level selector
	 * @author Johan RH, Markus
	 */
	public void removeKC(String name, int taxlvl) {
		String query = "MATCH(n:KC{"+ KC.KCLabel.NAME +":"+name+","+ KC.KCLabel.TAXONOMYLEVEL+ ":"+ taxlvl +"}) DETACH DELETE n";
	}
	
	public void removeProgram() {
		
	}

	/**
	 *  Edit a Course at Selector. todo KCs handling
	 *  Make sure that the edges and internal object match.
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
}
