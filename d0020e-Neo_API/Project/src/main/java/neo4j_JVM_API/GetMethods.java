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
	 * Login manager, todo load session when logged in
	 * @param username Selector
	 * @param password Input to test authentication
	 * @return true if successful
	 * @author Johan RH
	 * @throws NoSuchAlgorithmException
	 */
	public boolean login(String username, String password) throws NoSuchAlgorithmException {
		String query = "MATCH(n:User{"+ User.UserLables.USERNAME +":\""+username+"\"} return n";
		StatementResult result = communicator.readFromNeo(query);
		if(!result.hasNext())
			return false;
		Record record = result.next();
		String pwd = record.get("n").get(User.UserLables.PASSWORD.toString()).toString();
		if(pwd.equals(Security.generateHash(password))) {
			// session = getUser(username);
			return true;
		}
		return false;
	}

	/**
	 * get user object
	 * @param username Selector
	 * @author Johan RH
	 * @return User object
	 */
	public User getUser(String username) {
		String query = "MATCH(n:User{"+ User.UserLables.USERNAME +":\""+username+"\"} return n";
		StatementResult result = communicator.readFromNeo(query);
		if(!result.hasNext())
			return null;
		Record record = result.next();
		User user = new User(
				record.get('n').get(User.UserLables.USERNAME.toString()).toString(),
				record.get('n').get(User.UserLables.PASSWORD.toString()).toString()
				);
		query = "MATCH(:User{"+ User.UserLables.USERNAME +":\""+username+"\"})-->(n) return n";
		result = communicator.readFromNeo(query);
		while (result.hasNext()){
			user.addCourse(createCourse(result.next(),"n"));
		}
		//todo load admin tag
		return user;
	}
	
	/**
	 * Get Program from database
	 * 
	 * @param code
	 * @param startDate
	 * @return
	 */
	public CourseProgram getProgram(String code, CourseDate startDate) {
		String query = "MATCH (courseProgram: CourseProgram {code: \"" + code + "\", "+ CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" }) ";
		query += "RETURN courseProgram";
		
		StatementResult result = this.communicator.readFromNeo(query);
		Record row = result.next();
		
		int readingPeriods = row.get("courseProgram").get("readingPeriods").asInt();
		CourseOrder courseOrder = new CourseOrder(readingPeriods);
		
		String inProgramQuery = "MATCH (courseProgram: CourseProgram {code: \"" + code + "\", "+ CourseLabels.YEAR + " : \"" + startDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + startDate.getPeriod() + "\" }) ";
		inProgramQuery += "MATCH(courseInProgram : Course)<-[relation: IN_PROGRAM]-(courseProgram) RETURN courseInProgram, relation";
		result = this.communicator.readFromNeo(inProgramQuery);
		
		while(result.hasNext()) {
			Record currentRow = result.next();
			Course course = createCourse(currentRow, "courseInProgram");
			courseOrder.setCourseAt(course, currentRow.get("relation").get("period").asInt(), currentRow.get("relation").get("pos").asInt());
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
	private CourseProgram createCourseProgram(CourseOrder courseOrder, Record row, String nodename) {
		
		String name = row.get(nodename).get("name").toString();
		String code = row.get(nodename).get("code").toString();
		String description = row.get(nodename).get("description").toString();
		String creds = row.get(nodename).get("credit").toString();
		Credits credits = Credits.valueOf(creds);
		int year = Integer.parseInt(row.get(nodename).get("year").toString());
		LP lp = LP.valueOf(row.get(nodename).get("lp").toString());
		CourseDate startDate = new CourseDate(year, lp);	
		
		CourseProgram courseProgram = new CourseProgram(courseOrder, name, code, description, startDate, credits);
		
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
		String query = "MATCH (course: Course {courseCode: \"" + courseCode + "\", "+ CourseLabels.YEAR + " : \"" + courseDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + courseDate.getPeriod() + "\" }) ";
		query += "RETURN course";
		
		StatementResult result = this.communicator.readFromNeo(query);
		Record row = result.next();
		
		Course course = createCourse(row, "course");
		
		String developedQuery = "MATCH (course: Course {courseCode: \"" + courseCode + "\", "+ CourseLabels.YEAR + " : \"" + courseDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + courseDate.getPeriod() + "\" }) ";
		developedQuery += "MATCH(developedKC : KC)<-[r: DEVELOPED]-(course) RETURN developedKC";
		result = this.communicator.readFromNeo(developedQuery);
		
		while(result.hasNext()) {
			course.setDevelopedKC(createKC(result.next(), "developedKC"));
		}
		
		String requiredQuery = "MATCH (course: Course {courseCode: \"" + courseCode + "\", "+ CourseLabels.YEAR + " : \"" + courseDate.getYear() + "\" , " + CourseLabels.LP + " : \"" + courseDate.getPeriod() + "\" }) ";
		developedQuery += "MATCH(requiredKC : KC)<-[r: DEVELOPED]-(course) RETURN requiredKC";
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
		int taxonomyLevel = row.get(nodename).get("taxonomyLevel").asInt();
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
		String creds = row.get(nodename).get("credit").toString();
		Credits credits = Credits.valueOf(creds);
		String description = row.get(nodename).get("description").toString();
		String examiner = row.get(nodename).get("examiner").toString();
		int year = Integer.parseInt(row.get(nodename).get("year").toString());
		LP lp = LP.valueOf(row.get(nodename).get("lp").toString());
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
		String query = "MATCH (node: Kc {name: \"" + name + "\", taxonomyLevel: \"" + taxonomyLevel + "\"}) RETURN node";
		
		StatementResult result = this.communicator.readFromNeo(query);
		
		Record row = result.next();

		
		KC kc = createKC(row, "node");
		return kc;
	}
		
	
}