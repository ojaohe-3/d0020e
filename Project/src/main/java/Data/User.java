package Data;
//package Data;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * todo set session to this object
 * @author Johan RH
 */
public class User {
    public static String User = "User";
    private String username;
    private boolean Admintag;
    private String password;
    private ArrayList<Course> courses;


    public User(String username, String password) {
        this.username = username;
        this.password =  Security.generateHash(password);;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public boolean isAdmintag() {
        return Admintag;
    }

    public void setAdmintag(boolean admintag) {
        Admintag = admintag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password =  Security.generateHash(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static enum UserLables {
        USERNAME("Username"),  USERTAG("Usertag"), PASSWORD("Password"),USER("User");
        private String name;

        private UserLables(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
