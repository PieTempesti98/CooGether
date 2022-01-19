package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML private Button signIn;
    @FXML private Button signUp;
    @FXML private TextField email;
    @FXML private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
