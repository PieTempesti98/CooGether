package it.unipi.lmmsdb.coogether.coogetherapp.pojo;

import java.util.ArrayList;
import java.util.Date;

public class RecipePojo {

    private int recipeId;
    private String name;
    private int authorId;
    private String authorName;
    private int cookTime;
    private int prepTime;
    private DatePojo datePublished;
    private String description;
    private String image;
    private String recipeCategory;
    private ArrayList<String> ingredients;
    private ArrayList<CommentPojo> comments;
    private double calories;
    private double fatContent;
    private double sodiumContent;
    private double proteinContent;
    private ArrayList<String> recipeInstructions;

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

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public DatePojo getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(DatePojo datePublished) {
        this.datePublished = datePublished;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public ArrayList<CommentPojo> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentPojo> comments) {
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
}
