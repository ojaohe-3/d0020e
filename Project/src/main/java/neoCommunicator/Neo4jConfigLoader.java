package neoCommunicator;

import neo4j_JVM_API.Neo4JAPI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Load Configurations to communicate to Database
 * @author Johan RH
 */
public class Neo4jConfigLoader {
    private static String CONF_PATH = "db.properties";
    private static config CONF = null;
    /**
     * Generate a new API for every caller, will load every time
     * @return
     * @throws IOException
     */
    public static Neo4JAPI getApi() throws IOException {
        config c = loadConfig();
        Neo4JAPI api = new Neo4JAPI(c.getBOLTURL(),c.USERNAME,c.getPWD());
        return api;
    }

    /**
     * Load config from disk if it is not already loaded.
     * @return
     * @throws IOException
     */
    private static config loadConfig() throws IOException {
        if(CONF!=null)
            return CONF;

        String uname;
        String pwd;
        String url;
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            inputStream = Neo4jConfigLoader.class.getClassLoader().getResourceAsStream(CONF_PATH);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + CONF_PATH + "' not found in the classpath");
            }

            // get the property value and print it out
            uname  = prop.getProperty("username");
            pwd = prop.getProperty("psw");
            url = prop.getProperty("db_server");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return null;
        } finally {
            inputStream.close();
        }

        config temp = new config(uname,pwd,url);
        return temp;
    }

    /**
     * Struct
     * @author JohanRH
     */
    private static class config{
        private String USERNAME;
        private String PWD;
        private String BOLTURL;

        public config(String USERNAME, String PWD, String BOLTURL) {
            this.USERNAME = USERNAME;
            this.PWD = PWD;
            this.BOLTURL = BOLTURL;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }

        public String getPWD() {
            return PWD;
        }

        public void setPWD(String PWD) {
            this.PWD = PWD;
        }

        public String getBOLTURL() {
            return BOLTURL;
        }

        public void setBOLTURL(String BOLTURL) {
            this.BOLTURL = BOLTURL;
        }
    }
}
