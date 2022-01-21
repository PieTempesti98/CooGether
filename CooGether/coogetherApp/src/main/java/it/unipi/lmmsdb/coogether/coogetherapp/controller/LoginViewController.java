package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.config.SessionUtils;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML private TextField email;
    @FXML private TextField password;
    @FXML private ImageView goBack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBack.setOnMouseClicked(mouseEvent -> goBack(mouseEvent));
    }

    private void showErrorAlert(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(s);
        alert.showAndWait();
    }

    @FXML
    private void goBack(MouseEvent mouseEvent) {
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
        Utils.changeScene("hello-view.fxml", ae);
    }

    @FXML private void signIn(MouseEvent mouseEvent){
        if(email.getText().isEmpty() || password.getText().isEmpty()){
            showErrorAlert("You should insert email and password");
        }else{
            Neo4jDriver neo4j = Neo4jDriver.getInstance();
            User u = neo4j.getUsersFromUnique(email.getText());

            if(u == null){
                showErrorAlert("user not found");
                return;
            }
            if(!u.getPassword().equals(password.getText())){
                showErrorAlert("Wrong password");
                return;
            }
            SessionUtils.setUserLogged(u);
            if(u.getRole() == 2){
                //code to the admin page
            }
            else{
                ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
                Utils.changeScene("users-view.fxml", ae);

            }
        }

    }

    @FXML private void signUp(MouseEvent mouseEvent){
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
        Utils.changeScene("registration-view.fxml", ae);
    }
}
