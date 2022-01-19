package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.Recipe;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.Comment;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.MongoDBDriver;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class RecipeViewController  implements Initializable {

    @FXML private ImageView goBack;
    @FXML private ImageView log;
    @FXML private Text recipeTitle;
    @FXML private Text recipeAuthorName;
    @FXML private ImageView recipeImg;
    @FXML private Text recipeCategory;
    @FXML private Text recipeDescription;
    @FXML private VBox recipeIngredients;
    @FXML private Text recipeCookTime;
    @FXML private Text recipePrep;
    @FXML private Text recipeKal;
    @FXML private Text recipeFat;
    @FXML private Text recipeServings;
    @FXML private Text recipeSodium;
    @FXML private Text recipeProtein;
    @FXML private VBox recipeInstructions;
    @FXML private VBox comments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*Recipe recipe= MongoDBDriver.getRecipesFromId();
        recipeTitle.setText(recipe.getName());
        recipeAuthorName.setText(recipe.getAuthorName());
        Image img = new Image(recipe.getImage());
        recipeImg.setImage(img);
        recipeCategory.setText(recipe.getCategory());
        recipeDescription.setText(recipe.getDescription());
        recipeCookTime.setText(recipe.getCookTime());
        recipePrep.setText(recipe.getPrepTime());
        recipeKal.setText(recipe.getCalories());
        recipeFat.setText(recipe.getFatContent());
        recipeServings.setText(recipe.getRecipeServings());
        recipeSodium.setText(recipe.getSodiumContent());
        recipeProtein.setText(recipe.getProteinContent());

        for (String s : recipe.getIngredients()){
            Text text = new Text();
            text.setText(s);
            recipeIngredients.getChildren().add(text);
        }

        for (String s : recipe.getInstructions()){
            Text text = new Text();
            text.setText(s);
            recipeInstructions.getChildren().add(text);
        }

        for (Comment c : recipe.getComments()){
            HBox box = new HBox();
            Text author = new Text();
            author.setText(c.getAuthorName());
            box.getChildren().add(author);
            int star = c.getRating();
            for (int i=0; i< star; i++){
                ImageView imgViewStar = new ImageView();
                Image imgStar = new Image("/../../../../../resources/it/unipi/lmmsdb/coogether/coogetherapp/img");
                imgViewStar.setImage(imgStar);
                box.getChildren().add(imgViewStar);
            }
            Text text = new Text();
            text.setText(c.getText());
            comments.getChildren().addAll(box, text);
        }
   */ }
}
