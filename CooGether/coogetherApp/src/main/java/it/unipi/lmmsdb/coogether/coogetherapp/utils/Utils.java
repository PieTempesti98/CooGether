package it.unipi.lmmsdb.coogether.coogetherapp.utils;

import it.unipi.lmmsdb.coogether.coogetherapp.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Utils {
    static public void changeScene(String fxmlFile, ActionEvent event){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 900);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
