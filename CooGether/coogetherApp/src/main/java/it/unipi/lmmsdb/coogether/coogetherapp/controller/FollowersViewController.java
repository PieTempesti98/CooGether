package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.config.SessionUtils;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FollowersViewController  implements Initializable {
    private User logged = SessionUtils.getUserLogged();
    @FXML
    private VBox usersContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showUsers();
    }

    @FXML
    private void goBack(MouseEvent mouseEvent){
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
        Utils.changeScene("hello-view.fxml", ae);
    }

    private void showUsers() {
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        ArrayList<User> users;
        users= neo4j.getFollowerUsers(logged);

        for(User u: users) {
            VBox userBox = new VBox();

            Font bold = new Font("System Bold", 18);
            Font size = new Font(14);

            HBox containerU = new HBox();
            containerU.setSpacing(10);
            Label uname = new Label("Username:");
            uname.setFont(bold);
            Label userName = new Label(u.getUsername());
            userName.setFont(size);
            containerU.getChildren().addAll(uname, userName);
            userBox.getChildren().add(containerU);

            HBox containerName = new HBox();
            containerName.setSpacing(10);
            Label name = new Label("Full Name:");
            name.setFont(bold);
            Label fname = new Label(u.getFullName());
            fname.setFont(size);
            containerName.getChildren().addAll(name, fname);
            userBox.getChildren().add(containerName);

            HBox containerEmail = new HBox();
            containerEmail.setSpacing(10);
            Label em = new Label("E-mail:");
            em.setFont(bold);
            Label email = new Label(u.getEmail());
            email.setFont(size);
            containerEmail.getChildren().addAll(em, email);
            userBox.getChildren().add(containerEmail);

            //button per seguire u (se questo non è gia seguito)

            userBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #596cc2;");

            usersContainer.getChildren().add(userBox);

            /*if(logged!=null){
                //cerco se tra gli utenti mostrati ce ne sono seguiti
                if (!followedUsers.isEmpty()) {
                    for (User following : followedUsers) {
                        if (following.getUserId() == u.getUserId()) {
                            //aggiungo un button unfollow
                            createButtonUnfollow(logged.getUserId(), u.getUserId(), userBox);
                        } else {
                            //aggiungo un button follow
                            createButtonFollow(logged.getUserId(),u.getUserId(), userBox);
                        }
                    }
                }else{
                    //aggiungo un button follow
                    createButtonFollow(logged.getUserId(), u.getUserId(), userBox);
                }

            }else{
                //aggiungo sempre un button segui
                createButton( userBox);
            }

        }

        skip = skip +20;
        createShowMore();*/
        }
    }
}
