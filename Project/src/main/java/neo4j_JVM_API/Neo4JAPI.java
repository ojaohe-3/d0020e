package neo4j_JVM_API;

import neoCommunicator.Neo4jCommunicator;
import neoCommunicator.Neo4jConfigLoader;

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


	
	public final UserMethods userMethods;
	public final GetMethods getMethods;
	public final CreateMethods createMethods;
	public final ModifyMethods modifyMethods;
	public final FilterMethods filterMethods;
	public final DeleteMethods deleteMethods;

	/**
	 * Creates instances for the the attributes with the same communicator object
	 * @param communicator All methods will use this communicator object
	 */
	public Neo4JAPI(Neo4jCommunicator communicator) {
		this.getMethods = new GetMethods(communicator);
		this.createMethods = new CreateMethods(communicator);
		this.modifyMethods = new ModifyMethods(communicator);
		this.filterMethods = new FilterMethods(communicator);
		this.userMethods = new UserMethods(communicator);
		this.deleteMethods = new DeleteMethods(communicator);
	}
}