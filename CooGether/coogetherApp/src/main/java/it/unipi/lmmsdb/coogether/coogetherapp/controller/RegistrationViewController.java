package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.utils.Utils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationViewController implements Initializable {

    @FXML private ImageView goBack;
    @FXML private TextField Fname;
    @FXML private TextField Lname;
    @FXML private TextField email;
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private TextField conPassword;
    @FXML private Button submit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBack.setOnMouseClicked(mouseEvent -> clickOnBackToChangePage(mouseEvent));
        submit.setOnMouseClicked(mouseEvent -> clickOnToRegister(mouseEvent));
    }

    private void clickOnToRegister(MouseEvent mouseEvent) {
        User user;
        // controlla parametri registrazione
        String firstName=Fname.getText();
        String lastName=Lname.getText();
        String em=email.getText();
        String userN=username.getText();
        String pass=password.getText();
        String pass2=conPassword.getText();

        String totalName=firstName+" "+lastName;

        //controllo email
        Pattern p=Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(em);
        Boolean matchFound=m.matches();

        if(!matchFound)
        {
            //email errata tornare alla pagina
        }

        //controllo se username è già presente
        user=Neo4jDriver.getInstance().getUsersFromUsername(userN);
        if(user!=null)
        {
            // username già esistente tornare alla pagina
        }

        // controllo uguaglianza password
        if(pass.compareTo(pass2)!=0)
        {
            //password non uguali tornare alla pagina
        }

        //tutto giusto fare la registrazione
    }

    private void clickOnBackToChangePage(MouseEvent mouseEvent) {
        //torna alla pagina precedente
    }
}
