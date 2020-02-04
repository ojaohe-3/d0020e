package SpecificInterfaces;
import Data.*;
import neo4j_JVM_API.Neo4JAPI;

public class TeacherInterface extends UserInterface {

    protected final Data.User User;

    /**
     * TeacherInterface Teachers available functions
     * @author Johan RH
     * @param neoapi
     */
    public TeacherInterface(Neo4JAPI neoapi, User user) {
        super(neoapi);
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
                neoapi.modifyMethods.editCourse(courseCode,c);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * Search KC
     * @param field the attribute to search on
     * @param searchKey search key
     * @return
     */
    protected KC[] searchKC(KC.KCLabel field, String searchKey){
        return neoapi.filterMethods.filterKCByTag(field,searchKey);
    }
    private boolean hasWritePermission(Course course){
        for (Course o:User.getCourses()) {
            if(o.equals(course))
                return true;
        }
        return false;
    }
}
