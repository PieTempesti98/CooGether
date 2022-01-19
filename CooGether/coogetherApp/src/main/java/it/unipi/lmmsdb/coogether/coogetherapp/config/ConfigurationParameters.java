package it.unipi.lmmsdb.coogether.coogetherapp.config;

/**
 * Class used to store the configuration parameters retrieved from the config.xml
 * There is no need to modify this value, so there are only the getters methods
 */

public class ConfigurationParameters {
    private String mongoFirstIp;
    private int mongoFirstPort;
    private String mongoSecondIp;
    private int mongoSecondPort;
    private String mongoThirdIp;
    private int mongoThirdPort;
    private String mongoUsername;
    private String mongoPassword;
    private String mongoDbName;
    private String neo4jIp;
    private int neo4jPort;
    private String neo4jUsername;
    private String neo4jPassword;

    public int getMongoFirstPort() {
        return mongoFirstPort;
    }

    public int getMongoSecondPort() {
        return mongoSecondPort;
    }

    public int getMongoThirdPort() {
        return mongoThirdPort;
    }

    public String getMongoFirstIp() {
        return mongoFirstIp;
    }

    public String getMongoSecondIp() {
        return mongoSecondIp;
    }

    public String getMongoThirdIp() {
        return mongoThirdIp;
    }

    public String getMongoUsername() {
        return mongoUsername;
    }

    public String getMongoPassword() {
        return mongoPassword;
    }

    public String getMongoDbName() {
        return mongoDbName;
    }

    public String getNeo4jIp() {
        return neo4jIp;
    }

    public int getNeo4jPort() {
        return neo4jPort;
    }

    public String getNeo4jUsername() {
        return neo4jUsername;
    }

    public String getNeo4jPassword() {
        return neo4jPassword;
    }
}
