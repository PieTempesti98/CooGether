import dbConnections.GraphConn;
import dbConnections.MongoConn;
import dto.*;
import entities.Comment;
import entities.Recipe;
import entities.User;
import utils.CommentHandler;
import utils.RecipeHandler;
import utils.UserHandler;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args){
        RecipeListDTO recipeList = RecipeHandler.parseRecipes();
        CommentListDTO commentList = CommentHandler.parseComments();
        ArrayList<Recipe> recipes = RecipeHandler.convertToRecipes(recipeList.getData(), commentList.getData());
        ArrayList<Comment> comments = CommentHandler.convertToComments(commentList.getData());

        ArrayList<Integer> userIDs = new ArrayList<>();
        ArrayList<String> usernames = new ArrayList<>();

        for(Recipe r: recipes){
            if(!userIDs.contains(r.getAuthorId())){
                userIDs.add(r.getAuthorId());
                usernames.add(r.getAuthorName());
            }
        }

        for(Comment c: comments){
            if(!userIDs.contains(c.getAuthorId())){
                userIDs.add(c.getAuthorId());
                usernames.add(c.getAuthorName());
            }
        }

        int i = 1;
        while(userIDs.size() <= 65000){
            if(!userIDs.contains(i))
                userIDs.add(i);
            i++;
        }

        ArrayList<UserDTO> userList = new ArrayList<>();
        userList = UserHandler.parseUsers("/uploads/download (1).json", userList);
        userList = UserHandler.parseUsers("/uploads/download (2).json", userList);
        userList = UserHandler.parseUsers("/uploads/download (2) (1).json", userList);
        userList = UserHandler.parseUsers("/uploads/download (1) (1).json", userList);
        userList = UserHandler.parseUsers("/uploads/download (2) (2).json", userList);
        userList = UserHandler.parseUsers("/uploads/download.json", userList);
        userList = UserHandler.parseUsers("/uploads/download-2.json", userList);
        userList = UserHandler.parseUsers("/uploads/download-3.json", userList);
        userList = UserHandler.parseUsers("/uploads/download-4.json", userList);
        userList = UserHandler.parseUsers("/uploads/download-5.json", userList);
        userList = UserHandler.parseUsers("/uploads/download-6.json", userList);
        userList = UserHandler.parseUsers("/uploads/download-7.json", userList);
        userList = UserHandler.parseUsers("/uploads/download-8.json", userList);

        ArrayList<FollowDTO> followList = UserHandler.parseFollows("/uploads/follow.json");

        ArrayList<User> users = UserHandler.convertToUsers(userList);
        users = UserHandler.fixUserId(users, userIDs, usernames);

        NewRecipeListDTO newRecipes = RecipeHandler.parseNewRecipes();
        ArrayList<NewRecipeDTO> newList = new ArrayList<>(newRecipes.getData().subList(0,10000));
        ArrayList<Recipe>recipesToPrint = RecipeHandler.convertNewRecipes(recipes, newList, users);

        //MongoConn.insertRecipes(recipesToPrint);
        GraphConn graphConn = new GraphConn();
//        graphConn.addUsers(users);
//        graphConn.connectUsers(followList);
        graphConn.addRecipes(recipesToPrint);

//        UserHandler.toJson(users);
//        RecipeHandler.toJson(recipesToPrint);
//        CommentHandler.toJson(comments);


    }
}
