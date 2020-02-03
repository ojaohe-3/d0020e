package SpecificInterfaces;

import Data.User;
import neo4j_JVM_API.Neo4JAPI;

public class AdminInterface extends TeacherInterface {

    /**
     * Constructor
     *
     * @param neoapi
     */
    public AdminInterface(Neo4JAPI neoapi, Data.User user) {
        super(neoapi,user);
    }

}
