package it.unipi.dii.inginf.lsdb.coogether.bean;

import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

public class Recipe {

    private String recipeId;
    private String name;
    private String authorId;
    private String authorName;
    private int cookTime;
    private int prepTime;
    private Date datePublished;
    private String description;
    private String image;
    private String category;
    private List<String> ingredients;
    private List<Comment> comments;
    private double calories;
    private double fatContent;
    private double sodiumContent;
    private double proteinContent;
    private int recipeServings;
    private List<String> instructions;

    public Recipe(){
    }

    public Recipe(String id, String name){
        this.recipeId=id;
        this.name=name;
    }

    public Recipe(String id, String name, String authorId, String authorName, int cookTime, int prepTime, Date pub,
                  String desc, String img, String category, List<String> ing, List<Comment> comm, double cal,
                  double fat, double sodium, double protein, int serving, List<String> instructions){

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
        this.instructions=instructions;
    }

    public String getRecipeId(){
        return recipeId;
    }

    public String getName(){
        return name;
    }

    public String getAuthorId(){
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

    public List<String> getIngredients(){
        return ingredients;
    }

    public List<Comment> getComments(){
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

    public List<String> getInstructions(){
        return instructions;
    }

    public void setRecipeId(String id){
        this.recipeId=id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setAuthorId(String id){
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

    public void setIngredients(List<String> ing){
        this.ingredients=ing;
    }

    public void setComments(List<Comment> c){
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

    public void setInstructions(List<String> in){
        this.instructions=in;
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
                ", instructions='" + instructions + '\'' +
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
