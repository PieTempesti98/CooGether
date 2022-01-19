package dbConnections;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entities.Recipe;
import entities.Review;
import org.bson.Document;

import java.util.ArrayList;

public class MongoConn{

    private final static String connectionString = "mongodb://172.16.4.51:27018,172.16.4.52:27018,172.16.4.53:27018/?retryWrites=true&w=3&wtimeoutMS=5000&readPreference=nearest";

    private static MongoClient myClient;
    private static MongoDatabase db;
    private static MongoCollection<Document> coll;

    private static void openConnection() {
        myClient = MongoClients.create(connectionString);
        db = myClient.getDatabase("coogether");
        coll = db.getCollection("recipe");
    }

    private static void closeConnection() {
        myClient.close();
    }

    public static void insertRecipes(ArrayList<Recipe> list){
        openConnection();
        coll.drop();
        db.getCollection("recipe");
        ArrayList<Document> toAdd = new ArrayList<>();
        for(Recipe r: list) {
            Document d = new Document("recipeId", r.getRecipeId())
                    .append("name", r.getName())
                    .append("authorId", r.getAuthorId())
                    .append("authorName", r.getAuthorName())
                    .append("cookTime", r.getCookTime())
                    .append("prepTime", r.getPrepTime())
                    .append("datePublished", r.getDatePublished())
                    .append("description", r.getDescription())
                    .append("image", r.getImage())
                    .append("recipeCategory", r.getRecipeCategory())
                    .append("ingredients", r.getIngredients())
                    .append("calories", r.getCalories())
                    .append("fatContent", r.getFatContent())
                    .append("sodiumContent", r.getSodiumContent())
                    .append("proteinContent", r.getProteinContent())
                    .append("recipeInstructions", r.getRecipeInstructions());

            ArrayList<Document> comments = null;
            if(r.getComments() != null){
                comments = new ArrayList<>();
                for (Review rev : r.getComments()) {
                    Document doc = new Document("commentId", rev.getReviewId())
                            .append("authorId", rev.getAuthorId())
                            .append("authorName", rev.getAuthorName())
                            .append("dateSubmitted", rev.getDatePublished())
                            .append("rating", rev.getRating())
                            .append("comment", rev.getComment());
                    comments.add(doc);
                }
            }

            d.append("comments", comments);
            toAdd.add(d);
        }
            openConnection();
        for(Document d: toAdd)
            coll.insertOne(d);

            closeConnection();


    }

}
