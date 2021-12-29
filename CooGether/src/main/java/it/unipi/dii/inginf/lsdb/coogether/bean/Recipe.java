package it.unipi.dii.inginf.lsdb.coogether.bean;

import org.bson.conversions.Bson;

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
    private List<String> images;
    private String category;
    private List<Bson> ingredients;
    private List<Comment> comments;
    private double calories;
    private double fatContent;
    private double saturatedFatContent;
    private double sodiumContent;
    private double carbohydrateContent;
    private double fiberContent;
    private double sugarContent;
    private double proteinContent;
    private int recipeServings;
    private List<String> instructions;

    public Recipe(){
    }
}
