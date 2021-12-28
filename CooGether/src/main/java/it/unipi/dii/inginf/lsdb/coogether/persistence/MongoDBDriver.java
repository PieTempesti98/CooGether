package it.unipi.dii.inginf.lsdb.coogether.persistence;

import com.mongodb.client.*;
import com.mongodb.ConnectionString;
import it.unipi.dii.inginf.lsdb.coogether.bean.Comment;
import it.unipi.dii.inginf.lsdb.coogether.bean.Recipe;
import org.bson.Document;
import org.bson.*;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;
import  static com.mongodb.client.model.Sorts.*;

public class MongoDBDriver implements DatabaseDriver{

    private MongoClient myClient;
    private MongoDatabase db;
    private MongoCollection<org.bson.Document> collection;

    private ConnectionString uri= new ConnectionString("mongodb://localhost:27017");

    @Override public boolean openConnection(){
        try{
            myClient= MongoClients.create(uri);
            db= myClient.getDatabase("CooGether");
            collection= db.getCollection("recipe");
        }catch (Exception ex){
            System.out.println("Impossible open connection with MongoDB");
            return false;
        }
        return true;
    }

    @Override public void closeConnection(){
        try{
            myClient.close();
        }catch (Exception ex){
            System.out.println("Impossible close connection with MongoDB");
        }
    }

    //******************************************************************************************************************
    //                              CRUD OPERATIONS
    //******************************************************************************************************************

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

    //decidere se quando viene eliminato un utente devono essere eliminate tutte le sue ricette
    //public boolean deleteRecipesUser(User u){
        //return false;
    //}

    //******************************************************************************************************************
    //                              ANALYTICS
    //******************************************************************************************************************
}
