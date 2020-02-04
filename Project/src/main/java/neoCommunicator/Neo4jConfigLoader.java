package neoCommunicator;

import neo4j_JVM_API.Neo4JAPI;

/**
 * Load Configurations to communicate to Database
 * @author Johan RH
 */
public class Neo4jConfigLoader {
    private final String CONF_PATH = "db.properties";
    public static Neo4JAPI getApi(){
        config c = loadConfig();
        Neo4JAPI api = new Neo4JAPI(c.getBOLTURL(),c.USERNAME,c.getPWD());
        return api;
    }
    private static config loadConfig(){
        String uname;
        String pwd;
        String url;



        config temp = new config(uname,pwd,url);
        return temp;
    }

    /**
     * Struct
     * @author JohanRH
     */
    private class config{
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
