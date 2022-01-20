package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.config.SessionUtils;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.internal.value.StringValue;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailsViewController implements Initializable {

    @FXML private VBox userInfoBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = SessionUtils.getUserLogged();

        Font bold = new Font("System Bold", 18);
        Font size = new Font(14);

        HBox containerName = new HBox();
        Label name = new Label("Full Name:");
        name.setFont(bold);
        Label fname = new Label(user.getFullName());
        fname.setFont(size);
        containerName.getChildren().addAll(name, fname);
        userInfoBox.getChildren().add(containerName);

        HBox containerU = new HBox();
        Label uname = new Label("Username:");
        uname.setFont(bold);
        Label userName = new Label(user.getUsername());
        userName.setFont(size);
        containerU.getChildren().addAll(uname, userName);
        userInfoBox.getChildren().add(containerU);

        HBox containerEmail = new HBox();
        Label em= new Label("E-mail:");
        em.setFont(bold);
        Label email = new Label(user.getEmail());
        email.setFont(size);
        containerEmail.getChildren().addAll(em, email);
        userInfoBox.getChildren().add(containerEmail);

        HBox roleContainer= new HBox();
        Label role = new Label("Role:");
        role.setFont(bold);
        String r;
        if (user.getRole()== 1)
            r= "Admin";
        else
            r= "Normal User";
        Label rol = new Label(r);
        rol.setFont(size);
        roleContainer.getChildren().addAll(role, rol);
        userInfoBox.getChildren().add(roleContainer);

        HBox followerContainer = new HBox();
        Label follower= new Label("Follower:");
        follower.setFont(bold);
        Label fol= new Label(String.valueOf( user.getFollowers()));
        fol.setFont(size);
        followerContainer.getChildren().addAll(follower, fol);
        userInfoBox.getChildren().add(followerContainer);

        HBox followingContainer = new HBox();
        Label following= new Label("Following:");
        following.setFont(bold);
        Label foll= new Label(String.valueOf( user.getFollowing()));
        foll.setFont(size);
        followerContainer.getChildren().addAll(following, foll);
        userInfoBox.getChildren().add(followingContainer);
    }

    @FXML
    private void changePassword (ActionEvent actionEvent){

    }
}
