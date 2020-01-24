package neo4j_JVM_API;

import java.util.ArrayList;

import org.neo4j.driver.v1.StatementResult;

import Data.Course;
import Data.CourseDate;
import Data.Credits;
import Data.KC;
import Data.LP;
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
		String[] array = Course.CourseLabels.asStringArray();
		array[0] = Course.CourseLabels.DESCRIPTION.toString();
		for (String s : array) {
			System.out.println(s);
		}
	}
	
	/**
	 * 
	 * @param communicator
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
	 */
	public void createCourse(Course course) {
		String query = "MATCH (course: Course {courseCode: \"" + course.getCourseCode() + "\", "+ CourseLabels.YEAR + " : \"" + course.getStartPeriod().getYear() + "\" , " + CourseLabels.LP + " : \"" + course.getStartPeriod().getPeriod() + "\" }) ";
		StatementResult result = this.communicator.readFromNeo(query);
		if (!result.hasNext()) {
			throw new IllegalArgumentException("The course exists already for the same year and study period.");
		}
		
		query = "CREATE(n:Course{"+
		Course.CourseLabels.NAME.toString() + "\"" + course.getName().toString() + "\"" +
		Course.CourseLabels.CREDIT.toString() + "\"" + course.getCredit().toString() + "\"" +
		Course.CourseLabels.DESCRIPTION.toString() + "\"" + course.getDescription().toString() + "\"" +
		Course.CourseLabels.EXAMINER.toString() + "\"" + course.getExaminer().toString() + "\"" +
		Course.CourseLabels.YEAR.toString() + "\"" + Integer.toString(course.getStartPeriod().getYear()) + "\"" +
		Course.CourseLabels.LP.toString() + "\"" + course.getStartPeriod().getPeriod().toString() + "\"" +
		Course.CourseLabels.CODE.toString()+ "\"" + course.getCourseCode() + "\"" +
		"})";
		
		ArrayList<KC> required = course.getRequiredKC();
		
		
		communicator.writeToNeo(query);
	}
	
	/**
	 * Create a KC in the database.
	 * @param kc
	 */
	public void createKC (KC kc) {
		String query = "";
		
		
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
	 */
	public void createProgram() {
		throw new RuntimeException("This function is not finished yet.");
	}
	
	
}
