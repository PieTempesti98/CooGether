package it.unipi.lmmsdb.coogether.coogetherapp.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.mongodb.client.model.*;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.Comment;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.Recipe;
import it.unipi.lmmsdb.coogether.coogetherapp.bean.User;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.mongodb.client.*;
import com.mongodb.ConnectionString;

import static com.mongodb.client.model.Filters.*;


import it.unipi.lmmsdb.coogether.coogetherapp.config.ConfigurationParameters;
import it.unipi.lmmsdb.coogether.coogetherapp.pojo.RecipePojo;
import it.unipi.lmmsdb.coogether.coogetherapp.utils.Utils;
import org.bson.BsonArray;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class MongoDBDriver{

    private static MongoClient myClient;
    private static MongoDatabase db;
    private static MongoCollection<org.bson.Document> collection;

    private static final String connectionString = "mongodb://" + ConfigurationParameters.getMongoFirstIp() + ":" + ConfigurationParameters.getMongoFirstPort() +
            "," + ConfigurationParameters.getMongoSecondIp() + ":" + ConfigurationParameters.getMongoSecondPort() +
            "," + ConfigurationParameters.getMongoThirdIp() + ":" + ConfigurationParameters.getMongoThirdPort() +
            "/?retryWrites=true&w=3&wtimeoutMS=5000&readPreference=nearest";

    private static final ConnectionString uri= new ConnectionString(connectionString);

    //returns recipes given a list of mongoDB documents
    private static ArrayList<Recipe> getRecipesFromDocuments(ArrayList<Document> results){

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            for (Document doc : results) {
                RecipePojo pojo = objectMapper.readValue(doc.toJson(), RecipePojo.class);
                Recipe recipe = Utils.mapRecipe(pojo);
                recipes.add(recipe);
            }

            return recipes;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static void openConnection(){
        try{
            myClient= MongoClients.create(uri);
            db= myClient.getDatabase(ConfigurationParameters.getMongoDbName());
            collection= db.getCollection("recipe");
        }catch (Exception ex){
            System.out.println("Impossible open connection with MongoDB");
        }
    }


    private static void closeConnection(){
        try{
            myClient.close();
        }catch (Exception ex){
            System.out.println("Impossible close connection with MongoDB");
        }
    }

    //******************************************************************************************************************
    //                              CRUD OPERATIONS
    //******************************************************************************************************************

    public static boolean addRecipe(Recipe r){
        openConnection();

        try{

            Document doc= new Document();
            doc.append("recipeId", r.getRecipeId());
            doc.append("name", r.getName());
            doc.append("authorId", r.getAuthorId());
            doc.append("authorName", r.getAuthorName());
            if(r.getCookTime() != -1)
                doc.append("cookTime", r.getCookTime());
            if(r.getPrepTime()!=-1)
                doc.append("prepTime", r.getPrepTime());
            doc.append("datePublished", r.getDatePublished());
            doc.append("description", r.getDescription());
            doc.append("image", r.getImage());
            doc.append("recipeCategory", r.getCategory());
            doc.append("ingredients", r.getIngredients());
            doc.append("comments", r.getComments());
            if(r.getCalories()!=-1)
                doc.append("calories", r.getCalories());
            if(r.getFatContent()!=-1)
                doc.append("fatContent", r.getFatContent());
            if(r.getSodiumContent()!=-1)
                doc.append("sodiumContent", r.getSodiumContent());
            if(r.getProteinContent()!=-1)
                doc.append("proteinContent", r.getProteinContent());
            if(r.getRecipeServings()!=-1)
                doc.append("recipeServings", r.getRecipeServings());
            doc.append("recipeInstructions", r.getRecipeInstructions());

            collection.insertOne(doc);

        }catch(Exception ex){
            closeConnection();
            return false;
        }
        closeConnection();
        return true;
    }

    //delete the recipe and add the modified recipe
    public static boolean updateRecipe(Recipe r){
        openConnection();
        try{
            boolean res=deleteRecipe(r);
            if(!res)
            {
                System.out.println("A problem has occurred in modify recipe");
                return false;
            }

            res= addRecipe(r);
            if(!res)
            {
                System.out.println("A problem has occurred in modify recipe");
                return false;
            }

        }catch(Exception ex){
            closeConnection();
            return false;
        }
        closeConnection();
        return true;
    }

    public static boolean deleteRecipe(Recipe r){
        openConnection();
        try{
            collection.deleteOne(Filters.eq("recipeId", r.getRecipeId()));
        }catch(Exception ex){
            closeConnection();
            return false;
        }
        closeConnection();
        return true;
    }

    public static boolean addComment(Recipe r, Comment c){
        openConnection();
        try{

            Document com = new Document("reviewId", c.getCommentId())
                    .append("authorId", c.getAuthorId())
                    .append("rating", c.getRating())
                    .append("authorName", c.getAuthorName())
                    .append("comment", c.getText())
                    .append("datePublished", c.getDatePublished());

            Bson filter = Filters.eq( "recipeId", r.getRecipeId() ); //get the parent-document
            Bson setUpdate = Updates.push("comments", com);

            collection.updateOne(filter, setUpdate);

        }catch(Exception ex){
            closeConnection();
            return false;
        }
        closeConnection();
        return true;
    }

    //delete the comment and add the modified comment
    public static boolean updateComment(Recipe r, Comment c){
        openConnection();
        try{
            boolean res=deleteComment(r,c);
            if(!res)
            {
                System.out.println("A problem has occurred in modify comment");
                return false;
            }
            res=addComment(r,c);
            if(!res)
            {
                System.out.println("A problem has occurred in modify comment");
                return false;
            }

        }catch(Exception ex){
            closeConnection();
            return false;
        }
        closeConnection();
        return true;
    }

    public static boolean deleteComment(Recipe r, Comment c){
        openConnection();
        try{

            Bson studentFilter = Filters.eq( "recipeId", r.getRecipeId() );
            Bson delete = Updates.pull("comments", new Document("reviewId", c.getCommentId()));
            collection.updateOne(studentFilter, delete);

        }catch(Exception ex){
            closeConnection();
            return false;
        }
        closeConnection();
        return true;
    }

    //decidere se quando viene eliminato un utente devono essere eliminate tutte le sue ricette

    public static boolean deleteRecipesOfAUser(User u){
        try{

        }catch(Exception ex){
            return false;
        }
        return true;
    }

    public static Recipe getRecipesFromId( int id){
        openConnection();
        Recipe recipe= null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ArrayList<Document> myDoc = new ArrayList<>();
        try{
            for(Document doc: collection.find(eq("recipeId", id))){
                System.out.println(doc.toString());
                myDoc.add(doc);
            }
            RecipePojo pojo = objectMapper.readValue(myDoc.get(0).toJson(), RecipePojo.class);
            recipe = Utils.mapRecipe(pojo);
            closeConnection();
            return recipe;
        } catch (Exception e) {
            e.printStackTrace();
            closeConnection();
            return null;
        }
    }

    public static ArrayList<Recipe> getAllRecipes(){
        openConnection();
        Bson sort = Aggregates.sort(Sorts.descending("datePublished"));
        Bson proj = Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("recipeId", "name", "authorName","category", "datePublished")));

        ArrayList<Document> results = collection.aggregate(Arrays.asList(sort,proj)).into(new ArrayList<>());

        closeConnection();
        return getRecipesFromDocuments(results);
    }

    public static ArrayList<Recipe> getRecipesFromAuthorName(String username){
        ArrayList<Recipe> recipes = new ArrayList<>();
        ArrayList<Document> results;
        Gson gson = new Gson();

        openConnection();
        Bson myMatch = Aggregates.match(Filters.eq("authorName", username));
        Bson mySort = Aggregates.sort(Sorts.descending("datePublished"));
        Bson projection = Aggregates.project( Projections.fields(Projections.excludeId(), Projections.include("recipeId","name", "authorName", "recipeCategory", "datePublished")));

        results = collection.aggregate(Arrays.asList(myMatch, mySort, projection))
                .into(new ArrayList<>());

        closeConnection();
        return getRecipesFromDocuments(results);
    }

    public static ArrayList<Recipe> getRecipesFromCategory(String category){
        ArrayList<Recipe> recipes;
        ArrayList<Document> results;
        Gson gson = new Gson();

        openConnection();
        Bson myMatch = Aggregates.match(Filters.eq("recipeCategory", category));
        Bson mySort = Aggregates.sort(Sorts.descending("datePublished"));
        Bson projection= Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("recipeId", "name", "authorName", "recipeCategory", "datePublished")));

        results = collection.aggregate(Arrays.asList(myMatch,mySort, projection)).into(new ArrayList<>());

        closeConnection();
        return getRecipesFromDocuments(results);
    }

    public static ArrayList<Recipe> getRecipesFromTwoIngredients(String ing1, String ing2){
        ArrayList<Recipe> recipes;
        ArrayList<Document> results;

        openConnection();
        String pattern1=".*" + ing1 +".*";
        String pattern2=".*" + ing2 +".*";
        Bson myMatch_1= Aggregates.match(Filters.regex("ingredients", pattern1));
        Bson myMatch_2= Aggregates.match(Filters.regex("ingredients", pattern2));
        Bson projection= Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("recipeId", "name", "authorName", "recipeCategory", "datePublished")));

        results = collection.aggregate(Arrays.asList(myMatch_1,myMatch_2, projection)).into(new ArrayList<>());

        closeConnection();
        return getRecipesFromDocuments(results);
    }

    public static int getMaxRecipeId(){
        int maxId = 0;
        ArrayList<Document> results;
        Recipe recipe= null;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        openConnection();
        Bson mySort = Aggregates.sort(Sorts.descending("recipeId"));
        Bson projection= Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("recipeId")));
        results = collection.aggregate(Arrays.asList(mySort, projection)).into(new ArrayList<>());
        closeConnection();
        try {
            RecipePojo pojo = objectMapper.readValue(results.get(0).toJson(), RecipePojo.class);
            recipe = Utils.mapRecipe(pojo);
            maxId = recipe.getRecipeId();
            return maxId;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }


    //******************************************************************************************************************
    //                              ANALYTICS
    //******************************************************************************************************************

    public static ArrayList<Recipe> searchTopKHealthiestRecipes(String category, int k){
        ArrayList<Recipe> recipes;
        ArrayList<Document> results;
        Gson gson = new Gson();

        openConnection();
        Bson m = Aggregates.match(Filters.eq("recipeCategory", category));
        Bson m1 = Aggregates.match(Filters.exists("calories", true));
        Bson m2 = Aggregates.match(Filters.exists("fatContent", true));
        Bson m3 = Aggregates.match(Filters.exists("sodiumContent", true));
        Bson m4 = Aggregates.match(Filters.exists("proteinContent", true));
        Bson s= Aggregates.sort(Sorts.ascending("calories"));
        Bson s1= Aggregates.sort(Sorts.ascending("fatContent"));
        Bson s2= Aggregates.sort(Sorts.ascending("sodiumContent"));
        Bson s3= Aggregates.sort(Sorts.descending("proteinContent"));
        Bson l= Aggregates.limit(k);

        results = collection.aggregate(Arrays.asList(m, m1, m2, m3, m4, s, s1, s2, s3, l)).into(new ArrayList<>());

        closeConnection();
        return getRecipesFromDocuments(results);
    }

    public static ArrayList<Recipe> searchTopKReviewedRecipes(String category, int k){
        ArrayList<Document> results;

        openConnection();
        Bson myMatch_1 = Aggregates.match(Filters.eq("recipeCategory", category));
        Bson myUnwind = Aggregates.unwind("$comments");
        Bson myMatch_2 = Aggregates.match(Filters.eq("comments.rating", 5));
        Bson myGroup = new Document("$group", new Document("_id", new Document("recipeId","$recipeId")
                .append("name", "$name")
                .append("recipeCategory", "$recipeCategory")
                .append("datePublished", "$datePublished")
                .append("authorName", "$authorName"))
                .append("count", new Document("$sum", 1)));
        Bson mySort = Aggregates.sort(Sorts.descending("count"));
        Bson myLimit = Aggregates.limit(k);

        results = collection.aggregate(Arrays.asList(myMatch_1,myUnwind,myMatch_2,myGroup,mySort,myLimit))
                .into(new ArrayList<>());

        closeConnection();
        ArrayList<Document> recipeValues = new ArrayList<>();
        for(Document doc: results){
            recipeValues.add((Document) doc.get("_id"));
        }

        return getRecipesFromDocuments(recipeValues);
    }

    public static ArrayList<Recipe> searchMostRecentRecipes(String category){
        ArrayList<Document> results;

        openConnection();
        Bson myMatch = Aggregates.match(Filters.eq("recipeCategory", category));
        Bson mySort = Aggregates.sort(Sorts.descending("datePublished"));

        results = collection.aggregate(Arrays.asList(myMatch,mySort)).into(new ArrayList<>());

        closeConnection();
        return getRecipesFromDocuments(results);
    }

    public static ArrayList<Recipe> searchFastestRecipes(String category, int k){
        ArrayList<Document> results;

        openConnection();
        Bson m1 = Aggregates.match(Filters.eq("recipeCategory", category));
        Bson m2 = Aggregates.match(Filters.exists("cookTime", true));
        Bson m3 = Aggregates.match(Filters.exists("prepTime", true));
        Bson s1 = Aggregates.sort(Sorts.ascending("cookTime"));
        Bson s2 = Aggregates.sort(Sorts.ascending("prepTime"));
        Bson myLimit = Aggregates.limit(k);

        results = collection.aggregate(Arrays.asList(m1, m2, m3, s1, s2, myLimit)).into(new ArrayList<>());

        closeConnection();
        return getRecipesFromDocuments(results);
    }

    public static ArrayList<Recipe> searchFewestIngredientsRecipes(String category, int k){
        ArrayList<Document> results;

        openConnection();
        Bson m1 = Aggregates.match(Filters.eq("recipeCategory", category));
        Bson u= Aggregates.unwind("$ingredients");
        Bson g= new Document("$group", new Document("_id", new Document("recipeId","$recipeId")
                    .append("name", "$name")
                    .append("recipeCategory", "$recipeCategory")
                    .append("datePublished", "$datePublished")
                    .append("authorName", "$authorName"))
                .append("numberOfIngredients", new Document("$sum", 1)));
        Bson s= Aggregates.sort(Sorts.ascending("numberOfIngredients"));
        Bson myLimit = Aggregates.limit(k);

        results = collection.aggregate(Arrays.asList(m1, u, g, s, myLimit)).into(new ArrayList<>());
        closeConnection();

        ArrayList<Document> recipeValues = new ArrayList<>();
        for(Document doc: results){
            recipeValues.add((Document) doc.get("_id"));
        }

        return getRecipesFromDocuments(recipeValues);
    }

    public static ArrayList<User> userRankingSystem(int k){
        ArrayList<User> users;
        ArrayList<Document> results;

        openConnection();
        Bson unwind_comments = Aggregates.unwind("$comments");
        Bson group1 = new Document("$group", new Document("_id",
                	                                        new Document("recipe", "$recipeId")
                                                            .append("author", "$authorId"))
        	                                                .append("avgRating", new Document("$avg", "$comments.rating")));
        Bson group2 = Aggregates.group("$_id.author", Accumulators.avg("avgRating", "$avgRating"));
        Bson sort_by_rating = Aggregates.sort(Sorts.descending("avgRating"));
        Bson limitResults = Aggregates.limit(k);

        results= collection.aggregate(Arrays.asList(unwind_comments, group1, group2, sort_by_rating, limitResults))
                .into(new ArrayList<>());
        closeConnection();
        Neo4jDriver neo4j = Neo4jDriver.getInstance();
        if(results.size() == 0)
            return null;
        users = new ArrayList<>();
        for(Document d: results){
            User user = neo4j.getUsersFromId(d.getInteger("_id"));
            users.add(user);
        }
        return users;
    }

    public static ArrayList<Recipe> searchHighestLifespanRecipes(int k){
        ArrayList<Recipe>recipes;
        ArrayList<Document> results;
        Gson gson = new Gson();

        openConnection();
        Bson unwind_comments = Aggregates.unwind("$comments");
        Bson group1 = new Document("$group", new Document("_id", new Document("recipeId","$recipeId")
                                .append("name", "$name")
                                .append("recipeCategory", "$recipeCategory")
                                .append("datePublished", "$datePublished")
                                .append("authorName", "$authorName"))
        	                .append("mostRecentComment", new Document("$max", "$comments.dateModified"))
        	                .append("leastRecentComment",new Document("$min", "$comments.dateModified")));
        BsonArray operands = new BsonArray();
        operands.add(new BsonString("$mostRecentComment"));
        operands.add(new BsonString("$leastRecentComment"));
        Bson project_lifespan = Aggregates.project(Projections.fields(Projections.excludeId(), Projections.include("_id"),
                	        new Document("lifespan", new Document("$subtract", operands))));
        Bson sort_by_lifespan = Aggregates.sort(Sorts.descending("lifespan"));
        Bson limitResults = Aggregates.limit(k);

        results= collection.aggregate(Arrays.asList(unwind_comments, group1, project_lifespan, sort_by_lifespan,
                limitResults)).into(new ArrayList<>());
        closeConnection();
        ArrayList<Document> recipeValues = new ArrayList<>();
        for(Document doc: results){
            recipeValues.add((Document) doc.get("_id"));
        }

        return getRecipesFromDocuments(recipeValues);
    }

}
