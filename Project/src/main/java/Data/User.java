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
        private static String[] asStrings;
        private String name;

        static
        {
            asStrings = new String[KC.KCLabel.values().length];
            for (int i = 0; i < KC.KCLabel.values().length; i++) {
                asStrings[i] = KC.KCLabel.values()[i].toString();
            }
        }
        private UserLables(String name) {
            this.name = name;
        }


        /**
         * <b>The items of the returned array can be altered, but that is highly prohibited and
         * will mess up your code.</b>
         * @return Returns all the possible values as an array in the following order:
         * <ul>
         * <li>Username</li>
         * <li>Role tag</li>
         * <li>Password</li>
         *
         * </ul>
         */
        public static String[] asStringArray() {
            return asStrings;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
