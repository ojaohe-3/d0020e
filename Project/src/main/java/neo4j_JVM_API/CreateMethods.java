package neo4j_JVM_API;

import Data.Course;
import Data.KC;
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
	 * Add a topic. What's a topic btw?
	 */
	public void addTopic() {
			
	}
	
	/**
	 * Add a user. This function must be water tight.
	 */
	public void addUser() {
		
	}
	
	/**
	 * 
	 * @param course
	 */
	public void CreateCourse(Course course) {
		String[] options = new String[] {"name"};
		String[] values = new String[] {course.getName(), course.getCourseCode(), course.getCredit(), course.getDescription()};
		String query = QueryTranslator.CREATE("n", "Course", new String[] {}, new String[] {});
	}
	
	/**
	 * Create a KC in the database.
	 * @param kc
	 */
	public void createKC (KC kc) {
		
	}
	
	/**
	 * Why does this exist if one kc has only one taxonomy level?
	 * @author Robin
	 */
	public void createKCgroup() {
		
	}
	
	/**
	 * Create a program in the database.
	 */
	public void createProgram() {
	
	}
	
	
}
