module it.unipi.lmmsdb.coogether.coogetherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires com.google.gson;
    requires org.neo4j.driver;
    requires java.xml;
    requires com.fasterxml.jackson.databind;


    opens it.unipi.lmmsdb.coogether.coogetherapp to javafx.fxml;
    exports it.unipi.lmmsdb.coogether.coogetherapp;
    exports it.unipi.lmmsdb.coogether.coogetherapp.controller;
    exports it.unipi.lmmsdb.coogether.coogetherapp.pojo to com.fasterxml.jackson.databind;
    opens it.unipi.lmmsdb.coogether.coogetherapp.controller to javafx.fxml;
}