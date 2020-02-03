package SpecificInterfaces;

import Data.Course;
import Data.CourseDate;
import Data.CourseProgram;
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

    /**
     * Final commit to be writen in the database
     * @param c course to create
     * @return
     */
    protected boolean createCourse(Course c){
        try {
            this.neoapi.userMethods.createCourseWithUser(User,c);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Edit an already commited course object, User passed must have correct privilege
     * @param courseCode selector statment
     * @param startperiod period
     * @return
     */
    protected boolean editCourse(String courseCode, CourseDate startperiod){
        try {
            Course c = neoapi.getMethods.getCourse(courseCode,startperiod);
            if(hasWritePermission(c))
                neoapi.modifyMethods.editCourse(courseCode,c);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    protected boolean searchProgram(){
        try {
            neoapi.createMethods.createProgram(data);
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    protected boolean searchKC(){
        try {
            neoapi.filterMethods.;
            return true;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean hasWritePermission(Course course){
        for (Course o:User.getCourses()) {
            if(o.equals(course))
                return true;
        }
        return false;
    }
}
