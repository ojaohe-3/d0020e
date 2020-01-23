package neo4j_JVM_API;

import Data.Course;
import Data.CourseDate;
import Data.Credits;
import Data.KC;
import Data.LP;
import neoCommunicator.Neo4jCommunicator;

/**
 * Internal class for create functions.
 * @author Jesper, Robin
 *
 */
public class CreateMethods {

	public static void main(String[] args) {
		String[] array = Course.CourseLabels.asStringArray();
		array[0] = Course.CourseLabels.DESCRIPTION.toString();
		for (String s : array) {
			System.out.println(s);
		}
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
	public static void createCourse(Course course, Neo4jCommunicator communicator) {
		
		
		String query = "CREATE(n:Course{"+
		Course.CourseLabels.NAME.toString() + "\"" + course.getName().toString() + "\"" +
		Course.CourseLabels.CREDIT.toString() + "\"" + course.getCredit().toString() + "\"" +
		Course.CourseLabels.DESCRIPTION.toString() + "\"" + course.getDescription().toString() + "\"" +
		Course.CourseLabels.EXAMINER.toString() + "\"" + course.getExaminer().toString() + "\"" +
		Course.CourseLabels.YEAR.toString() + "\"" + Integer.toString(course.getStartPeriod().getYear()) + "\"" +
		Course.CourseLabels.LP.toString() + "\"" + course.getStartPeriod().getPeriod().toString() + "\"" +
		Course.CourseLabels.CODE.toString()+ "\"" + course.getCourseCode() + "\"" +
		"})";
		
		communicator.writeToNeo(query);
	}
	
	/**
	 * Create a KC in the database.
	 * @param kc
	 */
	public void createKC (KC kc) {
		String[] values = new String[] {};
		
		
		String query = QueryTranslator.getCreateStatement("n", "KC", new String[] {}, values);
		
		System.out.println(query);
		
		throw new RuntimeException("This function is not finished yet.");
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
