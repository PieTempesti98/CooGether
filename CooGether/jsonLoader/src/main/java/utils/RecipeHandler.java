package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.*;
import entities.Recipe;
import entities.Review;
import entities.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class RecipeHandler {
    private final static String path = "/uploads/recipes.json";

    public static RecipeListDTO parseRecipes(){
        InputStream input = RecipeListDTO.class.getResourceAsStream(path);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(input, RecipeListDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static NewRecipeListDTO parseNewRecipes(){
        InputStream input = NewRecipeListDTO.class.getResourceAsStream("/uploads/full_format_recipes.json");
        System.out.println(input);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(input, NewRecipeListDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Recipe> convertToRecipes(ArrayList<RecipeDTO> dto, ArrayList<CommentDTO> reviews){
        ArrayList<Recipe> list = new ArrayList<>();
        for(RecipeDTO elem: dto){
            Recipe r = new Recipe();
            r.setRecipeId(elem.getRecipeId());
            r.setName(elem.getName());
            r.setAuthorId(elem.getAuthorId());
            r.setAuthorName(elem.getAuthorName());
            r.setCookTime(elem.getCookTime());
            r.setPrepTime(elem.getPrepTime());
            r.setDatePublished(elem.getDatePublished());
            r.setDescription(elem.getDescription());
            if(elem.getImages() != null)
                r.setImage(elem.getImages().get(0));
            else
                r.setImage(null);
            r.setRecipeCategory(elem.getRecipeCategory());
            ArrayList<String> ingredients = new ArrayList<>();
            for(IngredientDTO i: elem.getIngredients()){
                ingredients.add(i.getQty() + " " + i.getName());
            }
            r.setIngredients(ingredients);
            ArrayList<Review> relatedReviews = new ArrayList<>();
            for(CommentDTO comment: reviews){
                if(comment.getRecipeId() == r.getRecipeId()) {
                    Review rev = new Review();
                    rev.setReviewId(comment.getReviewId());
                    rev.setAuthorId(comment.getAuthorId());
                    rev.setAuthorName(comment.getAuthorName());
                    rev.setRating(comment.getRating());
                    rev.setDateSubmitted(comment.getDateSubmitted());
                    rev.setComment(comment.getReview());
                    relatedReviews.add(rev);
                }
            }
            r.setComments(relatedReviews);
            r.setCalories(elem.getCalories());
            r.setFatContent(elem.getFatContent());
            r.setSodiumContent(elem.getSodiumContent());
            r.setProteinContent(elem.getProteinContent());
            r.setRecipeInstructions(elem.getRecipeInstructions());

            list.add(r);
        }
        return list;
    }

    public static ArrayList<Recipe> convertNewRecipes(ArrayList<Recipe> base, ArrayList<NewRecipeDTO> dto, ArrayList<User> users){
        int i = 0;
        for(NewRecipeDTO elem: dto){
            Recipe r = new Recipe();
            while(true){
                i++;
                boolean found = false;
                for(Recipe rec: base){
                    if(rec.getRecipeId() == i){
                        found = true;
                        break;
                    }
                }
                if(!found){
                    r.setRecipeId(i);
                    break;
                }
            }
            r.setName(elem.getTitle());
            int rand = new Random().nextInt(users.size());
            r.setAuthorId(users.get(rand).getUserID());
            r.setAuthorName(users.get(rand).getUsername());
            r.setCookTime(null);
            r.setPrepTime(null);
            r.setNewDatePublished(elem.getDate());
            r.setDescription(elem.getDesc());
            r.setImage(null);
            if(elem.getCategories() != null )
                if(elem.getCategories().size() > 0 )
                    r.setRecipeCategory(elem.getCategories().get(0));
                else
                    r.setRecipeCategory(null);
            else
                r.setRecipeCategory(null);
            r.setIngredients(elem.getIngredients());
            r.setComments(null);
            r.setCalories(elem.getCalories());
            r.setFatContent(elem.getFat());
            r.setSodiumContent(elem.getSodium());
            r.setProteinContent(elem.getProteins());
            r.setRecipeInstructions(elem.getDirections());

            base.add(r);
        }
        return base;
    }

    public static void toJson(ArrayList<Recipe> recipes){
        ObjectMapper om = new ObjectMapper();
        RecipeContainer c = new RecipeContainer();
        c.setData(recipes);
        try {
            om.writeValue(new File("recipes_ok.json"), c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
