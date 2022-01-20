package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.Recipe;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.Comment;
import it.unipi.lmmsdb.coogether.coogetherapp.config.SessionUtils;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.MongoDBDriver;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class RecipeViewController implements Initializable {

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
    @FXML private Spinner starSpinner;
    @FXML private Button addCommennt;

    Recipe recipe;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBack.setOnMouseClicked(mouseEvent -> clickOnBackToChangePage(mouseEvent));
        log.setOnMouseClicked(mouseEvent -> clickOnUserToChangePage(mouseEvent));
        recipe= MongoDBDriver.getRecipesFromId(SessionUtils.getRecipeToShow().getRecipeId());
        recipeTitle.setText(recipe.getName());
        recipeAuthorName.setText(recipe.getAuthorName());
        Image img = new Image(recipe.getImage());
        recipeImg.setImage(img);
        recipeCategory.setText(recipe.getCategory());
        recipeDescription.setText(recipe.getDescription());
        recipeCookTime.setText(String.valueOf(recipe.getCookTime()));
        recipePrep.setText(String.valueOf(recipe.getPrepTime()));
        recipeKal.setText(String.valueOf(recipe.getCalories()));
        recipeFat.setText(String.valueOf(recipe.getFatContent()));
        recipeServings.setText(String.valueOf(recipe.getRecipeServings()));
        recipeSodium.setText(String.valueOf(recipe.getSodiumContent()));
        recipeProtein.setText(String.valueOf(recipe.getProteinContent()));

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
        addCommennt.setOnMouseClicked(mouseEvent -> clickOnAddComment(mouseEvent));
   }

    private void clickOnAddComment(MouseEvent mouseEvent) {
        //deve controllare se lo user e loggato prima di aggiungere il commento
    }

    private void clickOnUserToChangePage(MouseEvent mouseEvent) {
        //mostra i dati dello user se questo è loggato, altrimenti ad una pagina per fare il login
    }

    private void clickOnBackToChangePage(MouseEvent mouseEvent) {
        //torna alla hello page
    }

    public void setRecipeId(int id) {
        this.recipe.setRecipeId(id);
    }
}
