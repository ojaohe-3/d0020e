package Data;

import java.util.ArrayList;

/**
 * todo set session to this object
 * @author Johan RH
 */
public class User {
    private String username;
    private boolean Admintag;
    private String password;
    private ArrayList<Course> courses;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static enum UserLables {
        USERNAME("Username"),  USERTAG("Usertag"), PASSWORD("Password");
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
