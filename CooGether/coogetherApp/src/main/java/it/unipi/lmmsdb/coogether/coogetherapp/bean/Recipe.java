package it.unipi.lmmsdb.coogether.coogetherapp.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Recipe{

    private int recipeId;
    private String name;
    private int authorId;
    private String authorName;
    private int cookTime;
    private int prepTime;
    private Date datePublished;
    private String description;
    private String image;
    private String category;
    private ArrayList<String> ingredients;
    private ArrayList<Comment> comments;
    private double calories;
    private double fatContent;
    private double sodiumContent;
    private double proteinContent;
    private int recipeServings;
    private ArrayList<String> recipeInstructions;

    public Recipe(){
    }

    public Recipe(int id, String name){
        this.recipeId=id;
        this.name=name;
    }

    public Recipe(int id, String name, Date datePublished, String category){
        this.recipeId= id;
        this.name= name;
        this.datePublished=datePublished;
        this.category= category;
    }

    public Recipe(int id, String name, int authorId, String authorName, int cookTime, int prepTime, Date pub,
                  String desc, String img, String category, ArrayList<String> ing, double cal,
                  double fat, double sodium, double protein, int serving, ArrayList<String> instructions){

        this.recipeId= id;
        this.name= name;
        this.authorId=authorId;
        this.authorName=authorName;
        this.cookTime=cookTime;
        this.prepTime=prepTime;
        this.datePublished=pub;
        this.description=desc;
        this.image= img;
        this.category= category;
        this.ingredients=ing;
        this.calories= cal;
        this.fatContent= fat;
        this.sodiumContent=sodium;
        this.proteinContent=protein;
        this.recipeServings=serving;
        this.recipeInstructions=instructions;
    }

    public Recipe(int id, String name, int authorId, String authorName, int cookTime, int prepTime, Date pub,
                  String desc, String img, String category, ArrayList<String> ing, ArrayList<Comment> comm, double cal,
                  double fat, double sodium, double protein, int serving, ArrayList<String> instructions){

        this.recipeId= id;
        this.name= name;
        this.authorId=authorId;
        this.authorName=authorName;
        this.cookTime=cookTime;
        this.prepTime=prepTime;
        this.datePublished=pub;
        this.description=desc;
        this.image= img;
        this.category= category;
        this.ingredients=ing;
        this.comments=comm;
        this.calories= cal;
        this.fatContent= fat;
        this.sodiumContent=sodium;
        this.proteinContent=protein;
        this.recipeServings=serving;
        this.recipeInstructions=instructions;
    }

    public int getRecipeId(){
        return recipeId;
    }

    public String getName(){
        return name;
    }

    public int getAuthorId(){
        return authorId;
    }

    public String getAuthorName(){
        return authorName;
    }

    public int getCookTime(){
        return cookTime;
    }

    public int getPrepTime(){
        return prepTime;
    }

    public Date getDatePublished(){
        return datePublished;
    }

    public String getDescription(){
        return description;
    }

    public String getImage(){
        return image;
    }

    public String getCategory(){
        return category;
    }

    public ArrayList<String> getIngredients(){
        return ingredients;
    }

    public ArrayList<Comment> getComments(){
        return comments;
    }

    public double getCalories(){
        return calories;
    }

    public double getFatContent(){
        return fatContent;
    }

    public double getSodiumContent(){
        return sodiumContent;
    }

    public double getProteinContent(){
        return proteinContent;
    }

    public int getRecipeServings(){
        return recipeServings;
    }

    public ArrayList<String> getRecipeInstructions(){
        return recipeInstructions;
    }

    public void setRecipeId(int id){
        this.recipeId=id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setAuthorId(int id){
        this.authorId=id;
    }

    public void setAuthorName(String name){
        this.authorName=name;
    }

    public void setCookTime(int time){
        this.cookTime=time;
    }

    public void setPrepTime(int time){
        this.prepTime=time;
    }

    public void setDatePublished(Date date){
        this.datePublished=date;
    }

    public void setDescription(String desc){
        this.description=desc;
    }

    public void setImages(String img){
        this.image=img;
    }

    public void setCategory(String cat){
        this.category=cat;
    }

    public void setIngredients(ArrayList<String> ing){
        this.ingredients=ing;
    }

    public void setComments(ArrayList<Comment> c){
        this.comments= c;
    }

    public void setCalories(double kal){
        this.calories= kal;
    }

    public void setFatContent(double f){
        this.fatContent=f;
    }

    public void setSodiumContent(double sod){
        this.sodiumContent=sod;
    }

    public void setProteinContent(double pro){
        this.proteinContent=pro;
    }

    public void setRecipeServings(int serv){
        this.recipeServings=serv;
    }

    public void setRecipeInstructions(ArrayList<String> in){
        this.recipeInstructions=in;
    }

    public void addComments(Comment comment) {
        this.comments.add(comment);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + name + '\'' +
                ", category=" + category + '\'' +
                ", cookTime=" + cookTime + '\'' +
                ", recipeServings=" + recipeServings + '\'' +
                ", prepTime=" + prepTime +'\'' +
                ", authorUsername='" + authorName + '\'' +
                ", description='" + description + '\'' +
                ", instructions='" + recipeInstructions + '\'' +
                ", ingredients=" + ingredients + '\'' +
                ", calories=" + calories + '\'' +
                ", fatContent=" + fatContent + '\'' +
                ", sodiumContent=" + sodiumContent + '\'' +
                ", proteinContent=" + proteinContent + '\'' +
                ", creationTime=" + datePublished + '\'' +
                ", comments=" + comments +
                '}';
    }
}
