package neo4j_JVM_API;

import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map;

import Data.*;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import Data.Course.CourseLabels;
import Data.Course;

import neoCommunicator.Neo4jCommunicator;
import org.neo4j.driver.v1.Value;

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
	public CourseProgram getProgram( String code, CourseDate startDate) {
		return getProgram(code,startDate, CourseLabels.YEAR, false);
	}


	/**
	 * Internal method for creating a course order. this is used by {@link #getProgram(String, CourseDate)} and
	 * {@link #getProgramSpecialization(String, String, CourseDate, CourseLabels, boolean)}.
	 * @param code
	 * @param startDate
	 * @param orderCoursesBy
	 * @param DESCENDING
	 * @return
	 */
	private ArrayList<Course> getCourseOrder(CourseProgram.ProgramType type, String name, String code, CourseDate startDate, CourseLabels orderCoursesBy, boolean DESCENDING) {
		String inProgramQuery = "MATCH (courseProgram: "+type+ "{"+ CourseProgram.ProgramLabels.NAME + ":\""+name+"\", " +CourseProgram.ProgramLabels.CODE+": \"" + code + "\", "+ CourseProgram.ProgramLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseProgram.ProgramLabels.LP + " : \"" + startDate.getPeriod() + "\" })"+
				"-[relation:IN_PROGRAM]-(courseInProgram:Course)"+
				"OPTIONAL MATCH (kc : KC)-[r]-(courseInProgram) RETURN courseInProgram,kc,type(r) ORDER BY courseInProgram." + orderCoursesBy;
		return this.getCourseOrderHelper(inProgramQuery);
	}

	private ArrayList<Course> getCourseOrder(CourseProgram.ProgramType type, String code, CourseDate startDate, CourseLabels orderCoursesBy, boolean DESCENDING) {
		String inProgramQuery = "MATCH (courseProgram: "+type+ "{" +CourseProgram.ProgramLabels.CODE+": \"" + code + "\", "+ CourseProgram.ProgramLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseProgram.ProgramLabels.LP + " : \"" + startDate.getPeriod() + "\" })"+
				"-[relation:IN_PROGRAM]-(courseInProgram:Course)"+
				"OPTIONAL MATCH (kc : KC)-[r]-(courseInProgram) RETURN courseInProgram,kc,type(r) ORDER BY courseInProgram." + orderCoursesBy;
		return this.getCourseOrderHelper(inProgramQuery);
	}

	private ArrayList<Course> getCourseOrderHelper(String inProgramQuery) {
		StatementResult courseQuery = this.communicator.readFromNeo(inProgramQuery);

		String tempCode;
		CourseDate tempDate;
		ArrayList<Course> courseOrder = new ArrayList<>();
		Record currentRow = null;
		Course lastCourse = null;
		while(courseQuery.hasNext()) {
			currentRow = courseQuery.next();
			Value currentCourse = currentRow.get(0);

			// Every row will only carry a course entry and max two KCs. We have to merge all the KCs and courses
			// That are equal.
			if (lastCourse != null)  {
				if (lastCourse.getCourseCode().equals(currentCourse.get(CourseLabels.CODE.toString()).toString().replaceAll("\"", ""))) {
					this.getProgramKCHelper(lastCourse, currentRow);
				} else {
					lastCourse = createCourse(currentCourse);
					this.getProgramKCHelper(lastCourse,currentRow);
					courseOrder.add(lastCourse);
				}
			} else {
				lastCourse = createCourse(currentCourse);
				this.getProgramKCHelper(lastCourse,currentRow);
				courseOrder.add(lastCourse);
			}
		}
		return courseOrder;
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
		System.out.println(query);
		StatementResult result = this.communicator.readFromNeo(query);
		Record row = result.next();

		CourseProgram courseProgram = createCourseProgram(this.getCourseOrder(CourseProgram.ProgramType.PROGRAM,code,startDate,orderCoursesBy,DESCENDING), row, "courseProgram");
		return courseProgram;
	}

	/**
	 * I made a helper function for retrieving a KC. This is used by {@link #getProgram(String, CourseDate)}.
	 * @param course
	 * @param currentRow
	 */
	private void getProgramKCHelper(Course course, Record currentRow) {
		Value kc = currentRow.get(1);

		if (!kc.isNull()) {
			String type = currentRow.get(2).toString().replaceAll("\"","");
			if (type.equals(Relations.DEVELOPED.toString())) {
				course.setDevelopedKC(createKC(kc));
			} else {
				course.setRequiredKC(createKC(kc));
			}
		}
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
		// TODO Merge this query with the second one.
		Record row = result.next();
		Course course = createCourse(row.get(0));

		// This query fetches ALL KCs
		query = "MATCH (course: Course {courseCode: \"" + courseCode.replaceAll("\"", "") + "\", "+ CourseLabels.YEAR + " : \"" + courseDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + courseDate.getPeriod() + "\" })"+
				"-[r]-(kc : KC) RETURN kc,type(r)";
		result = this.communicator.readFromNeo(query);

		// This iterates over all required and developed KCs.
		while(result.hasNext()) {
			Record KCs = result.next();
			Value kc = KCs.get(0);
			if (!kc.isNull()) {
				String type = KCs.get(1).toString().replaceAll("\"","");
				if (type.equals(Relations.DEVELOPED.toString())) {
					course.setDevelopedKC(createKC(kc));
				} else {
					course.setRequiredKC(createKC(kc));
				}
			}

		}
		return course;
	}
	
	/**
	 * Help function for creating a KC
	 * 
	 * @param row
	 * @return
	 */
	private KC createKC(Value row) {
		
		String generalDescription = row.get("generalDescription").toString().replaceAll("\"","");
		String taxonomyDescription = row.get("taxonomyDescription").toString().replaceAll("\"","");
		int taxonomyLevel = Integer.parseInt(row.get("taxonomyLevel").toString().replaceAll("\"", ""));
		String name = row.get("name").asString();
		
		KC kc = new KC(name, generalDescription, taxonomyLevel, taxonomyDescription);
		return kc;
	}
	/**
	 * Help function to createCourse
	 * 
	 * @param courseData
	 * @return
	 */
	private Course createCourse(Value courseData) {
		
		String name = courseData.get("name").toString();
		String courseCode = courseData.get("courseCode").toString();
		float credits = courseData.get("credit").asFloat();
		
		String description = courseData.get("description").toString();
		String examiner = courseData.get("examiner").toString();
		int year = Integer.parseInt(courseData.get("year").toString().replaceAll("\"", ""));
		LP lp = LP.valueOf(courseData.get("lp").toString().replaceAll("\"", ""));
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

		
		KC kc = createKC(row.get(0));
		return kc;
	}

	
	/**
	 * Get programSpecialization from database
	 *
	 * @param startDate
	 * @param code
	 * @param orderCoursesBy
	 * @param DESCENDING
	 * @return
	 */
	public ProgramSpecialization getProgramSpecialization(String name, String code, CourseDate startDate,CourseLabels orderCoursesBy, boolean DESCENDING) {
/*
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
			Course course = createCourse(currentRow.get(0));
			courseOrder.add(course);
		}


		//return courseProgramSpecialization;

 */

		String query = "MATCH (programSpecialization: ProgramSpecialization {"+ CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" , code : \"" + code + "\" }) ";
		query += "RETURN programSpecialization";

		StatementResult result = this.communicator.readFromNeo(query);
		Record row = result.next();

		ProgramSpecialization courseProgramSpecialization = createProgramSpecialization(this.getCourseOrder(CourseProgram.ProgramType.SPECIALIZATION,name, code,startDate,orderCoursesBy,DESCENDING), row, "programSpecialization");
		return courseProgramSpecialization;
	}

	/**
	 * Get programSpecialization from database
	 *
	 * @param startDate
	 * @param code
	 * @return
	 */
	public ProgramSpecialization getProgramSpecialization(String name, String code, CourseDate startDate) {
		return this.getProgramSpecialization(name,code,startDate,CourseLabels.YEAR, false);
	}

	private ProgramSpecialization createProgramSpecialization(ArrayList<Course> courseOrder, Record row, String nodename) {

		String name = row.get(nodename).get("name").toString().replaceAll("\"", "");
		String code = row.get(nodename).get("code").toString().replaceAll("\"", "");
		String description = row.get(nodename).get("description").toString().replaceAll("\"", "");
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

	/**
	 * Help method to create a CourseInformation object from a Record
	 * @param row
	 * @param nodename
	 * @return
	 */
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

