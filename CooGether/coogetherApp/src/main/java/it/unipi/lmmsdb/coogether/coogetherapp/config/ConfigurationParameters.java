package it.unipi.lmmsdb.coogether.coogetherapp.config;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * Class used to store the configuration parameters retrieved from the config.xml
 * There is no need to modify this value, so there are only the getters methods
 */

public class ConfigurationParameters {
    private static String mongoFirstIp;
    private static String mongoFirstPort;
    private static String mongoSecondIp;
    private static String mongoSecondPort;
    private static String mongoThirdIp;
    private static String mongoThirdPort;
    private static String mongoUsername;
    private static String mongoPassword;
    private static String mongoDbName;
    private static String neo4jIp;
    private static String neo4jPort;
    private static String neo4jUsername;
    private static String neo4jPassword;

    static {


        try {
            // creating a constructor of file class and
            // parsing an XML file
            File file = new File("CooGether/coogetherApp/src/main/resources/config/config.xml");


            // Defines a factory API that enables
            // applications to obtain a parser that produces
            // DOM object trees from XML documents.
            DocumentBuilderFactory dbf
                    = DocumentBuilderFactory.newInstance();

            // we are creating an object of builder to parse
            // the xml file.
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

			/*here normalize method Puts all Text nodes in
			the full depth of the sub-tree underneath this
			Node, including attribute nodes, into a "normal"
			form where only structure separates
			Text nodes, i.e., there are neither adjacent
			Text nodes nor empty Text nodes. */
            doc.getDocumentElement().normalize();

            mongoDbName = doc.getElementsByTagName("mongoDbName").item(0).getTextContent();
            mongoFirstIp = doc.getElementsByTagName("mongoFirstIp").item(0).getTextContent();
            mongoFirstPort = doc.getElementsByTagName("mongoFirstPort").item(0).getTextContent();
            mongoSecondIp = doc.getElementsByTagName("mongoSecondIp").item(0).getTextContent();
            mongoSecondPort = doc.getElementsByTagName("mongoSecondPort").item(0).getTextContent();
            mongoThirdIp = doc.getElementsByTagName("mongoThirdIp").item(0).getTextContent();
            mongoThirdPort = doc.getElementsByTagName("mongoThirdPort").item(0).getTextContent();
            mongoUsername = doc.getElementsByTagName("mongoUsername").item(0).getTextContent();
            mongoPassword = doc.getElementsByTagName("mongoPassword").item(0).getTextContent();
            neo4jIp = doc.getElementsByTagName("neo4jIp").item(0).getTextContent();
            neo4jPassword = doc.getElementsByTagName("neo4jPassword").item(0).getTextContent();
            neo4jPort = doc.getElementsByTagName("neo4jPort").item(0).getTextContent();
            neo4jUsername = doc.getElementsByTagName("neo4jUsername").item(0).getTextContent();

        }

        // This exception block catches all the exception
        // raised.
        // For example if we try to access a element by a
        // TagName that is not there in the XML etc.
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public static String getMongoFirstIp() {
        return mongoFirstIp;
    }

    public static void setMongoFirstIp(String mongoFirstIp) {
        ConfigurationParameters.mongoFirstIp = mongoFirstIp;
    }

    public static String getMongoFirstPort() {
        return mongoFirstPort;
    }

    public static void setMongoFirstPort(String mongoFirstPort) {
        ConfigurationParameters.mongoFirstPort = mongoFirstPort;
    }

    public static String getMongoSecondIp() {
        return mongoSecondIp;
    }

    public static void setMongoSecondIp(String mongoSecondIp) {
        ConfigurationParameters.mongoSecondIp = mongoSecondIp;
    }

    public static String getMongoSecondPort() {
        return mongoSecondPort;
    }

    public static void setMongoSecondPort(String mongoSecondPort) {
        ConfigurationParameters.mongoSecondPort = mongoSecondPort;
    }

    public static String getMongoThirdIp() {
        return mongoThirdIp;
    }

    public static void setMongoThirdIp(String mongoThirdIp) {
        ConfigurationParameters.mongoThirdIp = mongoThirdIp;
    }

    public static String getMongoThirdPort() {
        return mongoThirdPort;
    }

    public static void setMongoThirdPort(String mongoThirdPort) {
        ConfigurationParameters.mongoThirdPort = mongoThirdPort;
    }

    public static String getMongoUsername() {
        return mongoUsername;
    }

    public static void setMongoUsername(String mongoUsername) {
        ConfigurationParameters.mongoUsername = mongoUsername;
    }

    public static String getMongoPassword() {
        return mongoPassword;
    }

    public static void setMongoPassword(String mongoPassword) {
        ConfigurationParameters.mongoPassword = mongoPassword;
    }

    public static String getMongoDbName() {
        return mongoDbName;
    }

    public static void setMongoDbName(String mongoDbName) {
        ConfigurationParameters.mongoDbName = mongoDbName;
    }

    public static String getNeo4jIp() {
        return neo4jIp;
    }

    public static void setNeo4jIp(String neo4jIp) {
        ConfigurationParameters.neo4jIp = neo4jIp;
    }

    public static String getNeo4jPort() {
        return neo4jPort;
    }

    public static void setNeo4jPort(String neo4jPort) {
        ConfigurationParameters.neo4jPort = neo4jPort;
    }

    public static String getNeo4jUsername() {
        return neo4jUsername;
    }

    public static void setNeo4jUsername(String neo4jUsername) {
        ConfigurationParameters.neo4jUsername = neo4jUsername;
    }

    public static String getNeo4jPassword() {
        return neo4jPassword;
    }

    public static void setNeo4jPassword(String neo4jPassword) {
        ConfigurationParameters.neo4jPassword = neo4jPassword;
    }
}
