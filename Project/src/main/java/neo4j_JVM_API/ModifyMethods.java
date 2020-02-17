package neo4j_JVM_API;

import Data.*;
import Data.Course.CourseLabels;
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
	 * Takes in the name of the KC and its taxonomylevel and changes it's description
	 * @param kc takes in the kc object and change genereal description and taxonomy description to the desired values
	 * @author Tommy A
	 */
	 public void editKC(KC kc) {

		String query = "MATCH(kc: KC {" + KC.KCLabel.NAME.toString() + ": \"" + kc.getName() + "\"}) SET kc." + 
		KC.KCLabel.GENERAL_DESCRIPTION.toString() + "= \"" + kc.getGeneralDescription() + "\"";
	 
		communicator.writeToNeo(query);
	 }

	 /**
	 * Searches after the node from kcName but uses taxonomylevel from the object to change the name of the KC.
	 * @param kcName is the current name in the database
	 * @param kc contains the new name for the KC node, uses the old taxonomylevel
	 * @author Tommy A
	 */

	public void editKCName(KC kc, String kcName) {

		String query = "MATCH(kc: KC {" + KC.KCLabel.NAME.toString() + ": \"" + kcName + "\"}) SET kc." +
		KC.KCLabel.NAME.toString() + "= \"" + kc.getName() + "\""; 

		communicator.writeToNeo(query);
	 }

	 /**
	 *Change the taxonomyDescription of KC with a specific TAXONOMYLEVEL
	 *@param kc
	 *@author Robin, Tommy
	 */

	 public void editKCTaxonomyDescription(KC kc) {

		String query = "MATCH(kc: KC {" + KC.KCLabel.NAME.toString() + ": \"" + kc.getName() + "\", " + 
		KC.KCLabel.TAXONOMYLEVEL.toString() + ": \"" + kc.getTaxonomyLevel() + "\"}) SET kc." + 
		KC.KCLabel.TAXONOMY_DESCRIPTION + "= \"" + kc.getTaxonomyDescription() + "\"";
		
		communicator.writeToNeo(query);
	 }



	 /**
	 * Edit a Specific KC to a new generated KC object
	 * @param name name Selector
	 * @param taxlvl Taxonomy level Selector
	 * @param kcData New Object to change selected element
	 * @author Johan RH
	 */
	/*
	public void editKC(String name, int taxlvl, KC kcData) {
		String query = "MATCH(n:"+ KC.kc+"{"+ KC.KCLabel.NAME +":\""+name+"\","+ KC.KCLabel.TAXONOMYLEVEL +":"+taxlvl+"}) SET n={";
		query += KC.KCLabel.NAME+":\""+kcData.getName()+"\",";
		query += KC.KCLabel.TAXONOMYLEVEL+":\""+kcData.getTaxonomyLevel()+"\",";
		query += KC.KCLabel.GENERAL_DESCRIPTION+":\""+kcData.getGeneralDescription()+"\",";
		query += KC.KCLabel.TAXONOMY_DESCRIPTION+":\""+kcData.getTaxonomyDescription()+"\"}";

		communicator.writeToNeo(query);
	}
	*/
	
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
	 *  Edit a Course at Selector. todo KCs handling
	 *  Make sure that the edges and internal object match.
	 * @param courseID Course Code ex D0020E, Selector
	 * @param nCourse new Course object, will write the new object
	 * @author Johan RH
	 */
	public void editCourse(String courseID,Course nCourse) {
		String query = "MATCH (n:"+Course.course+"{CourseCode:\""+ courseID+"\"} SET n={";
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
	 //Moved to DeleteMethods
	 @Deprecated
	public void removeCourse(String couseID, CourseDate startyear) {
		String query  ="MATCH(n:"+Course.course+"{"+Course.CourseLabels.CODE.toString()+":\""+ couseID +"\", "+ Course.CourseLabels.YEAR.toString() + " :\""+ startyear.getYear() +"\", "+ Course.CourseLabels.LP +": \""+ startyear.getPeriod() +"\" }) DETACH DELETE n";
		
		
		communicator.writeToNeo(query);
	}
		/**
	 * Remove and detach selected KC object
	 * @param name name selector
	 * @param taxlvl taxonomy level selector
	 * @author Johan RH, Markus
	 */
	 //moved to DeleteMethods
	 @Deprecated
	public void removeKC(String name, int taxlvl) {
		String query = "MATCH(n:"+ KC.kc+"{"+ KC.KCLabel.NAME.toString() +": \""+name+"\","+ KC.KCLabel.TAXONOMYLEVEL.toString() + ": \""+ taxlvl +"\"}) DETACH DELETE n";
		
		communicator.writeToNeo(query);
	}
	//moved to DeleteMethods
	@Deprecated
	public void removeProgram( String programCode,CourseDate startyear) {
		String query  ="MATCH(n:Program{ProgramCode:\""+ programCode +"\", "+CourseProgram.ProgramLabels.YEAR+":\""+ startyear.getYear() +" "+ startyear.getPeriod().name() +"\"})" +"DETACH DELETE n";
		
		communicator.writeToNeo(query);
	}

	/**
	*Changes reading order of a course in a program, PS the courses startperiod needs to be changed before using this method
	*@param course
	*@param Program
	*@author Robin, Tommy
	*/
	public void editInProgramCourseRelation(CourseInformation course, ProgramInformation program) {
		
		String query = "MATCH (courseProgram:" +  CourseProgram.ProgramType.PROGRAM + "{code: \"" +  CourseProgram.ProgramLabels.CODE + "\", "+ 
		CourseLabels.YEAR + " : \"" + program.getStartDate().getYear() + "\" , " + 
		CourseLabels.LP + " : \"" + program.getStartDate().getPeriod() + "\" })-[r:" + Relations.IN_PROGRAM + "]-(course: Course {courseCode: \"" + course.getCourseCode() + "\", "+ 
		CourseLabels.YEAR + " : \"" + course.getStartPeriod().getYear() + "\" , " + 
		CourseLabels.LP + " : \"" + course.getStartPeriod().getPeriod() + "\" })"; 

				if(communicator.readFromNeo(query+ "RETURN r").hasNext()){
				
				query += "SET r."+ Relations.YEAR+" = \"" +course.getStartPeriod().getYear() + "\", r."+ Relations.PERIOD+" = \"" +course.getStartPeriod().getPeriod() + "\"";
				
				communicator.writeToNeo(query);
				}
	}
}
