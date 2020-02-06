package neo4j_JVM_API;

import Data.*;
import neoCommunicator.Neo4jCommunicator;


public class ModifyMethods {
	
	private final Neo4jCommunicator communicator;

	/**
	 * Manipulate and edit object already existing in the topology
	 * @param communicator Neo4j communicator object
	 * @author Johan RH
	 */
	public ModifyMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	/**
	 * Edit a Specific KC to a new generated KC object
	 * @param name name Selector
	 * @param taxlvl Taxonomy level Selector
	 * @param kcData New Object to change selected element
	 * @author Johan RH
	 */
	public void editKC(String name, int taxlvl, KC kcData) {
		String query = "MATCH(n:"+ KC.kc+"{"+ KC.KCLabel.NAME +":\""+name+"\","+ KC.KCLabel.TAXONOMYLEVEL +":"+taxlvl+"}) SET n={";
		query += KC.KCLabel.NAME+":\""+kcData.getName()+"\",";
		query += KC.KCLabel.TAXONOMYLEVEL+":\""+kcData.getTaxonomyLevel()+"\",";
		query += KC.KCLabel.GENERAL_DESCRIPTION+":\""+kcData.getGeneralDescription()+"\",";
		query += KC.KCLabel.TAXONOMY_DESCRIPTION+":\""+kcData.getTaxonomyDescription()+"\"}";

		communicator.writeToNeo(query);
	}
	
	public void editProgram(String programCode,CourseDate startyear, CourseProgram newProgram) {
		String query = "MATCH (n:CourseProgram{ProgramCode:\""+  programCode+"\"}) SET n={";
		query += CourseProgram.ProgramLabels.CODE.toString() +"="+newProgram.getCode();
		query += CourseProgram.ProgramLabels.DESCRIPTION.toString() +":"+newProgram.getDescription();
		query += CourseProgram.ProgramLabels.YEAR.toString() +":"+newProgram.getStartDate().getYear();
		query += CourseProgram.ProgramLabels.LP.toString() +":"+newProgram.getStartDate().getPeriod();
		query += CourseProgram.ProgramLabels.READING_PERIODS.toString() +":"+newProgram.getCourseOrder().getReadingPeriods();
		query += CourseProgram.ProgramLabels.CREDITS.toString() +":"+newProgram.getCredits();

		query +="}";
		communicator.writeToNeo(query);
	}

	/**
	 * Remove and detach selected KC object
	 * @param name name selector
	 * @param taxlvl taxonomy level selector
	 * @author Johan RH, Markus
	 */
	public void removeKC(String name, int taxlvl) {
		String query = "MATCH(n:"+ KC.kc+"{"+ KC.KCLabel.NAME.toString() +": \""+name+"\","+ KC.KCLabel.TAXONOMYLEVEL.toString() + ": \""+ taxlvl +"\"}) DETACH DELETE n";
		
		communicator.writeToNeo(query);
	}
	

	public void removeProgram( String programCode,CourseDate startyear) {
		String query  ="MATCH(n:Program{ProgramCode:\""+ programCode +"\", "+CourseProgram.ProgramLabels.YEAR+":\""+ startyear.getYear() +" "+ startyear.getPeriod().name() +"\"})" +"DETACH DELETE n";
		
		communicator.writeToNeo(query);
	}


	/**
	 *  Edit a Course at Selector. todo KCs handling
	 *  Make sure that the edges and internal object match.
	 * @param courseID Course Code ex D0020E, Selector
	 * @param period Period Selector
	 * @param nCourse new Course object, will write the new object
	 * @author Johan RH
	 */
	public void editCourse(String courseID, CourseDate period,Course nCourse) {
		String query = "MATCH (n:"+Course.course+"{"+ Course.CourseLabels.CODE +"\""+ courseID+"\","+
				Course.CourseLabels.YEAR +":"+period.getYear()+","+
				Course.CourseLabels.LP +":\""+period.getPeriod().name()+"\"} SET n={";
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
		String query  ="MATCH(n:"+Course.course+"{"+Course.CourseLabels.CODE.toString()+":\""+ couseID +"\", "+ Course.CourseLabels.YEAR.toString() + " :\""+ startyear.getYear() +"\", "+ Course.CourseLabels.LP +": \""+ startyear.getPeriod() +"\" }) DETACH DELETE n";
		
		
		communicator.writeToNeo(query);
	}
}
