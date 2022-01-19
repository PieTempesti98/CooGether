package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.Recipe;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private VBox recipeContainer;

    private int skip = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // retrieve first 20 recipes
        showRecipes();


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
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        ArrayList<Recipe> recipes = neo4j.getRecipes(skip);

        for(Recipe r: recipes){
            Label title = new Label(r.getName());
            Label category = new Label(r.getCategory());
            Label datePublished  = new Label(new SimpleDateFormat("dd-MM-yyyy").format(r.getDatePublished()));
            HBox recContainer = new HBox();
            recContainer.setAlignment(Pos.CENTER_LEFT);
            recContainer.getChildren().add(title);
            recContainer.getChildren().add(category);
            recContainer.getChildren().add(datePublished);
            recContainer.setOnMouseClicked(mouseEvent -> {goToRecipe(r);});
            recContainer.setSpacing(10);

            recipeContainer.getChildren().add(recContainer);
        }
        skip = skip + 20;

        createShowMore();

    }

    private void goToRecipe(Recipe r){

    }
}