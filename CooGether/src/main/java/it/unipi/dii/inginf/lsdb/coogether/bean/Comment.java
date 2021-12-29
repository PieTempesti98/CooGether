package it.unipi.dii.inginf.lsdb.coogether.bean;

public class Comment {
    private String commentId;
    private String authorId;
    private String authorName;
    private int rating;
    private int datePublished;
    private int dateModified;
    private String text;

    public Comment(){
    }

    public Comment( String id, String text, String name){
        this.commentId = id;
        this.text = text;
        this.authorId = name;
    }
}
