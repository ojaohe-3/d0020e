package neo4j_JVM_API;

import neoCommunicator.Neo4jCommunicator;

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
	
	private static final Neo4jCommunicator communicator = new Neo4jCommunicator("bolt://localhost:7687", "neo4j", "admin");;

	public static final UserMethods userMethods = new UserMethods(communicator);
	public static final GetMethods getMethods = new GetMethods(communicator);
	public static final CreateMethods createMethods = new CreateMethods(communicator);
	public static final ModifyMethods modifyMethods = new ModifyMethods(communicator);
	public static final FilterMethods filterMethods = new FilterMethods(communicator);

	public Neo4JAPI(String bolturl, String username, String pwd) {
	}
	
	
	/*public Neo4JAPI(String uri, String user, String password) {
		this.communicator = new Neo4jCommunicator(uri, user, password);
		this.getMethods = new GetMethods(communicator);
		this.createMethods = new CreateMethods(communicator);
		this.modifyMethods = new ModifyMethods(communicator);
		this.filterMethods = new FilterMethods(communicator);
		this.userMethods = new UserMethods(communicator);
	}*/
}