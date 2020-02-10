package SpecificInterfaces;

import Data.CourseProgram;
import Data.KC;
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

    public boolean addProgram(CourseProgram data){
        try {
            neoapi.createMethods.createProgram(data);
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean addKC(KC data){
        try {
            neoapi.createMethods.createKC(data);
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    @Deprecated
    public boolean addProgramSpecialization(){

        try {
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


}
