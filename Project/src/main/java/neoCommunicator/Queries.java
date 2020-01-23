package neoCommunicator;

public class Queries {
	
	GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();
	
	GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabase(
			  new File("data/cars"));

	public void createNode() {
		//graphDb.beginTx();
		Result result = graphDb.execute(

		CREATE (courses:Company {name:[0], credit:cou )
	}
	
	public void matchNodes() {
		
	}
	//"130.240.5.138"
}
