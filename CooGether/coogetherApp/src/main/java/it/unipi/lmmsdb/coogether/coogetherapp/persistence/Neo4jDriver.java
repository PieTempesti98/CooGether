package it.unipi.lmmsdb.coogether.coogetherapp.persistence;

import it.unipi.lmmsdb.coogether.coogetherapp.bean.Recipe;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;
import it.unipi.lmmsdb.coogether.coogetherapp.config.ConfigurationParameters;
import org.neo4j.driver.Record;
import org.neo4j.driver.*;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Neo4jDriver{

    private Driver driver;
    private final String uri;
    private final String user;
    private final String password;

    private static Neo4jDriver instance = null;

    private Neo4jDriver()
    {
        uri = "neo4j://" + ConfigurationParameters.getNeo4jIp() + ":" + ConfigurationParameters.getNeo4jPort();
        this.user = ConfigurationParameters.getNeo4jUsername();
        this.password = ConfigurationParameters.getNeo4jPassword();
        openConnection();
    }

    public static Neo4jDriver getInstance()
    {
        if (instance == null)
        {
            instance = new Neo4jDriver();
        }
        return instance;
    }


    private void openConnection() {
        try {
            driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
        }catch (Exception ex){
            System.out.println("Impossible open connection with Neo4j");
        }
    }

    private void closeConnection() {
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
        openConnection();
        try(Session session= driver.session()){

            session.writeTransaction((TransactionWork<Void>) tx ->{
                tx.run("match (u:User) where u.id=$usId CREATE (r:Recipe {id:$recId, name:$title}), (u)-[:ADDS]->(r)",
                        Values.parameters("usId", r.getAuthorId(),"recId", r.getRecipeId(), "title", r.getName()));

                return null;
            });
        }catch(Exception ex){
            ex.printStackTrace();
            closeConnection();
            return false;
        }
        closeConnection();
        return true;
    }

    //change the title of one recipe
    public boolean updateRecipe(Recipe r){
        openConnection();
        try(Session session= driver.session()){

            session.writeTransaction((TransactionWork<Void>) tx ->{
                tx.run("match(r:Recipe {id:$recId}) set r.name=$newName",
                        Values.parameters("recId", r.getRecipeId(), "newName", r.getName()));

                return null;
            });

        }catch(Exception ex){
            ex.printStackTrace();
            closeConnection();
            return false;
        }
        closeConnection();
        return true;
    }

    public boolean deleteRecipe(Recipe r){
        openConnection();
        try(Session session= driver.session()){

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run( "MATCH (r:Recipe) " +
                                "WHERE r.id=$id " +
                                "DETACH DELETE r",
                        Values.parameters( "id", r.getRecipeId()) );
                closeConnection();
                return null;
            });
            closeConnection();
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


    public boolean addUser(User u){
        try(Session session= driver.session()){
            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run ("CREATE (u:User {id: $id, username: $username, fullname: $fullname, email: $email, password: $password})",
                Values.parameters("id", u.getUserId(), "username", u.getUsername(), "fullname", u.getFullName(), "email", u.getEmail(), "password", u.getPassword()));
                return null;
            } );

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    //modify all the parameters
    public boolean updateUser(User u){
        try(Session session= driver.session()){

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run ( "match (u:User {id:$id})" +
                                "set u.email=$email, u.fullname=$fullName, u.password=$pass, u.username=$userName",
                        Values.parameters("email", u.getEmail(), "fullName", u.getFullName(), "pass",
                                u.getPassword(), "userName", u.getUsername(), "id", u.getUserId()));
                return null;
            } );

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
                        Values.parameters( "id", u.getUserId()) );
                return null;
            });
            return true;

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    public boolean follow(int following, int follower){
        try(Session session= driver.session()){

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run ("match (a:User) where a.id= $usera" +
                           "match (b:User) where b.id=$userb" +
                           "merge (a)-[:FOLLOWS]->(b)",
                        Values.parameters("usera", following, "userb", follower));
                return null;
            } );

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean unfollow(int following, int follower){
        try(Session session= driver.session()){

            session.writeTransaction((TransactionWork<Void>) tx -> {
                tx.run ("match (a:User {a.id: $usera}) -[f:FOLLOWS]-> (b:User {b.id:$userb})" +
                           "delete f",
                        Values.parameters("usera", following, "userb", follower));
                return null;
            } );

        }catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<User> getUsers( int skip, int limit){
        ArrayList<User> users= new ArrayList<>();

        try(Session session= driver.session()){

            session.readTransaction(tx->{
                Result result = tx.run("match (u:User) " +
                                "return u.id, u.username, u.email, u.fullName " +
                                "skip $toSkip " +
                                "limit $toLimit "
                        , Values.parameters("toLimit",limit, "toSkip", skip));

                while(result.hasNext()){
                    Record r= result.next();
                    int id = r.get("u.id").asInt();
                    String username = r.get("u.username").asString();
                    String email = r.get("u.email").asString();
                    String fullName = r.get("u.fullName").asString();
                    User user= new User(id, username, fullName, email);
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

    public User getUsersFromUnique(String unique){

        try(Session session= driver.session()){
            User user;
            user = session.readTransaction(tx->{
                Result result = tx.run("match (u:User) where u.username=$name or u.email=$name " +
                                "return u.id, u.password, u.email, u.username, u.fullName",
                        Values.parameters("name", unique));

                if (result.hasNext()){
                    Record r= result.next();
                    int id = r.get("u.id").asInt();
                    String username = r.get("u.username").asString();
                    String password = r.get("u.password").asString();
                    String email = r.get("u.email").asString();
                    String fullName = r.get("u.fullName").asString();
                    return new User(id, username, fullName, password, email);
                }
                return null;
            });
            return user;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public List<User> getFollowedUsers(User u){
        List<User> users= new ArrayList<>();

        try(Session session= driver.session()){

            session.readTransaction(tx->{
                Result result = tx.run("match (u1:User)-[f:FOLLOWS]->(u2:User)" +
                                          "where u1.id = $userId"+
                                           "return u2", Values.parameters("userId", u.getUserId()));

                while(result.hasNext()){
                    Record r= result.next();
                    int id = r.get("u2.id").asInt();
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

    public ArrayList<Recipe> getRecipes(int skip, int limit){
        ArrayList<Recipe> recipes= new ArrayList<>();

        try(Session session= driver.session()){

            session.readTransaction(tx->{
                Result result = tx.run("match (r:Recipe) where r.id IS NOT NULL " +
                                          "return r.id, r.name, r.datePublished, r.category order by r.datePublished desc " +
                                          "skip $toSkip " +
                                          "limit $toLimit"
                                          , Values.parameters("toLimit",limit, "toSkip", skip));

                while(result.hasNext()){
                    Record r= result.next();
                    int id = r.get("r.id").asInt();
                    String name = r.get("r.name").asString();
                    Date date = java.util.Date.from(r.get("r.datePublished").asLocalDate()
                            .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
                    String category = r.get("r.category").asString();
                    Recipe recipe= new Recipe(id, name, date, category);
                    recipes.add(recipe);
                }
                return recipes;
            });

        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return recipes;
    }

    public int getMaxUId() {
        AtomicInteger maxId = new AtomicInteger();
        try(Session session= driver.session()){
            session.readTransaction(tx->{
                Result result = tx.run("match (u:User) return max(u.id) as maxId");
                Record r= result.next();

                maxId.set(r.get("maxId").asInt());
                return maxId;
            });
            return maxId.get();
        }catch(Exception ex){
            ex.printStackTrace();
            return 0;
        }

    }

    public ArrayList<String> getAllCategories(){
        ArrayList<String> cat=new ArrayList<>();
        try(Session session=driver.session()){
            session.readTransaction(tx->{
                Result result=tx.run("match (r:Recipe) " +
                        "return distinct r.category order by r.category");
                while (result.hasNext())
                {
                    Record r=result.next();
                    String catString=r.get("r.category").asString();
                    cat.add(catString);
                }
                return cat;
            });
        }
        catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return cat;
    }

    //******************************************************************************************************************
    //                              ANALYTICS
    //******************************************************************************************************************

    public ArrayList<Recipe> searchSuggestedRecipes(int skip, int howMany, String userId){
        ArrayList<Recipe> recipes= new ArrayList<>();

        try(Session session= driver.session()){

            session.readTransaction(tx->{
                Result result = tx.run("match (u:User)-[f:FOLLOWS]->(u2:User) " +
                                "match (r:Recipe)<-[a:ADDS]-(u2) " +
                                "where u.id=$userId " +
                                "return r.id, r.name, r.category, r.datePublished order by r.datePublished desc " +
                                "skip $skip limit $limit",
                        Values.parameters("userId", userId, "skip", skip, "limit", howMany));

                while(result.hasNext()){
                    Record r= result.next();
                    int id= r.get("r.id").asInt();
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

    public List<User> mostFollowedUsers(int limit){
        ArrayList<User> users = new ArrayList<>();

        try ( Session session = driver.session() ) {
            session.readTransaction( tx -> {Result result = tx.run("match (u:User)<-[f:FOLLOWS]-(:User) " +
                                                                      "return u.id, u.username, count(DISTINCT f) as follower " +
                                                                      "order by follower desc " +
                                                                      "limit $l",
                                                            Values.parameters( "l", limit) );

                while(result.hasNext()){
                    Record r = result.next();
                    int id= r.get("u.id").asInt();
                    String name= r.get("u.username").asString();
                    int followers= r.get("follower").asInt();
                    User u= new User(id, name, followers);

                    users.add(u);
                }

                return users;
            });
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return users;
    }

    public List<User> getMostActiveUsers(int k){
        List<User> users = new ArrayList<>();

        try(Session session = driver.session()){
            session.readTransaction( tx -> {Result result = tx.run("match (user:User) --> (x:Recipe)" +
                          			                                  "return user, count(x)" +
                            			                              "order by count(x)" +
                           				                              "limit $k",
                			                                Values.parameters("k", k) );

                while(result.hasNext()){
                    Record r = result.next();
                    int id = r.get("user.id").asInt();
               		String username = r.get("user.username").asString();
                    User u = new User(id, username);
               		users.add(u);
                }
           		return users;
            });
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return users;
    }

}
