package entities;

import dto.IngredientDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Recipe {
    private int recipeId;
    private String name;
    private int authorId;
    private String authorName;
    private int cookTime;
    private int prepTime;
    private Date datePublished;
    private String description;
    private String image;
    private String recipeCategory;
    private ArrayList<String> ingredients;
    private ArrayList<Review> comments;
    private double calories;
    private double fatContent;
    private double sodiumContent;
    private double proteinContent;
    private ArrayList<String> recipeInstructions;

    //conversion of cookTime and prepTime string format in minutes
    private int cookTimeFormatting(String str){
        if(str != null) {
            String regex1 = "[H]+";
            String[] split1 = str.split(regex1);
            if (split1.length == 1) {
                split1[0] = split1[0].replace("PT", "");
                split1[0] = split1[0].replace("M", "");
                split1[0] = split1[0].replace("S", "");
                return Integer.parseInt(split1[0]) * 60;
            } else if (split1.length == 2) {
                split1[0] = split1[0].replace("PT", "");
                split1[1] = split1[1].replace("M", "");
                return Integer.parseInt(split1[0]) * 60 + Integer.parseInt(split1[1]);
            }
        }
        return 0;

    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public void setCookTime(String formattedCookTime){
        this.cookTime = cookTimeFormatting(formattedCookTime);
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public void setPrepTime(String formattedPrepTime){
        this.prepTime = cookTimeFormatting(formattedPrepTime);
    }


    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public void setDatePublished(String datePublished){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            this.datePublished = sdf.parse(datePublished);
        } catch (ParseException e) {
            e.printStackTrace();
            this.datePublished = null;
        }
    }
    public void setNewDatePublished(String datePublished){
        if(datePublished == null)
            this.datePublished = null;
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            try {
                this.datePublished = sdf.parse(datePublished);
            } catch (ParseException e) {
                e.printStackTrace();
                this.datePublished = null;
            }
        }
    }


    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(String recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Review> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Review> comments) {
        this.comments = comments;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFatContent() {
        return fatContent;
    }

    public void setFatContent(double fatContent) {
        this.fatContent = fatContent;
    }

    public double getSodiumContent() {
        return sodiumContent;
    }

    public void setSodiumContent(double sodiumContent) {
        this.sodiumContent = sodiumContent;
    }

    public double getProteinContent() {
        return proteinContent;
    }

    public void setProteinContent(double proteinContent) {
        this.proteinContent = proteinContent;
    }

    public ArrayList<String> getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(ArrayList<String> recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
