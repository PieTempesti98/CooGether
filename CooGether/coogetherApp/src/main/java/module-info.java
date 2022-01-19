module it.unipi.lmmsdb.coogether.coogetherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;
    requires com.google.gson;
    requires org.neo4j.driver;


    opens it.unipi.lmmsdb.coogether.coogetherapp to javafx.fxml;
    exports it.unipi.lmmsdb.coogether.coogetherapp;
    exports it.unipi.lmmsdb.coogether.coogetherapp.controller;
    opens it.unipi.lmmsdb.coogether.coogetherapp.controller to javafx.fxml;
}