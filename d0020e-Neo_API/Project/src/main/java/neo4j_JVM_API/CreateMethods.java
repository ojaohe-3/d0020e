package neo4j_JVM_API;

import java.util.ArrayList;

import org.neo4j.driver.v1.StatementResult;

import Data.Course;
import Data.CourseDate;
import Data.CourseOrder;
import Data.CourseProgram;
import Data.Credits;
import Data.KC;
import Data.LP;
import Data.Relations;
import Data.Course.CourseLabels;
import neoCommunicator.Neo4jCommunicator;

/**
 * Internal class for create functions.
 * @author Jesper, Robin
 *
 */
public class CreateMethods {

private final Neo4jCommunicator communicator;

	public static void main(String[] args) {
		/*Course course = new Course("Math course", "D0009M", Credits.FIFTEEN, "you do math", "Stefan", new CourseDate(2019, LP.TWO));
		
		KC kc = new KC("maeth", "Calulations", 1, "counting to 4");
		KC kc2 = new KC("extra maeth", "Division", 3, "Divide 4 by 2");
		KC kc3 = new KC("super maeth", "Multiplication", 4, "Multiply 4 by 2");
		course.setRequiredKC(kc);
		course.setDevelopedKC(kc2);
		course.setDevelopedKC(kc3);
		
		CreateMethods methods = new CreateMethods(null);
		methods.createCourse(course);
		methods.createKC(kc);
		methods.createKC(kc2);
		methods.createKC(kc3);
		methods.createCourseKCrelation(course);
		*/
		Neo4jCommunicator communicator = null;
		CreateMethods methods = new CreateMethods(communicator);
		CourseOrder order = new CourseOrder(12);
		CourseProgram program = new CourseProgram(order, "TCDAA", "Computer engineering", "We are the definitive nerds", new CourseDate(2019,LP.ONE), Credits.THIRTY);
		
		
		methods.createProgram(program);
	}
	
	/**
	 * 
	 * @param communicator  used when calling the database.
	 * @see neoCommunicator.Neo4jCommunicator
	 */
	public CreateMethods(Neo4jCommunicator communicator){
		this.communicator = communicator;
	}
	
	/**
	 * Add a topic.
	 */
	public void addTopic() {
		throw new RuntimeException("This function is not finished yet.");
	}
	
	/**
	 * Add a user. This method must prevent any unauthorized access and 
	 * hacker attacks.
	 */
	public void addUser() {
		throw new RuntimeException("This function is not finished yet.");
	}
	
	/**
	 * Add a new course to the server. This is still a work in progress, so don't use it yet.
	 * @param course - The selected course to create.
	 * @author Robin
	 * @see Data.Course
	 */
	public void createCourse(Course course) {
		String query = "MATCH (course: Course {courseCode: \"" + course.getCourseCode() + "\", "+ CourseLabels.YEAR + " : \"" + course.getStartPeriod().getYear() + "\" , " + CourseLabels.LP + " : \"" + course.getStartPeriod().getPeriod() + "\" }) ";
		StatementResult result = this.communicator.readFromNeo(query);
		
		/* Check if a course exist already. */
		if (result.hasNext()) {
			throw new IllegalArgumentException("The course exists already for the same year and study period.");
		}
		
		query = "CREATE(n:" + Course.course+"{"+
		Course.CourseLabels.NAME.toString() + ":\"" + course.getName().toString() + "\", " +
		Course.CourseLabels.CREDIT.toString() + ":\"" + course.getCredit().toString() + "\", " +
		Course.CourseLabels.DESCRIPTION.toString() + ":\"" + course.getDescription().toString() + "\", " +
		Course.CourseLabels.EXAMINER.toString() + ":\"" + course.getExaminer().toString() + "\", " +
		Course.CourseLabels.YEAR.toString() + ":\"" + Integer.toString(course.getStartPeriod().getYear()) + "\", " +
		Course.CourseLabels.LP.toString() + ":\"" + course.getStartPeriod().getPeriod().toString() + "\", " +
		Course.CourseLabels.CODE.toString()+ ":\"" + course.getCourseCode() + "\"" +
		"})";		
		
		communicator.writeToNeo(query);
	}
	
	/**
	 * Create a KC in the database.
	 * @param kc- The KC to add.
	 * @author Robin
	 * @see Data.KC
	 */
	public void createKC (KC kc) {
		String query = "CREATE(n:" +KC.kc + "{" +
			KC.KCLabel.NAME.toString() + ":\"" + kc.getName()+"\", " + 
			KC.KCLabel.GENERAL_DESCRIPTION.toString()+ ":\"" + kc.getGeneralDescription() + "\", " + 
			KC.KCLabel.TAXONOMYLEVEL.toString() + ":\"" + kc.getTaxonomyLevel() + "\", " + 
			KC.KCLabel.TAXONOMY_DESCRIPTION.toString() + ":\"" + kc.getTaxonomyDescription() + "\"})";
		communicator.writeToNeo(query);
	}
	
	/**
	 * 
	 * 
	 */
	public void createKCgroup() {
		throw new RuntimeException("This function is not finished yet.");
	}
	
	/**
	 * Add a new program in the database.
	 * @param program - The program you want to add.
	 * @see Data.CourseProgram
	 */
	public void createProgram(CourseProgram program) {
		String query = "CREATE(n:" + CourseProgram.courseProgram + " {" +
				CourseProgram.ProgramLabels.NAME.toString() + ":\"" + program.getName() + "\", " +
				CourseProgram.ProgramLabels.DESCRIPTION.toString() + ":\"" + program.getDescription() + "\", " +
				CourseProgram.ProgramLabels.CODE.toString() + ":\"" + program.getCode() + "\", " +
				CourseProgram.ProgramLabels.CREDITS.toString() + ":\"" + program.getCredits() + "\", " +
				CourseProgram.ProgramLabels.YEAR.toString() + ":\"" + program.getStartDate().getYear() + "\", " +
				CourseProgram.ProgramLabels.LP.toString() + ":\"" + program.getStartDate().getPeriod().toString() + "\", " +		
				CourseProgram.ProgramLabels.READING_PERIODS.toString() + ":\"" + program.getCourseOrder().getReadingPeriods() + "\"})";
		System.out.println(query);
		communicator.writeToNeo(query);
	}
	
	/**
	 * Add relations between a program and it's courses.
	 * The courses must be added to the program before the 
	 * connections are made.
	 * @param program - The course program.
	 * @see Data.CourseProgram
	 */
	public void createProgramCourseRelation(CourseProgram program) {
		CourseOrder courseOrder = program.getCourseOrder();
		Course[][] courses = courseOrder.getCourseArray();
		
		String query = "MATH(cp: " + CourseProgram.courseProgram + "{" + CourseProgram.ProgramLabels.CODE.toString() + ":\"" + program.getCode() + "\", " + 
				CourseProgram.ProgramLabels.YEAR.toString()+ ":\"" + program.getStartDate().getYear()+ "\", " + 
				CourseProgram.ProgramLabels.LP.toString() + "\"" + program.getStartDate().getPeriod().toString()+ "})";
		
		for (int x=0; x < courses.length; x++) {
			for (int y = 0; y < courses.length; y++) {
				query += "MATCH ()";
			}
		}
	}
	
	/**
	 * Add a relation between a course and a KC to the database. 
	 * The KCs must be added as required or developed to the desired 
	 * course first.
	 * @param course - The course used for the relations.
	 * @see Data.Course
	 */
	public void createCourseKCrelation(Course course) {
		ArrayList<KC> developed = course.getDevelopedKC();
		ArrayList<KC> required = course.getRequiredKC();
		String query = "MATCH (course: " + Course.course +" {" +CourseLabels.CODE.toString() + ":\""+course.getCourseCode()+"\", "+
				Course.CourseLabels.YEAR.toString() +":\"" + course.getStartPeriod().getYear()+"\","+
				Course.CourseLabels.LP.toString() + ":\""+course.getStartPeriod().getPeriod().toString()+"\"})";
		for (int i = 0; i < required.size(); i++) {
			query += " MATCH ( kc" + i + ": " + KC.kc + "{" + KC.KCLabel.NAME.toString() +":\"" + required.get(i).getName() + "\", " + KC.KCLabel.TAXONOMYLEVEL.toString() + ":\"" + required.get(i).getTaxonomyLevel()+ "\"})";
		}
		for (int i = 0; i < developed.size(); i++) {
			query += " MATCH ( kc" + (i+required.size()) + ": " + KC.kc + "{" + KC.KCLabel.NAME.toString() +":\"" + developed.get(i).getName() + "\", " + KC.KCLabel.TAXONOMYLEVEL.toString() + ":\"" + developed.get(i).getTaxonomyLevel()+ "\"})";
		}
		
		for (int i = 0; i < required.size(); i++) {
			query += "CREATE (course)-[r" + i + ":" + Relations.REQUIRED.toString() + "]->(kc" + i + ")";
		}
		for (int i = 0; i < developed.size(); i++) {
			query += "CREATE (course)-[r" + (required.size()+i) + ":" + Relations.DEVELOPED.toString() + "]->(kc" + (required.size()+i) + ")";
		}
		communicator.writeToNeo(query);
	}	
}