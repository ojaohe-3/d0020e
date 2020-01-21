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

private final Neo4jCommunicator communicator;
	
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
		String[] values = new String[] {course.getName(), course.getCourseCode(), 
										course.getCredit().toString(), course.getDescription(), 
										course.getExaminer(), Integer.toString(course.getStartPeriod().getYear()), 
										course.getStartPeriod().getPeriod().toString()};
		String query = QueryTranslator.CREATE("n", "Course", new String[] {"name", "courseCode", "credit", "description", "examiner", "year", "lp"}, values);
		System.out.println(query);
		throw new RuntimeException("This function is not finished yet.");
	}
	
	/**
	 * Create a KC in the database.
	 * @param kc
	 */
	public void createKC (KC kc) {
		String[] values = new String[] {};
		
		
		String query = QueryTranslator.CREATE("n", "KC", new String[] {}, values);
		
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
