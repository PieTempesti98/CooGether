package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.Recipe;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.config.SessionUtils;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.MongoDBDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private VBox recipeContainer;
    @FXML private ChoiceBox filterCategory;
    @FXML private ChoiceBox AnalyticsCategory;
    @FXML private ChoiceBox ChoseAnalytics;
    @FXML private ChoiceBox KAnalytics;
    @FXML private TextField filterAuthor;
    @FXML private TextField filterIng1;
    @FXML private TextField filterIng2;
    @FXML private VBox userName;

    private int skip = 0;

    Neo4jDriver neo4j = Neo4jDriver.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //create filter category choicebox
        ArrayList<String> categories= neo4j.getAllCategories();
        filterCategory.getItems().add("NO filter");
        for(String cat : categories)
        {
            filterCategory.getItems().add(cat);
            AnalyticsCategory.getItems().add(cat);
        }

        User logged = SessionUtils.getUserLogged();
        if(logged != null)
        {
            Label uName = new Label(logged.getUsername());
            Font bold = new Font("System Bold", 18);
            uName.setFont(bold);

            userName.getChildren().add(uName);
        }
        // retrieve first 20 recipes
        showRecipes();
    }

    private void visualizeProfile(ActionEvent actionEvent) {
        Utils.changeScene("user-details-view.fxml", actionEvent);
    }

    //Called by the show more button
    private void showMoreRecipes(){
        recipeContainer.getChildren().remove(recipeContainer.getChildren().size() - 1);
        showRecipes();

    }

    //Create the show more button
    private void createShowMore(){
        Button showMore = new Button("Show more");
        showMore.setOnAction(actionEvent -> showMoreRecipes());
        recipeContainer.getChildren().add(showMore);
    }

    //Adds 20 recipes in the scene and places the show more button
    private void showRecipes(){
        ArrayList<Recipe> recipes;
        User logged = SessionUtils.getUserLogged();
        if(logged == null)
            recipes = neo4j.getRecipes(skip, 20);
        else {
            recipes = neo4j.searchSuggestedRecipes(skip, 20, logged.getUsername());
            if(recipes == null || recipes.size() == 0)
                recipes = neo4j.getRecipes(skip, 20);
        }
        for(Recipe r: recipes){
            Label recipeName = new Label("Title: ");
            Font bold = new Font("System Bold", 18);
            Font size = new Font(14);
            recipeName.setFont(bold);
            Label recipeCategory= new Label("Category: ");
            recipeCategory.setFont(bold);
            Label date = new Label("Date of publication: ");
            date.setFont(bold);
            Label title = new Label(r.getName());
            title.setFont(size);
            Label category = new Label(r.getCategory());
            category.setFont(size);
            Label datePublished  = new Label(new SimpleDateFormat("dd-MM-yyyy").format(r.getDatePublished()));
            datePublished.setFont(size);
            HBox recContainer = new HBox();
            recContainer.setAlignment(Pos.CENTER_LEFT);
            recContainer.getChildren().add(recipeName);
            recContainer.getChildren().add(title);
            recContainer.getChildren().add(recipeCategory);
            recContainer.getChildren().add(category);
            recContainer.getChildren().add(date);
            recContainer.getChildren().add(datePublished);
            recContainer.setOnMouseClicked(mouseEvent -> {goToRecipe(r, mouseEvent);});
            recContainer.setSpacing(10);
            recContainer.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #596cc2;");
            recipeContainer.getChildren().add(recContainer);
        }
        skip = skip + 20;

        createShowMore();

    }

    private void goToRecipe(Recipe r, MouseEvent mouseEvent){
        SessionUtils.setRecipeToShow(r);
        ActionEvent ae = new ActionEvent(mouseEvent.getSource(), mouseEvent.getTarget());
        Utils.changeScene("recipe-view.fxml", ae);
    }

    @FXML
    private void login(ActionEvent ae, String password, String email){
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        User u = neo4j.getUsersFromUnique(email);

        if(email.isEmpty() || password.isEmpty()){
            Utils.showErrorAlert("You should insert email and password");
        }else {
            if (u == null) {
                Utils.showErrorAlert("user not found");
                return;
            }
            if (!u.getPassword().equals(password)) {
                Utils.showErrorAlert("Wrong password");
                return;
            }
            SessionUtils.setUserLogged(u);
            if (u.getRole() == 2) {
                //code to the admin page
            } else {
                Utils.changeScene("user-details-view.fxml", ae);

            }
        }
    }

    @FXML
    private void signUp(ActionEvent actionEvent) {
        Utils.changeScene("registration-view.fxml", actionEvent);
    }


    public void filterFunction(ActionEvent actionEvent) {
        //show filtered recipe
        String catFilter=(String) filterCategory.getValue();
        String autFilter=filterAuthor.getText();
        String ingFilter1=filterIng1.getText();
        String ingFilter2=filterIng2.getText();

        if(catFilter.equals("NO filter"))
        {
            showRecipes();
        }
        else if(!catFilter.equals("Filters")){
            ArrayList<Recipe> recipes=MongoDBDriver.getRecipesFromCategory(catFilter);
            showFilteredRecipes(recipes);
        }
        else if(!autFilter.equals("")){
            ArrayList<Recipe> recipes=MongoDBDriver.getRecipesFromAuthorName(autFilter);
            System.out.println("autore");
            System.out.println(recipes.size());
            if(recipes.size()==0)
            {
                Utils.showErrorAlert("No recipe found");
            }
            else
                showFilteredRecipes(recipes);
        }
        else if(!ingFilter1.equals("") && !ingFilter2.equals("")){
            ArrayList<Recipe> recipes=MongoDBDriver.getRecipesFromTwoIngredients(ingFilter1, ingFilter2);
            if(recipes.size()==0)
            {
                Utils.showErrorAlert("No recipe found");
            }
            else
                showFilteredRecipes(recipes);
        }
        else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("No filter inserted");
            errorAlert.setContentText("Select a category OR insert an author name OR insert two ingredients");
            errorAlert.showAndWait();
        }
    }

    private void showFilteredRecipes(ArrayList<Recipe> recipes) {
        //remove all the previous recipes
        recipeContainer.getChildren().clear();
        for(Recipe r: recipes) {
            Label recipeName = new Label("Title: ");
            Font bold = new Font("System Bold", 18);
            Font size = new Font(14);
            recipeName.setFont(bold);
            Label recipeCategory = new Label("Category: ");
            recipeCategory.setFont(bold);
            Label date = new Label("Date of publication: ");
            date.setFont(bold);
            Label title = new Label(r.getName());
            title.setFont(size);
            Label category = new Label(r.getCategory());
            category.setFont(size);
            Label datePublished = new Label(new SimpleDateFormat("dd-MM-yyyy").format(r.getDatePublished()));
            datePublished.setFont(size);
            HBox recContainer = new HBox();
            recContainer.setAlignment(Pos.CENTER_LEFT);
            recContainer.getChildren().add(recipeName);
            recContainer.getChildren().add(title);
            recContainer.getChildren().add(recipeCategory);
            recContainer.getChildren().add(category);
            recContainer.getChildren().add(date);
            recContainer.getChildren().add(datePublished);
            recContainer.setOnMouseClicked(mouseEvent -> {
                goToRecipe(r, mouseEvent);
            });
            recContainer.setSpacing(10);
            recContainer.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;"
                    + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                    + "-fx-border-radius: 5;" + "-fx-border-color: #596cc2;");
            recipeContainer.getChildren().add(recContainer);
        }
    }

    @FXML
    private void showUsers(ActionEvent actionEvent){
        Utils.changeScene("users-view.fxml", actionEvent);
    }

    public void AnalyticsFunction(ActionEvent actionEvent) {
        String typeAnalytics=(String) ChoseAnalytics.getValue();
        String catAnalytics=(String) AnalyticsCategory.getValue();
        String kAnalytics=(String) KAnalytics.getValue();

        if(!typeAnalytics.equals("Select") && !catAnalytics.equals("Categories") && !kAnalytics.equals("How many"))
        {
            //chiamare analytics
            if(typeAnalytics.equals("Top healthiest"))
            {
                ArrayList<Recipe> recipes=MongoDBDriver.searchTopKHealthiestRecipes(catAnalytics, Integer.parseInt(kAnalytics));
                showFilteredRecipes(recipes);
            }
            else if(typeAnalytics.equals("Plus votes equal to 5"))
            {
                ArrayList<Recipe> recipes=MongoDBDriver.searchTopKReviewedRecipes(catAnalytics, Integer.parseInt(kAnalytics));
                showFilteredRecipes(recipes);
            }
            else if(typeAnalytics.equals("Top fastest"))
            {
                ArrayList<Recipe> recipes=MongoDBDriver.searchFastestRecipes(catAnalytics, Integer.parseInt(kAnalytics));
                showFilteredRecipes(recipes);
            }
            else if(typeAnalytics.equals("With few ingredients"))
            {
                ArrayList<Recipe> recipes=MongoDBDriver.searchFewestIngredientsRecipes(catAnalytics, Integer.parseInt(kAnalytics));
                showFilteredRecipes(recipes);
            }
        }
        else
        {
            Utils.showErrorAlert("Not all fields have been selected.");
        }
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