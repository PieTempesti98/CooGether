package it.unipi.lmmsdb.coogether.coogetherapp.utils;

import it.unipi.lmmsdb.coogether.coogetherapp.HelloApplication;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.Comment;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.Recipe;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.MongoDBDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.pojo.CommentPojo;
import it.unipi.lmmsdb.coogether.coogetherapp.pojo.RecipePojo;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.neo4j.driver.internal.InternalPath;

import java.io.IOException;
import java.util.ArrayList;

public class Utils {
    static public void changeScene(String fxmlFile, ActionEvent event){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1200, 800);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Recipe mapRecipe(RecipePojo r){
        Recipe recipe = new Recipe();
        recipe.setRecipeId(r.getRecipeId());
        recipe.setName(r.getName());
        recipe.setAuthorId(r.getAuthorId());
        recipe.setAuthorName(r.getAuthorName());
        if(r.getDatePublished() != null)
            recipe.setDatePublished(r.getDatePublished().get$date());
        recipe.setCategory(r.getRecipeCategory());
        recipe.setRecipeInstructions(r.getRecipeInstructions());
        recipe.setCalories(r.getCalories());
        recipe.setFatContent(r.getFatContent());
        recipe.setProteinContent(r.getProteinContent());
        recipe.setSodiumContent(r.getSodiumContent());
        recipe.setCookTime(r.getCookTime());
        recipe.setPrepTime(r.getPrepTime());
        recipe.setDescription(r.getDescription());
        recipe.setImages(r.getImage());
        recipe.setIngredients(r.getIngredients());
        if(r.getComments() != null) {
            ArrayList<Comment> comments = new ArrayList<>();
            for (CommentPojo c : r.getComments()) {
                Comment comment = new Comment(c.getReviewId(), c.getAuthorId(), c.getAuthorName(), c.getRating(), c.getDateSubmitted().get$date(), c.getComment());
                comments.add(comment);
            }
            recipe.setComments(comments);
        }

        return recipe;
    }

    public static void showErrorAlert(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error message");
        alert.setContentText(s);
        alert.showAndWait();
    }

    public static void showInfoAlert(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Info message");
        alert.setContentText(s);
        alert.showAndWait();
    }

    public static boolean deleteAccount(User user) {
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        ArrayList<Recipe> recipesToDelete = MongoDBDriver.getRecipesFromAuthorName(user.getUsername());
        if (MongoDBDriver.deleteRecipesOfAUser(user)) {
            if (neo4j.deleteRecipesOfAUser(user))
                if (neo4j.deleteUser(user))
                    return true;
                else {
                    for (Recipe r : recipesToDelete)
                        addRecipe(r);
                    return false;
                }
            else {
                for (Recipe r : recipesToDelete)
                    MongoDBDriver.addRecipe(r);
                return false;
            }
        }
        return false;
    }

    public static boolean deleteRecipe(Recipe r){
        if(MongoDBDriver.deleteRecipe(r)) {
            if (Neo4jDriver.getInstance().deleteRecipe(r))
                return true;
            else {
                MongoDBDriver.addRecipe(r);
                return false;
            }
        }
        return false;
    }

    public static boolean addRecipe(Recipe r){
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        if(MongoDBDriver.addRecipe(r))
        {
            //If neo is ok, perform mongo
            if(!neo4j.addRecipe(r))
            {
                // if mongo is not ok, remove the previously added recipe
                MongoDBDriver.deleteRecipe(r);
                showErrorAlert("Error in adding the recipe");
                return false;
            }
            else
            {
                Utils.showInfoAlert("Recipe succesfully added");
                return true;
            }
        } else
            return false;
    }

    public static boolean updateRecipe(Recipe r){
        Recipe old = MongoDBDriver.getRecipesFromId(r.getRecipeId());
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        if(MongoDBDriver.updateRecipe(r))
        {
            //If neo is ok, perform mongo
            if(!neo4j.updateRecipe(r))
            {
                // if mongo is not ok, remove the previously added recipe
                MongoDBDriver.updateRecipe(old);
                showErrorAlert("Error updating the recipe");
                return false;
            }
            else
            {
                Utils.showInfoAlert("Recipe succesfully updated");
                return true;
            }
        } else
            return false;
    }

    public static void closeApp(){
        Neo4jDriver.getInstance().closeConnection();
    }
}
