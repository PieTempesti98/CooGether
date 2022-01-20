package it.unipi.lmmsdb.coogether.coogetherapp.config;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.Recipe;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;

public class SessionUtils {
    private static User userLogged = null;
    private static Recipe recipeToShow = null;

    public static void setUserLogged(User userLogged){
        SessionUtils.userLogged = userLogged;
    }

    public static User getUserLogged(){
        return userLogged;
    }

    public static Recipe getRecipeToShow() {
        return recipeToShow;
    }

    public static void setRecipeToShow(Recipe recipeToShow) {
        SessionUtils.recipeToShow = recipeToShow;
    }
}
