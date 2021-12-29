package it.unipi.dii.inginf.lsdb.coogether.persistence;

import it.unipi.dii.inginf.lsdb.coogether.bean.Comment;
import it.unipi.dii.inginf.lsdb.coogether.bean.Recipe;
import it.unipi.dii.inginf.lsdb.coogether.bean.User;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import static org.neo4j.driver.Values.parameters;
import static org.neo4j.driver.Values.NULL;

import java.util.*;

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

    //da implementare
    public boolean addRecipe(Recipe r){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    //da implementare
    public boolean updateRecipe(Recipe r){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    //oltre alla ricetta vanno cancellati tutti i commenti della ricetta
    public boolean deleteRecipe(Recipe r){
        try(Session session= driver.session()){

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (r:Recipe)-[h:HAS]->(c:Comment) " +
                                "WHERE r.id=$id " +
                                "DETACH DELETE r, c",
                        parameters( "id", r.getRecipeId()) );
                return null;
            });
            return true;

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
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

    //da implementare
    public boolean addComment(Recipe r, Comment c){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    //da implementare
    public boolean updateComment(Recipe r, Comment c){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteComment(Comment c){
        try(Session session= driver.session()){

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (c:Comment) WHERE c.id=$id DETACH DELETE c",
                        parameters( "id", c.getCommentId()) );
                return null;
            });
            return true;

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    //da implementare
    public boolean addUser(User u){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    //da implementare
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

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (u:User) WHERE u.id=$id DETACH DELETE u",
                        parameters( "id", u.getUserId()) );
                return null;
            });
            return true;

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    //da implementare
    public boolean follow(){
        try(Session session= driver.session()){

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    //da implementare
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

            session.readTransaction(tx->{
                Result result = tx.run("match (u:User)" +
                                "return u");

                while(result.hasNext()){
                    Record r= result.next();
                    String id = r.get("u.id").asString();
                    String username = r.get("u.username").asString();
                    User user= new User(id, username);
                    users.add(user);
                }
                return users;
            });

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

        return users;
    }

    public List<User> getFollowedUsers(User u){
        List<User> users= new ArrayList<>();

        try(Session session= driver.session()){

            session.readTransaction(tx->{
                Result result = tx.run("match (u1:User)-[f:FOLLOWS]->(u2:User)" +
                                          "where u1.id = $userId"+
                                           "return u2", parameters("userId", u.getUserId()));

                while(result.hasNext()){
                    Record r= result.next();
                    String id = r.get("u2.id").asString();
                    String username = r.get("u2.username").asString();
                    User user= new User(id, username);
                    users.add(user);
                }
                return users;
            });

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return users;
    }

    public List<Comment> getComments(String idRecipe){
        List<Comment> comments= new ArrayList<>();

        try(Session session= driver.session()){

            session.readTransaction(tx->{
                Result result = tx.run("match (r:Recipe)" +
                                          "match (c:Comment)<-[h:HAS]-(r)" +
                                          "match (u:User)-[w:WRITES]->(c)" +
                                          "where r.id=$recipeId" +
                                          "return r, c, h, u, w",
                        parameters("recipeId", idRecipe));

                while(result.hasNext()){
                    Record r= result.next();
                    String id = r.get("c.id").asString();
                    String text = r.get("c.text").asString();
                    String username = r.get("u.username").asString();
                    Comment comment= new Comment(id, text, username);
                    comments.add(comment);
                }
                return comments;
            });
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

            session.readTransaction(tx->{
                Result result = tx.run("match (u:User)-[f:FOLLOWS]->(u2:User)" +
                                "match (r:Recipe)<-[a:ADDS]-(u2)" +
                                "where u.id=$userId" +
                                "return r, u, u2, f, a",
                        parameters("userId", userId));

                while(result.hasNext()){
                    Record r= result.next();
                    String id= r.get("r.id").asString();
                    String name = r.get("r.name").asString();
                    Recipe rec= new Recipe(id, name);
                    recipes.add(rec);
                }
                return recipes;
            });

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }

        return recipes;
    }
}
