package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.config.SessionUtils;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.MongoDBDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersViewController implements Initializable {

    private int skip = 0;
    private ArrayList<User> followedUsers;
    Neo4jDriver neo4j = Neo4jDriver.getInstance();

    @FXML private VBox usersContainer;
    @FXML private TextField filterFullName;
    @FXML private TextField filterUsername;
    @FXML private ChoiceBox ChoseAnalytics;
    @FXML private ChoiceBox KAnalytics;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersContainer.getChildren().clear();
        ArrayList<User> users;
        users= neo4j.getUsers(skip, 20);
        int more=1;
        showUsers(users, more);
    }

    private void showUsers(ArrayList<User> users, int more) {

        User logged= SessionUtils.getUserLogged();

        if(logged!=null){
            followedUsers= neo4j.getFollowingUsers(logged);
        }

        for(User u: users){
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
            Label em= new Label("E-mail:");
            em.setFont(bold);
            Label email = new Label(u.getEmail());
            email.setFont(size);
            containerEmail.getChildren().addAll(em, email);
            userBox.getChildren().add(containerEmail);

            userBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #596cc2;");

            usersContainer.getChildren().add(userBox);

            if(logged!=null){
                //cerco se tra gli utenti mostrati ce ne sono seguiti
                if (!followedUsers.isEmpty()) {
                    Boolean find=false;
                    for (User following : followedUsers) {
                        if (following.getUserId() == u.getUserId()) {
                            find=true;
                            break;
                        }
                    }
                    if(find)
                    {
                        //aggiungo un button unfollow
                        createButtonUnfollow(logged.getUserId(), u.getUserId(), userBox);
                    }
                    else
                    {
                        //aggiungo un button follow
                        createButtonFollow(logged.getUserId(),u.getUserId(), userBox);
                    }

                }else{
                    //aggiungo un button follow
                    createButtonFollow(logged.getUserId(), u.getUserId(), userBox);
                }

            }else{
                //aggiungo sempre un button segui
                createButton( userBox);
            }

            if(logged!=null) {
                if (logged.getRole() == 1) {
                    createButtonDelete(u, userBox);
                    createButtonAdmin(u, userBox);
                }
            }
        }

        if(more==1){
            skip = skip +20;
            createShowMore();
        }

    }

    private void createButtonAdmin(User u, VBox box){
        Button admin= new Button();
        admin.setText("Make Admin");
        admin.setOnAction(actionEvent -> makeAdmin(u, box));
        box.getChildren().add(admin);
    }

    private void makeAdmin(User u, VBox box){
        Neo4jDriver neo4j= Neo4jDriver.getInstance();
        if(neo4j.makeAdmin(u)){
            Utils.showInfoAlert("User "+ u.getUsername() + " is an admin now");
            box.getChildren().remove(box.getChildren().size() - 1);
            createButtonNotAdmin(u, box);
        }else
            Utils.showErrorAlert("Error in make this user admin");

    }

    private void createButtonNotAdmin(User u, VBox box){
        Button admin= new Button();
        admin.setText("Make Not Admin");
        admin.setOnAction(actionEvent -> makeNotAdmin(u, box));
        box.getChildren().add(admin);
    }

    private void makeNotAdmin(User u, VBox box){
        Neo4jDriver neo4j= Neo4jDriver.getInstance();
        if(neo4j.makeNotAdmin(u)){
            Utils.showInfoAlert("User "+ u.getUsername() + " is not an admin now");
            box.getChildren().remove(box.getChildren().size() - 1);
            createButtonAdmin(u, box);
        }else
            Utils.showErrorAlert("Error in make this user not admin");

    }

    private void createButtonDelete(User u, VBox box){
        Button delete= new Button();
        delete.setText("Delete user");
        delete.setOnAction(actionEvent -> delete(u, box));
        box.getChildren().add(delete);
    }

    private void delete(User u, VBox box){
        Neo4jDriver neo4j= Neo4jDriver.getInstance();
        if(neo4j.deleteUser(u)){
            Utils.showInfoAlert("User followed correctly");
            ArrayList<User> users;
            users= neo4j.getUsers(skip, 20);
            int more=1;
            showUsers(users, 1);
        }else
            Utils.showErrorAlert("Error in delete this user");

    }

    private void createButton(VBox box){
        Button follow= new Button();
        follow.setText("Follow");
        follow.setOnAction(actionEvent -> loginPage(actionEvent));
        box.getChildren().add(follow);
    }

    private void createButtonFollow(int a, int b, VBox box){
        Button follow= new Button();
        follow.setText("Follow");
        follow.setOnAction(actionEvent -> follow(a, b, box));
        box.getChildren().add(follow);
    }

    private void createButtonUnfollow(int a, int b, VBox box){
        Button unfollow= new Button();
        unfollow.setText("Unfollow");
        unfollow.setOnAction(actionEvent -> unfollow(a, b, box));
        box.getChildren().add(unfollow);
    }

    private void follow(int a, int b, VBox box){
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        if(neo4j.follow(a, b)){
            Utils.showInfoAlert("User followed correctly");
            box.getChildren().remove(box.getChildren().size() - 1);
            createButtonUnfollow(a, b, box);
        }else
            Utils.showErrorAlert("Error in follow this user");

    }

    private void unfollow(int a, int b, VBox box){
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        if(neo4j.unfollow(a, b)){
            Utils.showInfoAlert("User unfollowed correctly");
            box.getChildren().remove(box.getChildren().size()-1);
            createButtonFollow(a, b, box);
        }else
            Utils.showErrorAlert("Error in unfollow user");

    }

    private void loginPage (ActionEvent ae){
        Utils.changeScene("login-view.fxml", ae);
        Utils.showInfoAlert("You should login before follow someone!");
    }

    private void createShowMore() {
        Button showMore = new Button("Show more");
        showMore.setOnAction(actionEvent -> showMoreUsers());
        usersContainer.getChildren().add(showMore);
    }

    private void showMoreUsers() {
        usersContainer.getChildren().remove(usersContainer.getChildren().size() - 1);
        ArrayList<User> users;
        users= neo4j.getUsers(skip, 20);
        showUsers(users, 1);
    }

    @FXML
    private void goBack(MouseEvent mouseEvent){
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
        Utils.changeScene("hello-view.fxml", ae);
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

    public void filterFunction(ActionEvent actionEvent) {
        String name = filterFullName.getText();
        String username = filterUsername.getText();

        if(name.equals("") && username.equals("")){
            Utils.showErrorAlert("No filter inserted!");
        }else if (!name.equals("")){
            //get users by fullname
            ArrayList<User>users = neo4j.getUsersFromFullname(name);
            if(users.isEmpty()){
                Utils.showErrorAlert("No users find");
            }else{
                showFilteredUsers(users);
            }
        }else if(!username.equals("")){
            //get user by username
            User u= neo4j.getUsersFromUnique(username);
            ArrayList<User> users= new ArrayList();
            users.add(u);
            if(u==null)
                Utils.showErrorAlert("No users find");
            else
                showFilteredUsers(users);
        }else{
            Utils.showInfoAlert("Choose one filter between Username and Full Name");
        }
    }

    private void showFilteredUsers(ArrayList<User> users){
        usersContainer.getChildren().clear();
        showUsers(users, 0);
    }

    public void AnalyticsFunction(ActionEvent actionEvent) {
        String typeAnalytics=(String) ChoseAnalytics.getValue();
        String kAnalytics=(String) KAnalytics.getValue();

        if(!typeAnalytics.equals("Select") && !kAnalytics.equals("How many"))
        {
            if(typeAnalytics.equals("Top active users")){
                ArrayList<User> users= neo4j.getMostActiveUsers(Integer.parseInt(kAnalytics));
                showFilteredUsers(users);
            }else if(typeAnalytics.equals("Top followed users")){
                ArrayList<User> users =neo4j.mostFollowedUsers(Integer.parseInt(kAnalytics));
                showFilteredUsers(users);
            }else if(typeAnalytics.equals("User ranking system")){
                ArrayList<User> users= MongoDBDriver.userRankingSystem(Integer.parseInt(kAnalytics));
                showFilteredUsers(users);
            }
        }else
        {
            Utils.showErrorAlert("Not all fields have been selected.");
        }
    }
}
