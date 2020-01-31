package SpecificInterfaces;

import Data.Course;
import Data.CourseDate;
import Data.User;
import neo4j_JVM_API.Neo4JAPI;

public class TeacherInterface extends UserInterface {

    protected final Data.User User;

    /**
     * TeacherInterface
     * @author Johan RH
     * @param neoapi
     */
    public TeacherInterface(Neo4JAPI neoapi, User user) {
        super(neoapi);
        this.User = user;
    }

    public boolean createCourse(Course c){
        try {

            this.neoapi.createMethods.createCourse(c);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean editCourse(Course c, String courseCode, CourseDate startperiod){
        try {

            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
