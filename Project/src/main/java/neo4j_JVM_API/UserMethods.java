package neo4j_JVM_API;

import Data.*;
import Data.User.UserLables;
import neoCommunicator.Neo4jCommunicator;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.StatementResult;

import java.util.ArrayList;

public class UserMethods {

	private Neo4jCommunicator communicator;

	public UserMethods(Neo4jCommunicator communicator) {
		this.communicator = communicator;
	}

	public void addUser(User userObj) {
		String query = "CREATE (n:"+ User.UserLables.USER +"{";
		query +=    User.UserLables.USERNAME +": \""+userObj.getUsername() + "\", ";
		query += 	User.UserLables.PASSWORD +": \"" + userObj.getPassword() + "\", ";
		query += 	User.UserLables.USERTAG +": \"" + (userObj.isAdmintag()?1:0) + "\"";
		query += 	"})";
		communicator.writeToNeo(query);
	}

	/**
	 * Fetch and create a user object by username
	 * @param username selector
	 * @return
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
	 * Create Course same as one in get methods
	 * @param row
	 * @param nodename
	 * @return
	 */
	private Course createCourse(Record row, String nodename) {

		String name = row.get(nodename).get("name").toString();
		String courseCode = row.get(nodename).get("courseCode").toString();
		String creds = row.get(nodename).get("credit").toString();

		creds = creds.replaceAll("\"", "");
		Credits credits = Credits.valueOf(creds);

		String description = row.get(nodename).get("description").toString();
		String examiner = row.get(nodename).get("examiner").toString();
		int year = Integer.parseInt(row.get(nodename).get("year").toString().replaceAll("\"", ""));
		LP lp = LP.valueOf(row.get(nodename).get("lp").toString().replaceAll("\"", ""));
		CourseDate startDate = new CourseDate(year, lp);

		Course course = new Course(name, courseCode, credits, description, examiner, startDate);

		return course;

	}
	public void changeUserPrivileges(String username,boolean admin) {
		String query = "MATCH(n:User{"+ User.UserLables.USERNAME +":"+username+"}) SET n."+ User.UserLables.USERTAG +"="+(admin?1:0);
		communicator.writeToNeo(query);
	}
	public void changeUserPassword(String username,String pwd) {
		String query = "MATCH(n:User{"+ User.UserLables.USERNAME +":"+username+"}) SET n."+ User.UserLables.PASSWORD +"=" + Security.generateHash(pwd);
		communicator.writeToNeo(query);
	}
	public void removeUser(String username) {
		String query = "MATCH(n:User{"+ User.UserLables.USERNAME +":"+username+"}) DETACH DELETE n";
		communicator.writeToNeo(query);
	}

	/**
	 * Add a course to a user
	 * @author Johan RH
	 * @param user user object
	 * @param data course to match with the user
	 */
	public void addCourseToUser(User user,Course data) {
		user.addCourse(data);
		String query = "MATCH(n:User{"+ User.UserLables.USERNAME +":"+user.getUsername()+
				"}),(m:Course{"+
				Course.CourseLabels.CODE+":\""+ data.getCourseCode()+"\", "+
				Course.CourseLabels.YEAR +":"+data.getStartPeriod().getYear()+"," +
				Course.CourseLabels.LP +":\""+data.getStartPeriod().getPeriod().name()+"\"" +
				"}) CREATE (n)-[r:CAN_EDIT]->(m)";
		communicator.writeToNeo(query);
		user.addCourse(data);
	}
	
	/**
	 * 
	 * 	Delete a relationship between a user and a course
	 * 
	 * @param username
	 * @param courseCode
	 * @param courseDate
	 */
	public void deleteRelationShipBetweenUserAndCourse(String username, String courseCode, CourseDate courseDate) {
		
		String query = "MATCH (user: User { " + User.UserLables.USERNAME + ": \"" + username + "\" }) ";
		query += "MATCH (course: Course { " + Course.CourseLabels.CODE + ": \"" + courseCode + "\", " + Course.CourseLabels.LP + ": \"" + courseDate.getPeriod() + "\", " + Course.CourseLabels.YEAR + ": \"" + courseDate.getYear()+"\" })";
		query += "MATCH (user)-[r]-(course) DELETE r";
		
		communicator.writeToNeo(query);
		
	}
	
	

	/**
	 * Create a course that gives user write privilege, Does not create relations between kcs and course, condouct it to object and pass it to
	 * CreateRelation method in CreateMethods API
	 * @param user UserObject references User Privilege on compile time.
	 * @param data Course To commit.
	 */
	public void createCourseWithUser(User user,Course data) {
		user.addCourse(data);
		String query = "MATCH(n:User{" + User.UserLables.USERNAME + ":" + user.getUsername() +
				"})CREATE (n)-[r:CAN_EDIT]-> (m:Course{" +
				Course.CourseLabels.CODE + "=\"" + data.getCourseCode() + "\", " +
				Course.CourseLabels.YEAR + "=" + data.getStartPeriod().getYear() + "," +
				Course.CourseLabels.LP + "=\"" + data.getStartPeriod().getPeriod().name() + "\"" +
				Course.CourseLabels.NAME + "=\"" + data.getName() + "\"" +
				Course.CourseLabels.EXAMINER + "=\"" + data.getExaminer() + "\"" +
				Course.CourseLabels.CREDIT + "=\"" + data.getCredit() + "\"" +
				Course.CourseLabels.DESCRIPTION + "=\"" + data.getDescription() + "\"" +
				"})";
		communicator.writeToNeo(query);
		user.addCourse(data);
	}

	/**
	 * Login manager
	 * @param username Selector
	 * @param password Input to test authentication
	 * @return true if successful
	 * @author Johan RH
	 */
	public User login(String username, String password)  {
		String query = "MATCH(n:"+User.User+"{"+ User.UserLables.USERNAME +":\""+username+"\"} return n";
		StatementResult result = communicator.readFromNeo(query);
		if(!result.hasNext())
			return null;
		Record record = result.next();
		String pwd = record.get("n").get(User.UserLables.PASSWORD.toString()).toString();
		User u = getUser(record.get("n").get(User.UserLables.USERNAME.toString()).toString());
		if(pwd.equals(Security.generateHash(password))) {

			return u;
		}
		return null;
	}
	
	/**
	 * Made another Login method that not read in courses
	 * @param user containing username and hashed password
	 * @return Information for the logged in user, including admin tag
	 * 
	 *  @author JSPr
	 */
	public User login2(User user) {
		
		String query = "MATCH (user: " + UserLables.USER + " { "+ User.UserLables.USERNAME +": \"" + user.getUsername() + "\" }) RETURN user";
		
		StatementResult result = communicator.readFromNeo(query);
		if(result.hasNext()) {
			
			Record record = result.next();
			String password = record.get("user").get(User.UserLables.PASSWORD.toString()).toString().replaceAll("\"", "");
			String username = record.get("user").get(User.UserLables.USERNAME.toString()).toString().replaceAll("\"", "");
			
			int isAdmin = Integer.parseInt(record.get("user").get(User.UserLables.USERTAG.toString()).toString().replaceAll("\"", ""));
			
			User newUser = new User(username, password);
			newUser.setAdmintag(1 == isAdmin);
			
			
			if(user.CompareForLogin(newUser)) {
				return newUser;
			} else {
				return null;
			}
			
			
		} else {
			return null;
		}
		
		
	}

	/**
	 * 	Returns the courses related to a user based on the username
	 * 
	 * @param username
	 * @return Course[] with the courses that the user can edit
	 */
	public Course[] getUserCourses(String username) {
		String query = "MATCH (user: " + User.UserLables.USER + " {" + User.UserLables.USERNAME + ": \"" + username + "\" }) ";
		query += "MATCH (user)-[r: " + Relations.CAN_EDIT + "]->(course) RETURN course";
		StatementResult db = communicator.readFromNeo(query);
		
		ArrayList<Course> courses = new ArrayList<Course>();
		while(db.hasNext()){
			Course c = createCourse(db.next(), "course");
			courses.add(c);	
		}
		return courses.toArray(new Course[courses.size()]);
	}
}