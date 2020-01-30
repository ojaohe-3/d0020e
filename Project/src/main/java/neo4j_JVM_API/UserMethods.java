
public class UserMethods {
	
	public void addUser(User userObj) {
		String query = "CREATE (n:"+ User.UserLables.USER +"{";
		query +=    User.UserLables.USERNAME +":"+userObj.getUsername();
		query += 	User.UserLables.PASSWORD +":" + userObj.getPassword() ;
		query += 	User.UserLables.USERTAG +":" + (userObj.isAdmintag()?1:0) ;
		query += 	"})";
		communicator.writeToNeo(query);
	}
	
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
	
	public void changeUserPrivileges(String username,boolean admin) {
		String query = "MATCH(n:User{"+ User.UserLables.USERNAME +":"+username+"}) SET n."+ User.UserLables.USERTAG +"="+(admin?1:0);
		communicator.writeToNeo(query);
	}
	public void changeUserPassword(String username,String pwd) throws NoSuchAlgorithmException {
		String query = "MATCH(n:User{"+ User.UserLables.USERNAME +":"+username+"}) SET n."+ User.UserLables.PASSWORD +"=" + Security.generateHash(pwd);
		communicator.writeToNeo(query);
	}
	public void removeUser(String username) {
		String query = "MATCH(n:User{"+ User.UserLables.USERNAME +":"+username+"}) DETACH DELETE n";
		communicator.writeToNeo(query);
	}
	
	public void addCourseToUser(User user,Course data) {

		String query = "MATCH(n:User{"+ User.UserLables.USERNAME +":"+user.getUsername()+
				"}),(m:Course{"+
				Course.CourseLabels.CODE+":\""+ data.getCourseCode()+"\", "+
				Course.CourseLabels.YEAR +":"+data.getStartPeriod().getYear()+"," +
				Course.CourseLabels.LP +":\""+data.getStartPeriod().getPeriod().name()+"\"" +
				"}) CREATE (n)-[r:CAN_EDIT]->(m)";
		communicator.writeToNeo(query);
		user.addCourse(data);
	}
}