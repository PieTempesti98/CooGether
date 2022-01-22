package it.unipi.lmmsdb.coogether.coogetherapp.utils;

import it.unipi.lmmsdb.coogether.coogetherapp.HelloApplication;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.Comment;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.Recipe;
import it.unipi.lmmsdb.coogether.coogetherapp.pojo.CommentPojo;
import it.unipi.lmmsdb.coogether.coogetherapp.pojo.RecipePojo;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Utils {
    static public void changeScene(String fxmlFile, ActionEvent event){

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Error message");
        alert.setContentText(s);
        alert.showAndWait();
    }

    public static void showInfoAlert(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Info message");
        alert.setHeaderText(s);
        alert.showAndWait();
    }
}
