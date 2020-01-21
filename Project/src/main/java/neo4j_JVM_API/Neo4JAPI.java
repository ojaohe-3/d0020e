package neo4j_JVM_API;


/**
 * <p>
 * This is the class used for creating, editing and deleting nodes in the 
 * database. Please use this instead of your own half-baked solution for Neo4J
 * communications.
 * </p>
 * @author Robin
 *
 */
public class Neo4JAPI {
	private final Neo4jCommunicator communicator;
	
	
	public Neo4JAPI(String uri, String user, String password) {
		this.communicator = new Neo4jCommunicator(uri, user, password);
	}
	
	public void CreateCourse() {
		
	}
	
	public void createKC () {
		
	}
	
	public void createKCgroup() {
		
	}
	
	public void createProgram() {
		
	}
	
	public void removeCourse() {
		
	}
	
	public void removeKCtaxonomy() {
		
	}
	
	public void removeKCByName() {
		
	}
	
	public void removeProgram() {
		
	}
	
	public void editCourse() {
		
	}
	
	public void editKCWithTaxonomyLevel() {
		
	}
	
	public void editProgram() {
		
	}
	
	public void getCourse() {
		
	}
	
	public void getKCwithTaxonomyLevel() {
		
	}
	
	public String getProgram() {
		return "";
	}
	
	public void addUser() {
		
	}
	
	public void removeUser() {
		
	}
	
	public void changeUserPrivileges() {
		
	}
	
	public String getUser() {
		return "";
	}
	
	public boolean login() {
		return false;
	}
	
	public void addTopic() {
		
	}
	
	public void removeTopic() {
		
	}
	
	public String[] getTopics() {
		return null;
	}
	
	
}

