package it.unipi.dii.inginf.lsdb.coogether.persistence;

import it.unipi.dii.inginf.lsdb.coogether.bean.Comment;
import it.unipi.dii.inginf.lsdb.coogether.bean.Recipe;
import it.unipi.dii.inginf.lsdb.coogether.bean.User;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import static org.neo4j.driver.Values.parameters;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Neo4jDriver implements DatabaseDriver{

    private Driver driver;
    private String uri="neo4j://localhost:7687";
    private String user= "neo4j";
    private String password= "root";

    @Override
    public boolean openConnection() {
        try {
            driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
        }catch (Exception ex){
            System.out.println("Impossible open connection with Neo4j");
            return false;
        }
        return true;
    }

    @Override
    public void closeConnection() {
        try{
            driver.close();
        }catch (Exception ex){
            System.out.println("Impossible close connection with Neo4j");
        }
    }

    //******************************************************************************************************************
    //                              CRUD OPERATIONS
    //******************************************************************************************************************

    //ogni volta che si elimina un nodo, ricordarsi di eliminare anche i relativi archi

    public boolean addRecipe(Recipe r){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateRecipe(Recipe r){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteRecipe(Recipe r){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    //decidere se quando viene eliminato un utente devono essere eliminate tutte le sue ricette
    /*public boolean deleteRecipesOfAUser(User u){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }*/

    public boolean addComment(Recipe r, Comment c){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateComment(Recipe r, Comment c){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteComment(Recipe r, Comment c){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addUser(User u){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateUser(User u){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteUser(User u){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean follow(){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean unfollow(){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public List<User> getUsers(){
        List<User> users= new ArrayList<>();

        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

        return users;
    }

    public List<User> getFollowedUsers(User u){
        List<User> users= new ArrayList<>();

        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

        return users;
    }

    public List<Comment> getComments(Recipe r){
        List<Comment> comments= new ArrayList<>();

        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

        return comments;
    }

    //******************************************************************************************************************
    //                              ANALYTICS
    //******************************************************************************************************************

    public List<Recipe> searchSuggestedRecipes(String userId){
        List<Recipe> recipes= new ArrayList<>();

        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

        return recipes;
    }
}
