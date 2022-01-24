package it.unipi.lmmsdb.coogether.coogetherapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.Recipe;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.config.SessionUtils;
import it.unipi.lmmsdb.coogether.coogetherapp.utils.Utils;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.Neo4jDriver;
import it.unipi.lmmsdb.coogether.coogetherapp.persistence.MongoDBDriver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class AddRecipeController implements Initializable {

    @FXML private TextField recipeName;
    @FXML private TextField recipeCategory;
    @FXML private TextField recipeDescription;
    @FXML private TextArea recipeIngredients;
    @FXML private TextField recipeImg;
    @FXML private TextField recipeCook;
    @FXML private TextField recipePrep;
    @FXML private TextField recipeServ;
    @FXML private TextField recipeKal;
    @FXML private TextField recipeFat;
    @FXML private TextField recipeSod;
    @FXML private TextField recipeProt;
    @FXML private TextArea recipeInstructions;
    @FXML private ImageView goBack;
    private Recipe recipe;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBack.setOnMouseClicked(mouseEvent -> goBack(mouseEvent));
        recipe = SessionUtils.getRecipeToShow();
        if(recipe != null){
            recipeName.setText(recipe.getName());
            recipeCategory.setText(recipe.getCategory());
            recipeDescription.setText(recipe.getDescription());
            if(recipe.getImage() != null)
                recipeImg.setText(recipe.getDescription());
            recipeCook.setText(String.valueOf(recipe.getCookTime()));
            recipePrep.setText(String.valueOf(recipe.getPrepTime()));
            recipeKal.setText(String.valueOf(recipe.getCalories()));
            recipeFat.setText(String.valueOf(recipe.getFatContent()));
            recipeServ.setText(String.valueOf(recipe.getRecipeServings()));
            recipeSod.setText(String.valueOf(recipe.getSodiumContent()));
            recipeProt.setText(String.valueOf(recipe.getProteinContent()));
            StringBuilder ing = new StringBuilder();
            for(String s: recipe.getIngredients()) {
                ing.append(s);
                ing.append("\n");
            }
            recipeIngredients.setText(ing.toString());
            StringBuilder proc = new StringBuilder();
            for(String s: recipe.getRecipeInstructions()) {
                proc.append(s);
                proc.append("\n");
            }
            recipeInstructions.setText(proc.toString());
        }
    }

    private void clearAllFields()
    {
        recipeName.setText("");
        recipeInstructions.setText("");
        recipeIngredients.setText("");
        recipePrep.setText("");
        recipeCook.setText("");
        recipeFat.setText("");
        recipeProt.setText("");
        recipeServ.setText("");
        recipeSod.setText("");
        recipeKal.setText("");
        recipeImg.setText("");
        recipeCategory.setText("");
        recipeDescription.setText("");
    }

    @FXML private void addRecipe(ActionEvent ae){
        if(recipeName.getText().isEmpty() || recipeCategory.getText().isEmpty() || recipeIngredients.getText().isEmpty()
                || recipeInstructions.getText().isEmpty()){
            Utils.showErrorAlert("Title, Category, Ingredients and Instructions are mandatory fields");
        }else{


            double kal=0;
            double fat=0;
            double sod=0;
            double prot=0;

            if(recipeKal.getText().isEmpty())
                kal = -1;
            else{
                try {
                    kal = Double.parseDouble(recipeKal.getText());
                }catch (NumberFormatException e){
                    Utils.showErrorAlert("The Calories field must contains only numbers!");
                    return;
                }
            }

            if(recipeFat.getText().isEmpty())
                fat = -1;
            else {
                try {
                    fat = Double.parseDouble(recipeFat.getText());
                } catch (NumberFormatException e) {
                    Utils.showErrorAlert("The Fat Content field must contains only numbers!");
                    return;
                }
            }

            if(recipeSod.getText().isEmpty())
                sod = -1;
            else{
                try {
                    sod = Double.parseDouble(recipeSod.getText());
                }catch (NumberFormatException e){
                    Utils.showErrorAlert("The Sodium Content field must contains only numbers!");
                    return;
                }
            }

            if(recipeProt.getText().isEmpty())
                prot = -1;
            else{
                try {
                    prot = Double.parseDouble(recipeProt.getText());
                }catch (NumberFormatException e){
                    Utils.showErrorAlert("The Protein Content field must contains only numbers!");
                    return;
                }
            }

            int serv=0;

            if(recipeServ.getText().isEmpty())
                serv = -1;
            else{
                try {
                    serv = Integer.parseInt(recipeServ.getText());
                }catch (NumberFormatException e){
                    Utils.showErrorAlert("The Recipe Servings field must contains only numbers!");
                    return;
                }
            }

            int cook=0;
            int prep=0;

            if(recipeCook.getText().isEmpty())
                cook = -1;
            else{
                try {
                    cook = Integer.parseInt(recipeCook.getText());
                }catch (NumberFormatException e){
                    Utils.showErrorAlert("The Cook Time field must contains only numbers!");
                    return;
                }
            }

            if(recipePrep.getText().isEmpty())
                prep = -1;
            else{
                try {
                    prep = Integer.parseInt(recipePrep.getText());
                }catch (NumberFormatException e){
                    Utils.showErrorAlert("The Preparation Time field must contains only numbers!");
                    return;
                }
            }
            String imageUrl;

            if(recipeImg.getText().isEmpty() || recipeImg.getText().equals(""))
                imageUrl = null;
            else
                imageUrl = recipeImg.getText();

            ArrayList<String> ing= new ArrayList<>(Arrays.asList(recipeIngredients.getText().split("\n")));
            ArrayList<String> inst= new ArrayList<>(Arrays.asList(recipeInstructions.getText().split("\n")));
            int id, authorId;
            String authorName;
            Date date;
            if(recipe == null) { //New recipe
                id = MongoDBDriver.getMaxRecipeId() + 1;
                date = new Date(System.currentTimeMillis());
                authorId = SessionUtils.getUserLogged().getUserId();
                authorName = SessionUtils.getUserLogged().getUsername();
            }
            else { //Updated recipe
                id = recipe.getRecipeId();
                date = recipe.getDatePublished();
                authorId = recipe.getAuthorId();
                authorName = recipe.getAuthorName();
            }

            Recipe r= new Recipe(id, recipeName.getText(), authorId, authorName, cook, prep, date, recipeDescription.getText(),
                    imageUrl, recipeCategory.getText(), ing, kal, fat, sod, prot, serv, inst);

            System.out.println(r.getRecipeId());
            if(recipe != null)
                System.out.println(recipe.getRecipeId());
            if(recipe == null){
                if(Utils.addRecipe(r)) {
                    clearAllFields();
                    SessionUtils.setRecipeToShow(r);
                    MongoDBDriver.setMaxRecipeId(r.getRecipeId());
                    Utils.changeScene("recipe-view.fxml", ae);
                }
            }
            else
                if(Utils.updateRecipe(r)){
                    clearAllFields();
                    SessionUtils.setRecipeToShow(r);
                    MongoDBDriver.setMaxRecipeId(r.getRecipeId());
                    Utils.changeScene("recipe-view.fxml", ae);
                }
        }
    }

    @FXML
    private void goBack(MouseEvent mouseEvent) {
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
}
