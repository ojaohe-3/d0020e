package SpecificInterfaces;
import Data.*;
import neo4j_JVM_API.Neo4JAPI;

public class TeacherInterface extends UserInterface {

    protected final Data.User User;

    /**
     * TeacherInterface Teachers available functions
     * @author Johan RH
     */
    public TeacherInterface(User user) {
        super();
        this.User = user;
    }

    /**
     * Final commit to be written in the database
     * @param c course to create
     * @return
     */
    protected boolean createCourse(Course c){
        try {
            this.neoapi.userMethods.createCourseWithUser(User,c);
            this.neoapi.createMethods.createCourseKCrelation(c);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Edit an already committed course object, User passed must have correct privilege
     * @param courseCode selector statement
     * @param startperiod period
     * @return
     */
    protected boolean editCourse(String courseCode, CourseDate startperiod){
        try {
            Course c = neoapi.getMethods.getCourse(courseCode,startperiod);
            if(hasWritePermission(c))
                neoapi.modifyMethods.editCourse(courseCode,startperiod,c);
            return true;
        }catch (Exception e){
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
