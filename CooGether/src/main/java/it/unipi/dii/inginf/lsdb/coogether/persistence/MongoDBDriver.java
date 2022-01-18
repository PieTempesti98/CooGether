package it.unipi.dii.inginf.lsdb.coogether.persistence;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import it.unipi.dii.inginf.lsdb.coogether.bean.Comment;
import it.unipi.dii.inginf.lsdb.coogether.bean.Recipe;
import it.unipi.dii.inginf.lsdb.coogether.bean.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.mongodb.client.*;
import com.mongodb.ConnectionString;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.*;


import org.bson.BsonArray;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

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
            doc.append("recipeInstructions", r.getInstructions());

            collection.insertOne(doc);

        }catch(Exception ex){
            return false;
        }
        return true;
    }

    //delete the recipe and add the modified recipe
    public boolean updateRecipe(Recipe r){
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
            return false;
        }
        return true;
    }

    public boolean deleteRecipe(Recipe r){
        try{
            collection.deleteOne(eq("recipeId", r.getRecipeId()));
        }catch(Exception ex){
            return false;
        }
        return true;
    }

    public boolean addComment(Recipe r, Comment c){
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
            return false;
        }
        return true;
    }

    //delete the comment and add the modified comment
    public boolean updateComment(Recipe r, Comment c){
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
                System.out.println("A problem has occurred in modify modify");
                return false;
            }

        }catch(Exception ex){
            return false;
        }
        return true;
    }

    public boolean deleteComment(Recipe r, Comment c){
        try{

            Bson studentFilter = Filters.eq( "recipeId", r.getRecipeId() );
            Bson delete = Updates.pull("comments", new Document("reviewId", c.getCommentId()));
            collection.updateOne(studentFilter, delete);

        }catch(Exception ex){
            return false;
        }
        return true;
    }

    //decidere se quando viene eliminato un utente devono essere eliminate tutte le sue ricette
    /*public boolean deleteRecipesOfAUser(User u){
        try{

        }catch(Exception ex){
            return false;
        }
        return true;
    }*/

    public List<Recipe> getAllRecipes(){
        List<Recipe> recipes = new ArrayList<>();

        Bson sort = sort(descending("datePublished"));
        Bson proj = project(fields(excludeId(), include("name", "authorName","datePublished")));

        List<Document> results = (List<Document>) collection.aggregate(Arrays.asList(sort,proj)).into(new ArrayList());

        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        Gson gson = new Gson();
        recipes = gson.fromJson(gson.toJson(results), recipeListType);

        return recipes;
    }

    public List<Recipe> getRecipesFromAuthorName(String username){
        List<Recipe> recipes= new ArrayList<>();
        List<Document> results = new ArrayList<>();
        Gson gson = new Gson();

        Bson myMatch = match(eq("authorName", username));
        Bson mySort = sort(descending("datePublished"));
        Bson projection = project( fields(excludeId(), include("name", "authorName","datePublished")));

        results= (List<Document>) collection.aggregate(Arrays.asList(myMatch, mySort, projection))
                .into(new ArrayList<>());

        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        recipes = gson.fromJson(gson.toJson(results), recipeListType);

        return recipes;
    }

    public List<Recipe> getRecipesFromCategory(String category){
        List<Recipe> recipes= new ArrayList<>();
        List<Document> results = new ArrayList<>();
        Gson gson = new Gson();

        Bson myMatch = match(eq("recipeCategory", category));
        Bson mySort = sort(descending("datePublished"));
        Bson projection=project(fields(excludeId(), include("name", "authorName", "recipeCategory", "datePublished")));

        results=(List<Document>) collection.aggregate(Arrays.asList(myMatch,mySort, projection))
                .into(new ArrayList<>());

        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        recipes = gson.fromJson(gson.toJson(results), recipeListType);

        return recipes;
    }

    public List<Recipe> getRecipesFromTwoIngredients(String ing1, String ing2){
        List<Recipe> recipes= new ArrayList<>();
        List<Document> results = new ArrayList<>();
        Gson gson = new Gson();

        String pattern1=".*" + ing1 +".*";
        String pattern2=".*" + ing2 +".*";
        Bson myMatch_1=match(regex("ingredients", pattern1));
        Bson myMatch_2=match(regex("ingredients", pattern2));
        Bson projection=project(fields(excludeId(), include("name", "ingredients")));

        results=(List<Document>) collection.aggregate(Arrays.asList(myMatch_1,myMatch_2,
               	 projection)).into(new ArrayList<>());

        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        recipes = gson.fromJson(gson.toJson(results), recipeListType);

        return recipes;
    }


    //******************************************************************************************************************
    //                              ANALYTICS
    //******************************************************************************************************************

    public List<Recipe> searchTopKHealthiestRecipes(String category, int k){
        List<Recipe> recipes= new ArrayList<>();
        List<Document> results = new ArrayList<>();
        Gson gson = new Gson();

        Bson m = match(eq("recipeCategory", category));
        Bson m1 = match(exists("calories", true));
        Bson m2 = match(exists("fatContent", true));
        Bson m3 = match(exists("sodiumContent", true));
        Bson m4 = match(exists("proteinContent", true));
        Bson s= sort(ascending("calories"));
        Bson s1= sort(ascending("fatContent"));
        Bson s2= sort(ascending("sodiumContent"));
        Bson s3= sort(descending("proteinContent"));
        Bson l=limit(k);

        results=(List<Document>) collection.aggregate(Arrays.asList(m, m1, m2, m3, m4, s, s1,
                        s2, s3, l)).into(new ArrayList<>());

        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        recipes = gson.fromJson(gson.toJson(results), recipeListType);

        return recipes;
    }

    public List<Recipe> searchTopKReviewedRecipes(String category, int k){
        List<Recipe> recipes= new ArrayList<>();
        List<Document> results = new ArrayList<>();
        Gson gson = new Gson();

        Bson myMatch_1 = match(eq("recipeCategory", category));
        Bson myUnwind = unwind("$comments");
        Bson myMatch_2 = match(eq("comments.rating", 5));
        Bson myGroup = group("$recipeId", sum("count", 1));
        Bson mySort = sort(descending("count"));
        Bson myLimit = limit(k);

        results=(List<Document>) collection.aggregate(Arrays.asList(myMatch_1,myUnwind,myMatch_2,myGroup,mySort,myLimit)).
                into(new ArrayList<>());

        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        recipes = gson.fromJson(gson.toJson(results), recipeListType);

        return recipes;
    }

    public List<Recipe> searchMostRecentRecipes(String category){
        List<Recipe> recipes= new ArrayList<>();
        List<Document> results = new ArrayList<>();
        Gson gson = new Gson();

        Bson myMatch = match(eq("recipeCategory", category));
        Bson mySort = sort(descending("datePublished"));

        results=(List<Document>)collection.aggregate(Arrays.asList(myMatch,mySort))
             .into(new ArrayList<>());

        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        recipes = gson.fromJson(gson.toJson(results), recipeListType);

        return recipes;
    }

    public List<Recipe> searchFastestRecipes(String category){
        List<Recipe> recipes= new ArrayList<>();
        List<Document> results = new ArrayList<>();
        Gson gson = new Gson();

        Bson m1 = match(eq("recipeCategory", category));
        Bson m2 = match(exists("cookTime", true));
        Bson m3 = match(exists("prepTime", true));
        Bson s1 = sort(ascending("cookTime"));
        Bson s2 = sort(ascending("prepTime"));

        results=(List<Document>)collection.aggregate(Arrays.asList(m1, m2, m3, s1, s2))
                .into(new ArrayList<>());

        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        recipes = gson.fromJson(gson.toJson(results), recipeListType);

        return recipes;
    }

    public List<Recipe> searchFewestIngredientsRecipes(String category){
        List<Recipe> recipes= new ArrayList<>();
        List<Document> results = new ArrayList<>();
        Gson gson = new Gson();

        Bson m1 = match(eq("recipeCategory", category));
        Bson u= unwind("$ingredients");
        Bson g=group("$recipeId", sum("numberOfIngredients", 1));
        Bson s= sort(ascending("numberOfIngredients"));

        results=(List<Document>)collection.aggregate(Arrays.asList(m1, u, g, s))
                .into(new ArrayList<>());

        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        recipes = gson.fromJson(gson.toJson(results), recipeListType);

        return recipes;
    }

    public List<User> userRankingSystem(int k){
        List<User> users= new ArrayList<>();
        List<Document> results= new ArrayList<>();
        Gson gson= new Gson();

        Bson unwind_comments = unwind("$comments");
        Bson group1 = new Document("$group", new Document("_id",
                	                                        new Document("recipe", "$recipeId")
                                                            .append("author", "$authorId"))
        	                                                .append("avgRating", new Document("$avg", "$comments.rating")));
        Bson group2 = group("$_id.author", avg("avgRating", "$avgRating"));
        Bson sort_by_rating = sort(descending("avgRating"));
        Bson limitResults = limit(k);

        results= (List<Document>) collection.aggregate(Arrays.asList(unwind_comments, group1, group2,
                 sort_by_rating, limitResults)).into(new ArrayList<>());

        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
        users = gson.fromJson(gson.toJson(results), userListType);

        return users;
    }

    public List<Recipe> searchHighestLifespanRecipes(int k){
        List<Recipe> recipes= new ArrayList<>();
        List<Document> results = new ArrayList<>();
        Gson gson = new Gson();

        Bson unwind_comments = unwind("$comments");
        Bson group1 = new Document("$group", new Document("_id", "$recipeId")
        	                .append("mostRecentComment", new Document("$max", "$comments.dateModified"))
        	                .append("leastRecentComment",new Document("$min", "$comments.dateModified")));
        BsonArray operands = new BsonArray();
        operands.add(new BsonString("$mostRecentComment"));
        operands.add(new BsonString("$leastRecentComment"));
        Bson project_lifespan = project(fields(excludeId(), include("_id"),
                	        new Document("lifespan", new Document("$subtract", operands))));
        Bson sort_by_lifespan = sort(descending("lifespan"));
        Bson limitResults = limit(k);

        results= (List<Document>) collection.aggregate(Arrays.asList(unwind_comments, group1,
                	project_lifespan, sort_by_lifespan, limitResults)).into(new ArrayList<>());

        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        recipes = gson.fromJson(gson.toJson(results), recipeListType);

        return recipes;
    }

}
