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
	
	private final Neo4jCommunicator communicator;
	
	public GetMethods getMethods;
	public SetMethods setMethods;
	public ModifyMethods modifyMethods;
	
	
	public Neo4JAPI(String uri, String user, String password) {
		this.communicator = new Neo4jCommunicator(uri, user, password);
		this.getMethods = new GetMethods(communicator);
		this.setMethods = new SetMethods(communicator);
		this.modifyMethods = new ModifyMethods(communicator);
	}
	
	
}
