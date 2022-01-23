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

    public void initialize(URL url, ResourceBundle resourceBundle) {
        goBack.setOnMouseClicked(mouseEvent -> goBack(mouseEvent));
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

    @FXML private void addRecipe(ActionEvent ae) throws JsonProcessingException {
        if(recipeName.getText().isEmpty() || recipeCategory.getText().isEmpty() || recipeIngredients.getText().isEmpty()
                || recipeInstructions.getText().isEmpty()){
            Utils.showErrorAlert("Title, Category, Ingredients and Instructions are mandatory fields");
        }else{
            Date date = new Date(System.currentTimeMillis());

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

            int id=MongoDBDriver.getMaxRecipeId() +1 ;

            int authorId = SessionUtils.getUserLogged().getUserId();
            String authorName= SessionUtils.getUserLogged().getUsername();

            ArrayList<String> ing= (ArrayList<String>) Arrays.asList(recipeIngredients.getText().split(","));
            ArrayList<String> inst= (ArrayList<String>) Arrays.asList(recipeInstructions.getText().split(","));

            Recipe r= new Recipe(id, recipeName.getText(), authorId, authorName, cook, prep, date, recipeDescription.getText(),
                    recipeImg.getText(), recipeCategory.getText(), ing, kal, fat, sod, prot, serv, inst);

            Neo4jDriver neo4j = Neo4jDriver.getInstance();
            if(neo4j.addRecipe(r))
            {
                //If neo is ok, perform mongo
                if(!MongoDBDriver.addRecipe(r))
                {
                    // if mongo is not ok, remove the previously added recipe
                    neo4j.deleteRecipe(r);
                    Utils.showErrorAlert("Error in adding the recipe");
                }
                else
                {
                    Utils.showInfoAlert("Recipe succesfully added");
                }
            }
            clearAllFields();
            SessionUtils.setRecipeToShow(r);
            Utils.changeScene("recipe-view.fxml", ae);
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
