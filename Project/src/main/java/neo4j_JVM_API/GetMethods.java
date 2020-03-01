package neo4j_JVM_API;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

import Data.*;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course.CourseLabels;
import Data.Course;

import neoCommunicator.Neo4jCommunicator;

/**
 * Get kc, course, topic, program and users
 * 
 * @author Wilma and Jesper
 *
 */
public class GetMethods {
	
	private final Neo4jCommunicator communicator;
	
	public GetMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	/**
	 * Get all topics from database
	 * 
	 * @return string array with all topics
	 */
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

	/**
	 * Get Program from database
	 * @param code
	 * @param startDate
	 * @return
	 */
	public CourseProgram getProgram(String code, CourseDate startDate) {
		return getProgram(code,startDate, CourseLabels.YEAR, false);
	}
	
	/**
	 * Get Program from database
	 * 
	 * @param code
	 * @param startDate
	 * @param orderCoursesBy - Order the course in the program by this parameter.
	 * @param DESCENDING - Set this to true if you want the courses in descending order.
	 * @return
	 */
	public CourseProgram getProgram(String code, CourseDate startDate, CourseLabels orderCoursesBy, boolean DESCENDING) {
		String query = "MATCH (courseProgram: CourseProgram {code: \"" + code + "\", "+ CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" }) ";
		query += "RETURN courseProgram";
		
		
		StatementResult result = this.communicator.readFromNeo(query);
		Record row = result.next();
		
		//int readingPeriods = Integer.parseInt(row.get("courseProgram").get("readingPeriods").toString().replaceAll("\"",""));
		//CourseOrder courseOrder = new CourseOrder(readingPeriods);//useless
		
		String inProgramQuery = "MATCH (courseProgram: CourseProgram {"+CourseProgram.ProgramLabels.CODE+": \"" + code + "\", "+ CourseProgram.ProgramLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseProgram.ProgramLabels.LP + " : \"" + startDate.getPeriod() + "\" })";
		inProgramQuery += "-[relation:IN_PROGRAM]-(courseInProgram:Course) RETURN courseInProgram ORDER BY courseInProgram." + orderCoursesBy;
		if (DESCENDING) {
			inProgramQuery += " DESCENDING";
		}
		result = this.communicator.readFromNeo(inProgramQuery);

		String tempCode;
		CourseDate tempDate;
		ArrayList<Course> courseOrder = new ArrayList<>();
		while(result.hasNext()) {
			Record currentRow = result.next();
			tempCode = currentRow.get("courseInProgram").get(CourseLabels.CODE.toString()).toString().replaceAll("\"","");
			tempDate = new CourseDate(
					Integer.parseInt(currentRow.get("courseInProgram").get(CourseLabels.YEAR.toString()).toString().replaceAll("\"","")),
					LP.valueOf(currentRow.get("courseInProgram").get(CourseLabels.LP.toString()).toString().replaceAll("\"","")));
			Course course = getCourse(tempCode,tempDate);

			courseOrder.add(course);

		}
		
		CourseProgram courseProgram = createCourseProgram(courseOrder, row, "courseProgram");
		return courseProgram;
	}
	
	/**
	 * Help function to create a Program
	 * 
	 * @param courseOrder
	 * @param row
	 * @param nodename
	 * @return
	 */
	private CourseProgram createCourseProgram(ArrayList<Course> courseOrder, Record row, String nodename) {
		
		String name = row.get(nodename).get("name").toString().replaceAll("\"","");
		String code = row.get(nodename).get("code").toString().replaceAll("\"","");
		String description = row.get(nodename).get("description").toString().replaceAll("\"","");
		float credits = row.get(nodename).get("credits").asFloat();
		int year = Integer.parseInt(row.get(nodename).get("year").toString().replaceAll("\"",""));
		LP lp = LP.valueOf(row.get(nodename).get("lp").toString().replaceAll("\"",""));
		CourseDate startDate = new CourseDate(year, lp);	
		
		CourseProgram courseProgram = new CourseProgram(courseOrder, code, name, description, startDate, credits, CourseProgram.ProgramType.PROGRAM);
		
		return courseProgram;
	}
	
	/**
	 * Get course from database
	 * 
	 * @param courseCode
	 * @param courseDate
	 * @return course
	 */
	public Course getCourse(String courseCode, CourseDate courseDate) {
		String query = "MATCH (course: Course {courseCode: \"" + courseCode.replaceAll("\"", "") + "\", "+ CourseLabels.YEAR + " : \"" + courseDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + courseDate.getPeriod() + "\" }) RETURN course";
		StatementResult result = this.communicator.readFromNeo(query);
		
		Record row = result.next();
		
		Course course = createCourse(row, "course");
		
		String developedQuery = "MATCH (course: Course {courseCode: \"" + courseCode + "\", "+ CourseLabels.YEAR + " : \"" + courseDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + courseDate.getPeriod() + "\" }) ";
		developedQuery += "MATCH(developedKC : KC)<-[r:" + Relations.DEVELOPED.toString() +"]-(course) RETURN developedKC";
		System.out.println(developedQuery);
		//System.out.println(developedQuery);
		result = this.communicator.readFromNeo(developedQuery);
		
		while(result.hasNext()) {
			course.setDevelopedKC(createKC(result.next(), "developedKC"));
		}
		
		String requiredQuery = "MATCH (course: Course {courseCode: \"" + courseCode + "\", "+ CourseLabels.YEAR + " : \"" + courseDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + courseDate.getPeriod() + "\" }) ";
		requiredQuery += "MATCH(requiredKC : KC)<-[r: "+ Relations.REQUIRED.toString() +"]-(course) RETURN requiredKC";
		//System.out.println(requiredQuery);
		result = this.communicator.readFromNeo(requiredQuery);
		
		while(result.hasNext()) {
			course.setRequiredKC(createKC(result.next(), "requiredKC"));
		}
		
		return course;
	}
	
	/**
	 * Help function for creating a KC
	 * 
	 * @param row
	 * @param nodename
	 * @return
	 */
	private KC createKC(Record row, String nodename) {
		
		String generalDescription = row.get(nodename).get("generalDescription").toString();
		String taxonomyDescription = row.get(nodename).get("taxonomyDescription").toString();
		int taxonomyLevel = Integer.parseInt(row.get(nodename).get("taxonomyLevel").toString().replaceAll("\"", ""));
		String name = row.get(nodename).get("name").asString();		
		
		KC kc = new KC(name, generalDescription, taxonomyLevel, taxonomyDescription);
		return kc;
	}
	/**
	 * Help function to createCourse
	 * 
	 * @param row
	 * @return
	 */
	private Course createCourse(Record row, String nodename) {
		
		String name = row.get(nodename).get("name").toString();
		String courseCode = row.get(nodename).get("courseCode").toString();
		float credits = row.get(nodename).get("credit").asFloat();
		
		String description = row.get(nodename).get("description").toString();
		String examiner = row.get(nodename).get("examiner").toString();
		int year = Integer.parseInt(row.get(nodename).get("year").toString().replaceAll("\"", ""));
		LP lp = LP.valueOf(row.get(nodename).get("lp").toString().replaceAll("\"", ""));
		CourseDate startDate = new CourseDate(year, lp);
		
		Course course = new Course(name, courseCode, credits, description, examiner, startDate);
		
		return course;
	
	}
	
	/**
	 * Get KC from database
	 * 
	 * @param name
	 * @param taxonomyLevel
	 * @return
	 */
	public KC getKCwithTaxonomyLevel(String name, int taxonomyLevel) {
		
		String query = "MATCH (node: "+ KC.kc +" {name: \"" + name + "\", taxonomyLevel: \"" + taxonomyLevel + "\"}) RETURN node";
		
		StatementResult result = this.communicator.readFromNeo(query);
		
		Record row = result.next();

		
		KC kc = createKC(row, "node");
		return kc;
	}

	
	/**
	 * Get programSpecialization from database
	 * 
	 * 
	 * @param specialization
	 * @param code
	 *
	 */
	public ProgramSpecialization getProgramSpecialization(String specialization, CourseDate startDate, String code) {

		String query = "MATCH (programSpecialization: ProgramSpecialization {"+ CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" , code : \"" + code + "\" }) ";
		query += "RETURN programSpecialization";

		StatementResult result = this.communicator.readFromNeo(query);
		Record row = result.next();

		ArrayList<Course> courseOrder = new ArrayList<>();

		String inProgramQuery = "MATCH (programSpecialization: ProgramSpecialization {code: \"" + code + "\", "+ CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\"}) ";
		inProgramQuery += "MATCH(courseInProgram : Course)<-[relation: IN_PROGRAM]-(programSpecialization) RETURN courseInProgram, relation";
		result = this.communicator.readFromNeo(inProgramQuery);
		while(result.hasNext()) {
			Record currentRow = result.next();
			Course course = createCourse(currentRow, "courseInProgram");
			courseOrder.add(course);
		}
		
		ProgramSpecialization courseProgramSpecialization = createProgramSpecialization(courseOrder, row, "programSpecialization");
		return courseProgramSpecialization;
	}
	
	private ProgramSpecialization createProgramSpecialization(ArrayList<Course> courseOrder, Record row, String nodename) {
		
		String name = row.get(nodename).get("name").toString();
		String code = row.get(nodename).get("code").toString();
		String description = row.get(nodename).get("description").toString();
		float credits = row.get(nodename).get("credits").asFloat();
		int year = Integer.parseInt(row.get(nodename).get("year").toString().replaceAll("\"", ""));
		LP lp = LP.valueOf(row.get(nodename).get("lp").toString().replaceAll("\"", ""));
		CourseDate startDate = new CourseDate(year, lp);	
		
		ProgramSpecialization courseProgramSpecialization = new ProgramSpecialization(courseOrder, name, code, description, startDate, credits);
		
		return courseProgramSpecialization;
	}



	@Deprecated
	public Course getCourseNoKc(String courseCode, CourseDate courseDate) {

		String query = "MATCH (course: Course {courseCode: \"" + courseCode + "\", "+ CourseLabels.YEAR + " : \"" + courseDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + courseDate.getPeriod() + "\" }) RETURN course";
		
		StatementResult result = this.communicator.readFromNeo(query);
		Record row = result.next();

		CourseInformation courseNoKc = createCourseNoKc(row, "course");

		return (Course) courseNoKc;
		
	}

	private CourseInformation createCourseNoKc(Record row, String nodename) {
		
		String name = row.get(nodename).get("name").toString();
		String courseCode = row.get(nodename).get("courseCode").toString();
		float credits = row.get(nodename).get("credit").asFloat();

		String description = row.get(nodename).get("description").toString();
		String examiner = row.get(nodename).get("examiner").toString();
		int year = Integer.parseInt(row.get(nodename).get("year").toString().replaceAll("\"", ""));
		LP lp = LP.valueOf(row.get(nodename).get("lp").toString().replaceAll("\"", ""));
		CourseDate startDate = new CourseDate(year, lp);
		
		CourseInformation courseNoKc = new CourseInformation(name, courseCode, credits, description, examiner, startDate);
		
		return courseNoKc;
	
	}
}

