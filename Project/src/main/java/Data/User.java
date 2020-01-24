package Data;

/**
 * todo set session to this object
 * @author Johan RH
 */
public class User {
    private String username;
    private boolean Admintag;
    private String password;

    public User(String username, boolean admintag, String password) {
        this.username = username;
        Admintag = admintag;
        this.password = password;
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
}
