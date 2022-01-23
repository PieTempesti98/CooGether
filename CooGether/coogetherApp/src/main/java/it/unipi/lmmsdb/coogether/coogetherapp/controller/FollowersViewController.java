package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.config.SessionUtils;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private ArrayList<User> followedUsers;
    @FXML
    private VBox usersContainer;
    @FXML private VBox userName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Label uName = new Label(logged.getUsername());
        Font bold = new Font("System Bold", 18);
        uName.setFont(bold);

        userName.getChildren().add(uName);
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
        followedUsers= neo4j.getFollowingUsers(logged);

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

            userBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #596cc2;");

            usersContainer.getChildren().add(userBox);

            if (!followedUsers.isEmpty()) {
                for (User following : followedUsers) {
                    if (following.getUserId() == u.getUserId()) {
                        //aggiungo un button unfollow
                        createButtonUnfollow(logged, u, userBox);
                    } else {
                        //aggiungo un button follow
                        createButtonFollow(logged,u, userBox);
                    }
                }
            }else{
                //aggiungo un button follow
                createButtonFollow(logged, u, userBox);
                }

        }
    }

    private void createButtonFollow(User a, User b, VBox box){
        Button follow= new Button();
        follow.setText("Follow");
        follow.setOnAction(actionEvent -> follow(a, b, box));
        box.getChildren().add(follow);
    }

    private void createButtonUnfollow(User a, User b, VBox box){
        Button unfollow= new Button();
        unfollow.setText("Unfollow");
        unfollow.setOnAction(actionEvent -> unfollow(a, b, box));
        box.getChildren().add(unfollow);
    }

    private void follow(User a, User b, VBox box){
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        if(neo4j.follow(a.getUserId(), b.getUserId())){
            Utils.showInfoAlert("User followed correctly");
            int i= a.getFollowing();
            a.setFollowing(i+1);
            int j = b.getFollowers();
            b.setFollowers(j+1);
            box.getChildren().remove(box.getChildren().size() - 1);
            createButtonUnfollow(a, b, box);
        }else
            Utils.showErrorAlert("Error in follow this user");

    }

    private void unfollow(User a,User b, VBox box){
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        if(neo4j.unfollow(a.getUserId(), b.getUserId())){
            Utils.showInfoAlert("User unfollowed correctly");
            int i= a.getFollowing();
            a.setFollowing(i-1);
            int j = b.getFollowers();
            b.setFollowers(j-1);
            box.getChildren().remove(box.getChildren().size()-1);
            createButtonFollow(a, b, box);
        }else
            Utils.showErrorAlert("Error in unfollow user");
    }

    @FXML
    private void log(MouseEvent mouseEvent) {
        //mostra i dati dello user se questo è loggato, altrimenti ad una pagina per fare il login
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
        User logged =SessionUtils.getUserLogged();
        if(logged==null){
            Utils.changeScene("login-view.fxml", ae);
        }else{
            Utils.changeScene("user-details-view.fxml", ae);
        }
    }
}
