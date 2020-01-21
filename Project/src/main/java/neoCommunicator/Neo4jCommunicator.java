package neoCommunicator;
import org.neo4j.driver.v1.*;


public class Neo4jCommunicator implements AutoCloseable {

    private final Driver driver;


    public Neo4jCommunicator( String uri, String user, String password ) {
        this.driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    	
    }

    public void close() throws Exception
    {
        driver.close();
    }
    
    
    /**
     *  Method used for writing to the DB
     * @param query
     */
    
    protected synchronized void writeToNeo(final String query) {
    	try ( Session session = driver.session() ) {
            session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    StatementResult result = tx.run(query);
                    
                    return result.single().get(0).asString();
                }
            });
        }
    }
    
    
    /**
     * 
     *  Method used for getting nodes from the DB
     * 
     * @param message The Cipher query to execute
     * @return Statement result containing the returned nodes from the query
     */
    protected synchronized StatementResult readFromNeo(final String query) {
    	
    	try ( Session session = driver.session() ) {
            StatementResult result = session.writeTransaction( new TransactionWork<StatementResult>() {
                @Override
                public StatementResult execute( Transaction tx ) {
                    StatementResult result = tx.run(query);
                    
                    return result;
                }
            });
            return result;
        } 
    	
    }
    
}