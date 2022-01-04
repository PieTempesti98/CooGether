package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class RecipeDTO {
    @JsonProperty("RecipeId")
    private int RecipeId;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("AuthorId")
    private int AuthorId;
    @JsonProperty("AuthorName")
    private String AuthorName;
    @JsonProperty("CookTime")
    private String CookTime;
    @JsonProperty("PrepTime")
    private String PrepTime;
    @JsonProperty("TotalTime")
    private String TotalTime;
    @JsonProperty("DatePublished")
    private String DatePublished;
    @JsonProperty("Description")
    private String Description;
    @JsonProperty("Images")
    private ArrayList<String> Images;
    @JsonProperty("RecipeCategory")
    private String RecipeCategory;
    @JsonProperty("Ingredients")
    private ArrayList<IngredientDTO> Ingredients;
    @JsonProperty("Keywords")
    private ArrayList<String> Keywords;
    @JsonProperty("AggregatedRating")
    private double AggregatedRating;
    @JsonProperty("ReviewCount")
    private double ReviewCount;
    @JsonProperty("Calories")
    private double Calories;
    @JsonProperty("FatContent")
    private double FatContent;
    @JsonProperty("SaturatedFatContent")
    private double SaturatedFatContent;
    @JsonProperty("CholesterolContent")
    private double CholesterolContent;
    @JsonProperty("SodiumContent")
    private double SodiumContent;
    @JsonProperty("CarbohydrateContent")
    private double CarbohydrateContent;
    @JsonProperty("FiberContent")
    private double FiberContent;
    @JsonProperty("SugarContent")
    private double SugarContent;
    @JsonProperty("ProteinContent")
    private double ProteinContent;
    @JsonProperty("RecipeServings")
    private int RecipeServings;
    @JsonProperty("RecipeYield")
    private String RecipeYield;
    @JsonProperty("RecipeInstructions")
    private ArrayList<String> RecipeInstructions;

    public RecipeDTO() {
    }

    public int getRecipeId() {
        return RecipeId;
    }

    public void setRecipeId(int recipeId) {
        RecipeId = recipeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public int getRecipeServings() {
        return RecipeServings;
    }

    public void setRecipeServings(int recipeServings) {
        RecipeServings = recipeServings;
    }

    public int getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(int authorId) {
        AuthorId = authorId;
    }

    public String getAuthorName() {
        return AuthorName;
    }

    public void setAuthorName(String authorName) {
        AuthorName = authorName;
    }

    public String getCookTime() {
        return CookTime;
    }

    public void setCookTime(String cookTime) {
        CookTime = cookTime;
    }

    public String getPrepTime() {
        return PrepTime;
    }

    public void setPrepTime(String prepTime) {
        PrepTime = prepTime;
    }

    public String getTotalTime() {
        return TotalTime;
    }

    public void setTotalTime(String totalTime) {
        TotalTime = totalTime;
    }

    public String getDatePublished() {
        return DatePublished;
    }

    public void setDatePublished(String datePublished) {
        DatePublished = datePublished;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRecipeCategory() {
        return RecipeCategory;
    }

    public void setRecipeCategory(String recipeCategory) {
        RecipeCategory = recipeCategory;
    }

    public double getAggregatedRating() {
        return AggregatedRating;
    }

    public void setAggregatedRating(double aggregatedRating) {
        AggregatedRating = aggregatedRating;
    }

    public double getReviewCount() {
        return ReviewCount;
    }

    public void setReviewCount(double reviewCount) {
        ReviewCount = reviewCount;
    }

    public double getCalories() {
        return Calories;
    }

    public void setCalories(double calories) {
        Calories = calories;
    }

    public double getFatContent() {
        return FatContent;
    }

    public void setFatContent(double fatContent) {
        FatContent = fatContent;
    }

    public double getSaturatedFatContent() {
        return SaturatedFatContent;
    }

    public void setSaturatedFatContent(double saturatedFatContent) {
        SaturatedFatContent = saturatedFatContent;
    }

    public double getCholesterolContent() {
        return CholesterolContent;
    }

    public void setCholesterolContent(double cholesterolContent) {
        CholesterolContent = cholesterolContent;
    }

    public double getSodiumContent() {
        return SodiumContent;
    }

    public void setSodiumContent(double sodiumContent) {
        SodiumContent = sodiumContent;
    }

    public double getCarbohydrateContent() {
        return CarbohydrateContent;
    }

    public void setCarbohydrateContent(double carbohydrateContent) {
        CarbohydrateContent = carbohydrateContent;
    }

    public double getFiberContent() {
        return FiberContent;
    }

    public void setFiberContent(double fiberContent) {
        FiberContent = fiberContent;
    }

    public double getSugarContent() {
        return SugarContent;
    }

    public void setSugarContent(double sugarContent) {
        SugarContent = sugarContent;
    }

    public double getProteinContent() {
        return ProteinContent;
    }

    public void setProteinContent(double proteinContent) {
        ProteinContent = proteinContent;
    }

    public ArrayList<String> getRecipeInstructions() {
        return RecipeInstructions;
    }

    public void setRecipeInstructions(ArrayList<String> recipeInstructions) {
        RecipeInstructions = recipeInstructions;
    }

    public ArrayList<String> getImages() {
        return Images;
    }

    public void setImages(ArrayList<String> images) {
        Images = images;
    }

    public ArrayList<IngredientDTO> getIngredients() {
        return Ingredients;
    }

    public void setIngredients(ArrayList<IngredientDTO> ingredients) {
        Ingredients = ingredients;
    }

    public ArrayList<String> getKeywords() {
        return Keywords;
    }

    public void setKeywords(ArrayList<String> keywords) {
        Keywords = keywords;
    }

    public String getRecipeYield() {
        return RecipeYield;
    }

    public void setRecipeYield(String recipeYield) {
        RecipeYield = recipeYield;
    }
}
