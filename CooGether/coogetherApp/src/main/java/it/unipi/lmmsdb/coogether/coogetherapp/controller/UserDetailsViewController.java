package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.config.SessionUtils;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
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
    @FXML private PasswordField newPass;
    @FXML private PasswordField confPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = SessionUtils.getUserLogged();

        Font bold = new Font("System Bold", 18);
        Font size = new Font(14);

        HBox containerName = new HBox();
        containerName.setSpacing(10);
        Label name = new Label("Full Name:");
        name.setFont(bold);
        Label fname = new Label(user.getFullName());
        fname.setFont(size);
        containerName.getChildren().addAll(name, fname);
        userInfoBox.getChildren().add(containerName);

        HBox containerU = new HBox();
        containerU.setSpacing(10);
        Label uname = new Label("Username:");
        uname.setFont(bold);
        Label userName = new Label(user.getUsername());
        userName.setFont(size);
        containerU.getChildren().addAll(uname, userName);
        userInfoBox.getChildren().add(containerU);

        HBox containerEmail = new HBox();
        containerEmail.setSpacing(10);
        Label em= new Label("E-mail:");
        em.setFont(bold);
        Label email = new Label(user.getEmail());
        email.setFont(size);
        containerEmail.getChildren().addAll(em, email);
        userInfoBox.getChildren().add(containerEmail);

        HBox roleContainer= new HBox();
        roleContainer.setSpacing(10);
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
        followerContainer.setAlignment(Pos.CENTER);
        followerContainer.setSpacing(10);
        Label follower= new Label("Follower:");
        follower.setFont(bold);
        Label fol= new Label(String.valueOf( user.getFollowers()));
        fol.setFont(size);
        followerContainer.getChildren().addAll(follower, fol);
        if(user.getFollowers()!=0) {
            followerContainer.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #596cc2;");
            followerContainer.setOnMouseClicked(mouseEvent -> showFollower(mouseEvent));
        }
        userInfoBox.getChildren().add(followerContainer);

        HBox followingContainer = new HBox();
        followingContainer.setAlignment(Pos.CENTER);
        followingContainer.setSpacing(10);
        Label following= new Label("Following:");
        following.setFont(bold);
        Label foll= new Label(String.valueOf( user.getFollowing()));
        foll.setFont(size);
        followingContainer.getChildren().addAll(following, foll);
        if(user.getFollowing()!=0) {
            followingContainer.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #596cc2;");
            followingContainer.setOnMouseClicked(mouseEvent -> showFollowing(mouseEvent));
        }
        userInfoBox.getChildren().add(followingContainer);

    }

    private void showFollower(MouseEvent mouseEvent){
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
        Utils.changeScene("followers-view.fxml", ae);
    }

    private void showFollowing(MouseEvent mouseEvent){
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
        Utils.changeScene("following-view.fxml", ae);
    }

    @FXML
    private void changePassword (ActionEvent actionEvent){
        if(newPass.getText().equals("") || confPass.getText().equals("")){
            Utils.showErrorAlert("Please fill all the fields");
            return;
        }
        if(!newPass.getText().equals(confPass.getText())){
            Utils.showErrorAlert("Password and confirmation don't match");
            return;
        }
        SessionUtils.getUserLogged().setPassword(newPass.getText());
        Neo4jDriver.getInstance().updateUser(SessionUtils.getUserLogged());
        Utils.showInfoAlert("Password successfully updated");

    }

    @FXML
    private void deleteAccount (ActionEvent actionEvent){
        if(Utils.deleteAccount(SessionUtils.getUserLogged())) {
            System.out.println("User successfully deleted");
            SessionUtils.logout();
            Utils.changeScene("hello-view.fxml", actionEvent);
        }
        else
            Utils.showErrorAlert("There was an error, retry");
    }

    @FXML
    private void goBack(MouseEvent mouseEvent){
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
        Utils.changeScene("hello-view.fxml", ae);
    }

    @FXML
    public void addRecipe(ActionEvent actionEvent) {
        Utils.changeScene("add-recipe.fxml", actionEvent);
    }
}
