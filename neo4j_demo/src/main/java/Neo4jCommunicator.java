import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;


class Neo4jCommunicator implements AutoCloseable {

    private final Driver driver;


    public Neo4jCommunicator( String uri, String user, String password ) {
        this.driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    	
    }

    public void close() throws Exception
    {
        driver.close();
    }
    
    protected synchronized void writeToNeo(final String message) {
    	try ( Session session = driver.session() ) {
            session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    StatementResult result = tx.run(message);
                    
                    return result.single().get(0).asString();
                }
            });
        }
    }
    
    
    /*
     * 
     *  Will need different return types or to let the caller create the return obj from a string
     * 
     */
    protected synchronized String readFromNeo(final String message) {
    	
    	try ( Session session = driver.session() ) {
            session.writeTransaction( new TransactionWork<String>() {
                @Override
                public String execute( Transaction tx ) {
                    StatementResult result = tx.run(message);
                    
                    return result.single().get(0).asString();
                }
            });
        }
		return "null :) readFromNeo() ";
    }
    
}