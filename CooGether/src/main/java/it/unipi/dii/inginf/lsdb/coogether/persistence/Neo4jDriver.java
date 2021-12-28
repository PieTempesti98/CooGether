package it.unipi.dii.inginf.lsdb.coogether.persistence;

import it.unipi.dii.inginf.lsdb.coogether.bean.Comment;
import it.unipi.dii.inginf.lsdb.coogether.bean.Recipe;
import it.unipi.dii.inginf.lsdb.coogether.bean.User;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import java.util.List;

import static org.neo4j.driver.Values.parameters;

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
        return false;
    }

    public boolean updateRecipe(Recipe r){
        return false;
    }

    public boolean deleteRecipe(Recipe r){
        return false;
    }

    public boolean addComment(Recipe r, Comment c){
        return false;
    }

    public boolean updateComment(Recipe r, Comment c){
        return false;
    }

    public boolean deleteComment(Recipe r, Comment c){
        return false;
    }

    public boolean addUser(User u){
        return false;
    }

    public boolean updateUser(User u){
        return false;
    }

    public boolean deleteUser(User u){
        return false;
    }

    public List<User> getUsers(){

    }

    //******************************************************************************************************************
    //                              ANALYTICS
    //******************************************************************************************************************
}
