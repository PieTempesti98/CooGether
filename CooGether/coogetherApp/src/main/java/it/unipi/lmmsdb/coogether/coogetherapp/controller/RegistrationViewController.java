package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.config.SessionUtils;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.MongoDBDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationViewController {

    @FXML private TextField fName;
    @FXML private TextField lName;
    @FXML private TextField email;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private PasswordField conPassword;

    @FXML
    private void register(ActionEvent actionEvent) {
        String firstName=fName.getText();
        String lastName=lName.getText();
        String em=email.getText();
        String userN=username.getText();
        String pass=password.getText();
        String pass2=conPassword.getText();

        //empty field control
        if(firstName.equals("") || lastName.equals("") || em.equals("") || userN.equals("") || pass.equals("") || pass2.equals("")){
            Utils.showErrorAlert("Fill in all fields");
            return;
        }

        //email check
        Pattern p=Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(em);

        if(!m.matches()) {
            //show the error message
            Utils.showErrorAlert("incorrect email");
            return;
        }

        if(!pass.equals(pass2)){
            //show the error message
            Utils.showErrorAlert("Passwords do not match");
            return;
        }

        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        if(neo4j.getUsersFromUnique(userN) != null || neo4j.getUsersFromUnique(em) != null){
            //show the error message
            Utils.showErrorAlert("username or email already exist");
            return;
        }
        int newId = MongoDBDriver.getMaxUserId() + 1;
        User user = new User(newId, userN, (firstName + " " + lastName), pass, em);
        if(!neo4j.addUser(user)) {
            Utils.showErrorAlert("User entered incorrectly");
            return;
        }
        MongoDBDriver.setMaxUserId(user.getUserId());
        Utils.showInfoAlert("User succesfully added");
        SessionUtils.setUserLogged(user);
        Utils.changeScene("user-details-view.fxml", actionEvent);


    }

    @FXML
    public void goBack(MouseEvent mouseEvent) {
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
        Utils.changeScene("hello-view.fxml", ae);
    }
}
